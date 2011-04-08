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

import java.util.List;

import com.google.gwt.core.ext.typeinfo.JMethod;

public class InstanceGenerator implements ClassGenerator {

	final GenUtils utils;
	final String requestedClassName;
	final String generatedClassName;
	final List<JMethod> getters;
	final List<JMethod> hasManyRels;
	final List<JMethod> hasOneRels;

	public InstanceGenerator(GenUtils utils, String requestedClassName, String generatedClassName, List<JMethod> getters, List<JMethod> hasManyRels, List<JMethod> hasOneRels) {
		this.utils = utils;
		this.requestedClassName = requestedClassName;
		this.generatedClassName = generatedClassName;
		this.getters = getters;
		this.hasManyRels = hasManyRels;
		this.hasOneRels = hasOneRels;
	}
	
	@Override
	public void classSetup() {
		utils.addVariable("private", "JavaScriptObject", "nativeObject");								
	}
	@Override
	public void generateClass() {
		utils.generateMethod("public", null, requestedClassName + "Impl", 
				new String[][] {{"JavaScriptObject", "nativeObject"}},
				new MethodGenerator() {
					@Override
					public void generateMethod() {
						utils.println("this.nativeObject = nativeObject;");
					}
				});

		utils.generateMethod("public", "JavaScriptObject", "getNativeObject", null,
				new MethodGenerator() {
			@Override
			public void generateMethod() {
				utils.println("return nativeObject;");
			}
		});
		
		utils.generateMethod("public", "String", "getId", null,
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("return getId(nativeObject);"); 
			}
		});
		utils.generateNativeMethod("private", "String", "getId", 
				new String[][]{{"JavaScriptObject", "nativeObject"}},
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("return nativeObject.id;"); 
			}
		});
		
		utils.generateMethod("public", "<T extends Persistable> void", "fetch",
				new String[][]{
				{"Transaction", "transaction"},
				{"Entity<T>", "entity"},
				{"ScalarCallback<T>", "callback"}},
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("EntityInternal<T> entityInternal = (EntityInternal<T>)entity;");
				utils.println("fetch(transaction, entityInternal.getEntityName(), entityInternal, callback, this, nativeObject);"); 
			}
		});
		utils.generateMethod("public", "<T extends Persistable> void", "fetch",
				new String[][]{
				{"Entity<T>", "entity"},
				{"ScalarCallback<T>", "callback"}},
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("fetch(null, entity, callback);"); 
			}
		});
		utils.generateNativeMethod("private", " <T extends Persistable> void", "fetch",
				new String[][]{
				{"Transaction", "transaction"},
				{"String", "property"},
				{"EntityInternal<T>", "entity"},
				{"ScalarCallback<T>", "callback"},
				{requestedClassName + "Impl", "self"},
				{"JavaScriptObject", "nativeObject"}},
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("nativeObject.fetch($wnd.persistence, transaction, property, function(result) {");
				utils.sw().indent();
				utils.println("self.@%s.%s.%sImpl::processFetchCallback(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/gwtmobile/persistence/client/EntityInternal;Lcom/gwtmobile/persistence/client/ScalarCallback;)(result, entity, callback);", 
						utils.getPackageName(requestedClassName), generatedClassName, requestedClassName);
				utils.sw().outdent();
				utils.println("});");
			}
		});
		utils.generateMethod("private", "<T extends Persistable> void", "processFetchCallback",
				new String[][]{
				{"JavaScriptObject", "result"},
				{"EntityInternal<T>", "entity"},
				{"ScalarCallback<T>", "callback"}},
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("callback.onSuccess(entity.newInstance(result));");
			}
		});
		
		utils.generateMethod("public", "void", "selectJSON",
				new String[][]{
				{"Transaction", "transaction"},
				{"String[]", "propertySpec"},
				{"ScalarCallback<String>", "callback"}},
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("JsArrayString jsArray = null;");
				utils.println("if (propertySpec != null) {");
				utils.sw().indent();				
				utils.println("jsArray = (JsArrayString) JavaScriptObject.createArray();");
				utils.println("for (int i = 0; i < propertySpec.length; i++) {");
				utils.println("String spec = propertySpec[i];");
				utils.println("jsArray.set(i, spec);");
				utils.sw().outdent();				
				utils.println("}");
				utils.sw().outdent();				
				utils.println("}");
				utils.println("selectJSON(transaction, jsArray, callback, this, nativeObject);");
			}
		});
		utils.generateMethod("public", "void", "selectJSON",
				new String[][]{
				{"String[]", "propertySpec"},
				{"ScalarCallback<String>", "callback"}},
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("selectJSON(null, propertySpec, callback);");
			}
		});
		utils.generateNativeMethod("private", "void", "selectJSON",
				new String[][]{
				{"Transaction", "transaction"},
				{"JsArrayString", "propertySpec"},
				{"ScalarCallback<String>", "callback"},
				{requestedClassName + "Impl", "self"},
				{"JavaScriptObject", "nativeObject"}},
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("nativeObject.selectJSON(transaction, propertySpec, function(result) {");
				utils.sw().indent();
				utils.println("var resultJson = $wnd.JSON.stringify(result);");
				utils.println("self.@%s.%s.%sImpl::processStringCallback(Ljava/lang/String;Lcom/gwtmobile/persistence/client/ScalarCallback;)(resultJson, callback);", 
						utils.getPackageName(requestedClassName), generatedClassName, requestedClassName);
				utils.sw().outdent();
				utils.println("});");
			}
		});
		utils.generateMethod("private", "void", "processStringCallback",
				new String[][]{
				{"String", "result"},
				{"ScalarCallback<String>", "callback"}},
				new MethodGenerator() {			
			@Override
			public void generateMethod() {
				utils.println("callback.onSuccess(result);");
			}
		});
		
		for (final JMethod getter : getters) {
			//TODO: is getter method always public?
			final String returnTypeName = getter.getReturnType().getSimpleSourceName();
			final boolean isDateReturnType = returnTypeName.equals("Date");
			final boolean isPrimitiveReturnType = getter.getReturnType().isPrimitive() != null;
			final boolean isCharReturnType = returnTypeName.equals("char");
			final boolean isJsonReturnType = utils.isSubclassOf(getter.getReturnType(), "JSONValue");
			utils.generateMethod("public", returnTypeName, getter.getName(), 
					null, new MethodGenerator(){
						@Override
						public void generateMethod() {
							if (isDateReturnType) {
								utils.println("long value = (long)" + getter.getName() + "(nativeObject);");
								//TODO: assume that the actual date is not "the epoch" for now.
								utils.println("return (value == 0) ? null : new Date(value);"); 																			
							}
							else if (isJsonReturnType) {
								utils.println("String value = " + getter.getName() + "(nativeObject);");
								utils.println("return (value == null) ? null : (%s) JSONParser.parse(value);", returnTypeName);
							}
							else {
								utils.println("return " + getter.getName() + "(nativeObject);"); 											
							}
						}});
			utils.generateNativeMethod("private", 
					isDateReturnType ? "double" :
						isJsonReturnType ? "String" :
						returnTypeName, getter.getName(), 
					new String[][]{{"JavaScriptObject", "nativeObject"}},
					new MethodGenerator(){
						@Override
						public void generateMethod() {
							utils.println("var value = nativeObject." + getter.getName().substring(3) + ";");
							//CHAR in SQLite has TEXT affinity.  
							if (isCharReturnType) {
								utils.println("return (value == null || value.length == 0) ? 0 : value.charCodeAt(0);"); 											
							}
							else if (isPrimitiveReturnType) {
								utils.println("return (value == null) ? 0 : value;"); 											
							}
							else if (isDateReturnType) {
								//return Date.getTime() as double. Can't pass long to Java.
								utils.println("return (value == null) ? 0 : value.getTime();"); 											
							}
							else if (isJsonReturnType) {
								utils.println("return (value == null) ? null : JSON.stringify(value);"); 											
							}
							else {
								utils.println("return value;");
							}
						}});
			utils.generateMethod("public", "void", "set" + getter.getName().substring(3), 
					new String[][]{{returnTypeName, "value"}},
					new MethodGenerator(){
						@Override
						public void generateMethod() {
							if (isDateReturnType) {
								utils.println("set" + getter.getName().substring(3) + "(value == null ? 0 : value.getTime(), nativeObject);");
							}
							else if (isJsonReturnType) {
								utils.println("set" + getter.getName().substring(3) + "(value == null ? null : value.toString(), nativeObject);");
							}
							else {
								utils.println("set" + getter.getName().substring(3) + "(value, nativeObject);");
							}
						}});
			utils.generateNativeMethod("private", "void", "set" + getter.getName().substring(3), 
					new String[][]{
						{isDateReturnType ? "double" : 
							isJsonReturnType ? "String" : 
							returnTypeName, "value"},
						{"JavaScriptObject", "nativeObject"}},
					new MethodGenerator(){
						@Override
						public void generateMethod() {
							if (isDateReturnType) {
								utils.println("nativeObject." + getter.getName().substring(3) + " = (value == 0) ? null : new Date(value);");
							}
							else if (isJsonReturnType) {
								utils.println("nativeObject." + getter.getName().substring(3) + " = (value == null) ? null : JSON.parse(value);");
							}
							else if (isCharReturnType) {
								utils.println("nativeObject." + getter.getName().substring(3) + " = String.fromCharCode(value);");
							}
							else {
								utils.println("nativeObject." + getter.getName().substring(3) + " = value;");
							}
						}});
		}
		
		for (final JMethod hasManyRel : hasManyRels) {
			utils.generateMethod("public", hasManyRel.getReturnType().getParameterizedQualifiedSourceName(), hasManyRel.getName(), 
					null, new MethodGenerator(){
						@Override
						public void generateMethod() {
							utils.println("return hasMany" + hasManyRel.getName().substring(3) + 
									".newCollection(" + hasManyRel.getName() + "(nativeObject));"); 											
						}});							
			utils.generateNativeMethod("private", "JavaScriptObject", hasManyRel.getName(), 
					new String[][]{{"JavaScriptObject", "nativeObject"}},
					new MethodGenerator(){
						@Override
						public void generateMethod() {
							utils.println("return nativeObject." + hasManyRel.getName().substring(3) + ";"); 											
						}});							
		}
		
		for (final JMethod hasOneRel : hasOneRels) {
			utils.generateMethod("public", hasOneRel.getReturnType().getSimpleSourceName(), hasOneRel.getName(), 
					null, new MethodGenerator(){
						@Override
						public void generateMethod() {
							utils.println("return hasOne" + hasOneRel.getName().substring(3) + 
									".newInstance(" + hasOneRel.getName() + "(nativeObject));"); 											
						}});
			utils.generateNativeMethod("private", "JavaScriptObject", hasOneRel.getName(), 
					new String[][]{{"JavaScriptObject", "nativeObject"}},
					new MethodGenerator(){
						@Override
						public void generateMethod() {
							utils.println("return nativeObject." + hasOneRel.getName().substring(3) + ";"); 											
						}});
			utils.generateMethod("public", "void", "set" + hasOneRel.getName().substring(3), 
					new String[][]{{hasOneRel.getReturnType().getSimpleSourceName(), "value"}},
					new MethodGenerator(){
						@Override
						public void generateMethod() {
							utils.println("set" + hasOneRel.getName().substring(3) + "(((PersistableInternal)(value)).getNativeObject(), nativeObject);"); 											
						}});
			utils.generateNativeMethod("private", "void", "set" + hasOneRel.getName().substring(3), 
					new String[][]{
						{"JavaScriptObject", "value"},
						{"JavaScriptObject", "nativeObject"}},
					new MethodGenerator(){
						@Override
						public void generateMethod() {
							utils.println("nativeObject." + hasOneRel.getName().substring(3) + " = value;"); 											
						}});
		}
	}	


}
