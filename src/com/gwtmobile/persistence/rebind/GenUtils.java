/*
 * Copyright 2010 Zhihua (Dennis) Jiang
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtmobile.persistence.rebind;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class GenUtils {
	private TreeLogger logger;
	private GeneratorContext context;
	private SourceWriter sw;
	private ClassSourceFileComposerFactory factory;
	
	public GenUtils(TreeLogger logger, GeneratorContext context) {
		this.logger = logger;
		this.context = context;
	}
	
	public String generateClass(String requestedTypeName, String gennedTypeNameSuffix, 
			ClassGenerator classGenerator) {
        // The package the class is going to be in.
        String packageName = getPackageName(requestedTypeName);
        String gennedTypeName = getClassName(requestedTypeName) + gennedTypeNameSuffix;
        PrintWriter pw = context.tryCreate(logger, packageName, gennedTypeName);
       
        // No PrintWriter means we've generated the same code before.
        if (pw != null) {
            factory = new ClassSourceFileComposerFactory(packageName, gennedTypeName);
            classGenerator.classSetup();
            sw = factory.createSourceWriter(context, pw);                        
            classGenerator.generateClass();
            sw.commit(logger);
        }
        return packageName + "." + gennedTypeName;
	}

	public void generateInnerClass(String modifiers, String className, String superClass, String[] interfaces,
			ClassGenerator classGenerator) {
		sw.println();
		print("%s class %s", modifiers, className);
		if (superClass != null) {
			print(" extends %s", superClass);
		}
		if (interfaces != null) {
			print(" implements ");
			for (int i = 0; i < interfaces.length; i++) {
				if (i > 0) {
					print(",");
				}
				print(interfaces[i]);
			}
		}
		println(" {");
		sw.indent();
		classGenerator.classSetup();
		classGenerator.generateClass();
		sw.outdent();
		println("}");
	}
	
	public void genrateStaticConstructor(MethodGenerator bodyGenerator) {
		sw.println();
		sw.println("static {");
		sw.indent();
		bodyGenerator.generateMethod();
		sw.outdent();
		sw.println("}");
	}

	public boolean generateMethod(String modifiers, String returnType, 
			String methodName, String[][] params, MethodGenerator methodGenerator) {
		return generateMethod(modifiers, returnType, methodName, params, false, methodGenerator);	
	}
	
	public boolean generateNativeMethod(String modifiers, String returnType, 
			String methodName, String[][] params, MethodGenerator methodGenerator) {
		return generateMethod(modifiers, returnType, methodName, params, true, methodGenerator);	
	}
	
	private boolean generateMethod(String modifiers, String returnType, 
			String methodName, String[][] params, boolean isNative, MethodGenerator methodGenerator) {
		
		sw.println();
		sw.print(modifiers + " ");
		if (isNative) {
			sw.print("native ");
		}
		if (returnType != null) {
			sw.print(returnType + " ");
		}
		sw.print(methodName + "(");
		if (params != null ) {
			for (int i = 0; i < params.length; i++) {
				print("%s %s", params[i][0], params[i][1]);
				if (i < params.length - 1) {
					print(", ");
				}
			}
		}
		sw.print(") ");
		if (isNative) {
			sw.print("/*-");
		}
		sw.println("{");
		sw.indent();
		if (methodGenerator != null) {
			methodGenerator.generateMethod();			
		}
		sw.outdent();
		sw.print("}");
		if (isNative) {
			sw.print("-*/;");
		}
		sw.println();
		return true;		
	}
	
	public SourceWriter sw() {
		return sw;
	}
	
	ClassSourceFileComposerFactory factory() {
		return factory;
	}

	public void print(String format, Object... args) {
		sw.print(String.format(format, args));
	}

	public void println(String format, Object... args) {
		sw.println(String.format(format, args));
	}
	
	public void addVariable(String modifiers, String type, String name) {
		sw.println(String.format("%s %s %s;", modifiers, type, name));
	}

	public void addVariable(String modifiers, String type, String name, String initValue) {
		sw.println(String.format("%s %s %s = %s;", modifiers, type, name, initValue));
	}

	public String getPackageName(String typeName) {
        final JClassType classType = getClassType(typeName);
        if (classType != null) {
        	return classType.getPackage().getName();
        }
        return null;
	}

	public String getClassName(String typeName) {
        final JClassType classType = getClassType(typeName);
        if (classType != null) {
        	return classType.getName();
        }
        return null;
	}
	
	public Boolean isInterface(String typeName) {
		JClassType classType = getClassType(typeName);
		return classType.isInterface() != null;
	}
	
	public JClassType getClassType(String typeName) {
		if (typeName.indexOf('.') < 0) {
			typeName = factory.getCreatedPackage() + "." + typeName;			
		}
        TypeOracle typeOracle = context.getTypeOracle();
        return typeOracle.findType(typeName);
	}
	
	public String getSQLiteType(JType returnType) {
		String sqliteType = null;
		JPrimitiveType primitiveReturnType = returnType.isPrimitive();
		if (primitiveReturnType != null) {
			if (primitiveReturnType == JPrimitiveType.INT)
			{
				sqliteType = "INTEGER";
			}
			else if (primitiveReturnType == JPrimitiveType.BOOLEAN)
			{
				sqliteType = "BOOL"; 
			}
			else {
				sqliteType = primitiveReturnType.getSimpleSourceName().toUpperCase();
			}
		}
		else
		{
			String returnTypeName = returnType.getSimpleSourceName();
			if (returnTypeName.equals("String")) {
				sqliteType = "TEXT";				
			}
			else if (isSubclassOf(returnType, "JSONValue")) {
				sqliteType = "JSON";
			}
			else {
				sqliteType = returnTypeName.toUpperCase();
			}
		}
		return sqliteType;
	}
	
	public void inspectType(String typeName, List<JMethod> getters, List<JMethod> hasManyRels, List<JMethod> hasOneRels) throws UnableToCompleteException {
		JClassType classType = getClassType(typeName);
		for (JMethod method : classType.getOverridableMethods()) {
			if (!method.isAbstract()) {
				continue;
			}
			String methodName = method.getName();
			if (methodName.startsWith("get")) {
				String propertyName = methodName.substring(3);	// getId() is reserved. 
				if (propertyName.equals("Id")) {
					continue;
				}
				JType returnType = method.getReturnType();
				String returnTypeName = returnType.getSimpleSourceName();
				if (returnType.isPrimitive() != null && !returnTypeName.equals("long")
						|| returnTypeName.equals("String")
						|| returnTypeName.equals("Date")
						|| isSubclassOf(returnType, "JSONValue")) {
					getters.add(method);
					continue;
				}
				if (returnTypeName.equals("long")) {
					logger.log(TreeLogger.ERROR,
		                	"GWT JSNI does not support 'long' as return type on getter '" + methodName + "'. Use 'double' instead.");
						throw new UnableToCompleteException();
				}
				
				if (returnTypeName.startsWith("Collection")) {
					hasManyRels.add(method);
					continue;
				}
				
				if (isSubclassOf(returnType, "Persistable")) {
					hasOneRels.add(method);
					continue;
				}

				logger.log(TreeLogger.ERROR,
                	"Unsupported return type '" + returnTypeName + "' on getter '" + methodName + "'.");
				throw new UnableToCompleteException();
			}
			else {
				// TODO: check if method is a setter. ignore if so, error if not.
			}
				
		}
	}
	
	public boolean isSubclassOf(JType type, String superClass) {
		JClassType classType = type.isInterface();
		if (classType == null) {
			classType = type.isClass();
		}
		if (classType == null) {
			return false;
		}
		Set<? extends JClassType> superTypes = classType.getFlattenedSupertypeHierarchy();
		for (JClassType superType : superTypes) {
			if (superType.getSimpleSourceName().equals(superClass)) {
				return true;
			}
		}
		return false;
	}
	
	public String getGenericTypeShortName(String collectionTypeName) {
		int beginIndex = collectionTypeName.indexOf('<') + 1;
		int endIndex = collectionTypeName.indexOf('>');
		String genericTypeName = collectionTypeName.substring(beginIndex, endIndex);
		beginIndex = genericTypeName.lastIndexOf('.');
		if (beginIndex >= 0) {
			return genericTypeName.substring(beginIndex + 1);
		}
		else {
			return genericTypeName;
		}		
	}
}
