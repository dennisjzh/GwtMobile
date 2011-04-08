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

public class EntityGenerator implements ClassGenerator {

	final GenUtils utils;
	final String requestedClassName;
	final String generatedClassName;
	final List<JMethod> getters;
	final List<JMethod> hasManyRels;
	final List<JMethod> hasOneRels;

	public EntityGenerator(GenUtils utils, String requestedClassName, String generatedClassName, List<JMethod> getters, List<JMethod> hasManyRels, List<JMethod> hasOneRels) {
		this.utils = utils;
		this.requestedClassName = requestedClassName;
		this.generatedClassName = generatedClassName;
		this.getters = getters;
		this.hasManyRels = hasManyRels;
		this.hasOneRels = hasOneRels;
	}

	@Override
	public void classSetup() {
		AddImports();
		setSuperClass();
	}
	private void AddImports() {
		utils.factory().addImport("java.util.HashMap");
		utils.factory().addImport("java.util.Date");
		utils.factory().addImport("com.google.gwt.core.client.JavaScriptObject");
		utils.factory().addImport("com.google.gwt.core.client.JsArrayString");
		utils.factory().addImport("com.google.gwt.core.client.GWT");
		utils.factory().addImport("com.google.gwt.json.client.*");
		utils.factory().addImport("com.gwtmobile.persistence.client.*");
	}
	private void setSuperClass() {
		utils.factory().setSuperclass("EntityInternal<" + requestedClassName + ">");
	}			
	private void AddVariables() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Persistence.define(\"%s\", new HashMap<String, String> () {{\n", requestedClassName));
		for (JMethod getter : getters) {
			String propertyName = getter.getName().substring(3);
			String propertyType = utils.getSQLiteType(getter.getReturnType());
			sb.append(String.format("\tput(\"%s\", \"%s\");\n", propertyName, propertyType));
		}
		sb.append("}});");
		utils.addVariable("private static", "JavaScriptObject", "nativeEntity", sb.toString());
		for (JMethod hasManyRel : hasManyRels) {
			String hasManyRelName = hasManyRel.getName().substring(3);
			String hasManyRelEntity = utils.getGenericTypeShortName(hasManyRel.getReturnType().getParameterizedQualifiedSourceName());
			utils.addVariable("private static", "EntityInternal<" + hasManyRelEntity + ">", 
					"hasMany" + hasManyRelName, "GWT.create(" + hasManyRelEntity + ".class)");
		}
		for (JMethod hasOneRel : hasOneRels) {
            String hasOneRelColName = hasOneRel.getName().substring(3);
            String hasOneRelEntity = hasOneRel.getReturnType().getSimpleSourceName();
			utils.addVariable("private static", "EntityInternal<" + hasOneRelEntity + ">", 
					"hasOne" + hasOneRelColName, "GWT.create(" + hasOneRelEntity + ".class)");
		}
	}
	@Override
	public void generateClass() {				
		AddVariables();
		utils.genrateStaticConstructor(new MethodGenerator() {

			@Override
			public void generateMethod() {
				for (JMethod hasManyRel : hasManyRels) {
					String hasManyRelName = hasManyRel.getName().substring(3);
					utils.println("hasMany(nativeEntity, \"%s\", hasMany%s.getNativeObject(), hasMany%s.getInverseRelationName(\"%s\"));", 
							hasManyRelName, hasManyRelName, hasManyRelName, requestedClassName);
				}
				for (JMethod hasOneRel : hasOneRels) {
					String hasOneRelColName = hasOneRel.getName().substring(3);
					utils.println("hasOne(nativeEntity, \"%s\", hasOne%s.getNativeObject());", 
							hasOneRelColName, hasOneRelColName);
				}
			}
		});
		
		utils.generateMethod("public", "JavaScriptObject", "getNativeObject", null,
				new MethodGenerator() {
			@Override
			public void generateMethod() {
				utils.println("return nativeEntity;");
			}
		});

		utils.generateMethod("public", requestedClassName, "newInstance", 
				new String[][] {{"JavaScriptObject", "nativeObject"}},
				new MethodGenerator() {
					@Override
					public void generateMethod() {
						utils.println("return new %sImpl(nativeObject);", requestedClassName);  
					}
				});

		utils.generateMethod("public", requestedClassName, "newInstance", null,
				new MethodGenerator() {
			@Override
			public void generateMethod() {
				utils.println("JavaScriptObject nativeObject = %s.newInstanceNative(nativeEntity, Persistence.getAutoAdd());", generatedClassName);
				utils.println("return new %sImpl(nativeObject);", requestedClassName);
			}
		});

		utils.generateMethod("public", requestedClassName + "[]", "newInstanceArray", 
				new String[][] {{"int", "size"}},
				new MethodGenerator() {
					@Override
					public void generateMethod() {
						utils.println("return new %s[size];", requestedClassName);
					}
				});
		
		utils.generateMethod("public", "Collection<" + requestedClassName + ">", "all", null,
				new MethodGenerator() {
			@Override
			public void generateMethod() {
				utils.println("JavaScriptObject nativeObject = %s.all(nativeEntity);", generatedClassName);
				utils.println("return new CollectionImpl(nativeObject, this);");
			}
		});

		utils.generateMethod("public", "Collection<" + requestedClassName + ">", "newCollection",
				new String[][] {{"JavaScriptObject", "nativeObject"}},
				new MethodGenerator() {
			@Override
			public void generateMethod() {
				utils.println("return new CollectionImpl(nativeObject, this);", requestedClassName);
			}
		});

		utils.generateMethod("public", "String", "getInverseRelationName",
				new String[][] {{"String", "rel"}}, 
				new MethodGenerator(){
					@Override
					public void generateMethod() {
						for (JMethod hasManyRel : hasManyRels) {
							String hasManyRelName = hasManyRel.getName().substring(3);
							String hasManyRelEntity = utils.getGenericTypeShortName(hasManyRel.getReturnType().getParameterizedQualifiedSourceName());
							utils.println("if (rel.equals(\"%s\")) {", hasManyRelEntity);
							utils.sw().indent();
							utils.println("return \"%s\";", hasManyRelName);
							utils.sw().outdent();
							utils.println("}");
						}
						for (JMethod hasOneRel : hasOneRels) {
							String hasOneRelationName = hasOneRel.getName().substring(3);
							String hasOneRelationEntity = hasOneRel.getName().substring(3);
							utils.println("if (rel.equals(\"%s\")) {", hasOneRelationEntity);
							utils.sw().indent();
							utils.println("return \"%s\";", hasOneRelationName);
							utils.sw().outdent();
							utils.println("}");
						}
						utils.println("return null;");
					}});

		utils.generateMethod("public", "String", "getEntityName", null,
				new MethodGenerator() {
			@Override
			public void generateMethod() {
				utils.println("return \"%s\";", requestedClassName);
			}
		});

		utils.generateNativeMethod("private static", "JavaScriptObject", "newInstanceNative",
				new String[][] {
					{"JavaScriptObject", "nativeEntity"}, 
					{"Boolean", "autoAdd"}},
				new MethodGenerator() {
					@Override
					public void generateMethod() {
						utils.println("var instance = new nativeEntity();");
						utils.println("if (autoAdd) {");
						utils.println("\t$wnd.persistence.add(instance);");
						utils.println("}");
						utils.println("return instance;");
					}
				});

		utils.generateNativeMethod("private static", "JavaScriptObject", "all",
				new String[][] {{"JavaScriptObject", "nativeEntity"}},
				new MethodGenerator() {
					@Override
					public void generateMethod() {
						utils.println("return nativeEntity.all();");
					}
				});

		utils.generateNativeMethod("private static", "void", "hasMany",
				new String[][] {
					{"JavaScriptObject", "nativeEntity"},
					{"String", "collName"},
					{"JavaScriptObject", "otherEntity"},
					{"String", "invRel"}},
				new MethodGenerator() {
					@Override
					public void generateMethod() {
						utils.println("nativeEntity.hasMany(collName, otherEntity, invRel);");
					}
				});

		utils.generateNativeMethod("private static", "void", "hasOne",
				new String[][] {
					{"JavaScriptObject", "nativeEntity"},
					{"String", "collName"},
					{"JavaScriptObject", "otherEntity"}},
				new MethodGenerator() {
					@Override
					public void generateMethod() {
						utils.println("nativeEntity.hasOne(collName, otherEntity);");
					}
				});

		String superClass = null;
		String[] interfaces = null;
		if (utils.isInterface(requestedClassName)) {
			interfaces = new String[]{"PersistableInternal", requestedClassName};
		}
		else {
			superClass = requestedClassName;
			interfaces = new String[]{"PersistableInternal"};
		}
			
		
		utils.generateInnerClass("private", requestedClassName + "Impl", superClass, interfaces,
				new InstanceGenerator(utils, requestedClassName, generatedClassName, getters, hasManyRels, hasOneRels));

	}
}
