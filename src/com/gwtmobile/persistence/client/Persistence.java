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

package com.gwtmobile.persistence.client;

import java.util.Date;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Persistence {
	
	private static Boolean _autoAdd = true;
	
	static {
		initializeConsole();
	}
	
	private static native void initializeConsole() /*-{
		if (!$wnd.console) $wnd.console = {};
		$wnd.console.log = $wnd.console.log || function(){};
		$wnd.console.warn = $wnd.console.warn || function(){};
		$wnd.console.error = $wnd.console.error || function(){};
		$wnd.console.info = $wnd.console.info || function(){};
	}-*/;
	
	public static Boolean getAutoAdd() {
		return _autoAdd;
	}

	public static void setAutoAdd(Boolean autoAdd) {
		_autoAdd = autoAdd;
	}

	public static native void connect(String dbname, String description, int size) /*-{
		$wnd.persistence.store.websql.config($wnd.persistence, dbname, description, size);
	}-*/;
	
	public static JavaScriptObject define(String entityName, Map<String, String> fields) {
		JavaScriptObject assoArray = Map2AssociativeArray(fields);
		return define(entityName, assoArray);
	};

	public static native void transaction(TransactionCallback callback) /*-{
		$wnd.persistence.transaction(
			function(transaction) {
				callback.@com.gwtmobile.persistence.client.TransactionCallback::onSuccess(Lcom/gwtmobile/persistence/client/Transaction;)(transaction);
			}
		);
	}-*/;
	
	public static native void schemaSync(TransactionCallback callback) /*-{
		$wnd.persistence.schemaSync(
			function(transaction) {
				callback.@com.gwtmobile.persistence.client.TransactionCallback::onSuccess(Lcom/gwtmobile/persistence/client/Transaction;)(transaction);
			}
		);
	}-*/;

	public static native void schemaSync(Callback callback) /*-{
		$wnd.persistence.schemaSync(
			function(transaction) {
				callback.@com.gwtmobile.persistence.client.Callback::onSuccess()();
			}
		);
	}-*/;

	public static native void schemaSync() /*-{
		$wnd.persistence.schemaSync();
	}-*/;
	
	public static void add(Persistable persistable) {
		add(((PersistableInternal)persistable).getNativeObject());
	}

	private static native void add(JavaScriptObject obj) /*-{
		$wnd.persistence.add(obj);
	}-*/;

	public static void remove(Persistable persistable) {
		remove(((PersistableInternal)persistable).getNativeObject());
	}

	private static native void remove(JavaScriptObject obj) /*-{
		$wnd.persistence.remove(obj);
	}-*/;

	public static native void flush(Transaction transaction, Callback callback) /*-{
		$wnd.persistence.flush(transaction,
			function() {
				callback.@com.gwtmobile.persistence.client.Callback::onSuccess()();
			}
		);
	}-*/;

	public static native void flush(Callback callback) /*-{
		$wnd.persistence.flush(null,
			function() {
				callback.@com.gwtmobile.persistence.client.Callback::onSuccess()();
			}
		);
	}-*/;

	public static native void flush() /*-{
		$wnd.persistence.flush();
	}-*/;

	public static native void reset(Transaction transaction) /*-{
		$wnd.persistence.reset(transaction);
	}-*/;

	public static native void reset() /*-{
		$wnd.persistence.transaction(function (transaction) {
			$wnd.persistence.reset(transaction);
		});
	}-*/;

	public static native void reset(Callback callback) /*-{
		$wnd.persistence.reset(
			function() {
				callback.@com.gwtmobile.persistence.client.Callback::onSuccess()();
			}
		);
	}-*/;

	@SuppressWarnings("unchecked")
	public static void dumpToJson(Transaction transaction, Entity<?>[] entities, ScalarCallback<String> callback) {
		JsArray<JavaScriptObject> entitiesArray = null;
		if (entities != null) {
			entitiesArray = (JsArray<JavaScriptObject>) JavaScriptObject.createArray();
			for (int i = 0; i < entities.length; i++) {
				EntityInternal<?> entity = (EntityInternal<?>) entities[i];
				entitiesArray.set(i, entity.getNativeObject());
			}
		}
		dumpToJsonNative(transaction, entitiesArray, callback);
	}
	public static void dumpToJson(Entity<?>[] entities, ScalarCallback<String> callback) {
		dumpToJson(null, entities, callback);
	}
	
	private static native void dumpToJsonNative(Transaction transaction, JsArray<JavaScriptObject> entities, ScalarCallback<String> callback) /*-{
		$wnd.persistence.dumpToJson(transaction, entities,
			function(result) {
				@com.gwtmobile.persistence.client.Persistence::processStringCallback(Ljava/lang/String;Lcom/gwtmobile/persistence/client/ScalarCallback;)(result, callback);
			}
		);
	}-*/;
	
	
	@SuppressWarnings("unused")
	private static void processStringCallback(String result, ScalarCallback<String> callback) {
		callback.onSuccess(result);
	}
	
	public static native void loadFromJson(Transaction transaction, String jsonDump, Callback callback) /*-{
		$wnd.persistence.loadFromJson(transaction, jsonDump,
			function() {
				callback.@com.gwtmobile.persistence.client.Callback::onSuccess()();
			}
		);
	}-*/;
	
	private static JavaScriptObject Map2AssociativeArray(
			Map<String, String> fields) {
		JavaScriptObject assoArray = JavaScriptObject.createObject();
		for (Map.Entry<String, String> field : fields.entrySet()) {
			setAssoArray(assoArray, field.getKey(), field.getValue());
		}
		return assoArray;
	}

	private static native void setAssoArray(JavaScriptObject assoArray, String key,
			String value) /*-{
		assoArray[key] = value;		
	}-*/;

	public static native JavaScriptObject define(String entityName, JavaScriptObject fields) /*-{
		return $wnd.persistence.define(entityName, fields);
	}-*/;
	
	public static Filter newPropertyFilter(String property, String operator, char value) {
		return newPropertyFilter(property, operator, new String(new char[] {value}));
	}

	public static native Filter newPropertyFilter(String property, String operator, String value) /*-{
		return new $wnd.persistence.PropertyFilter(property, operator, value);
	}-*/;

	public static native Filter newPropertyFilter(String property, String operator, boolean value) /*-{
		return new $wnd.persistence.PropertyFilter(property, operator, value);
	}-*/;
	
	public static native Filter newPropertyFilter(String property, String operator, int value) /*-{
		return new $wnd.persistence.PropertyFilter(property, operator, value);
	}-*/;

	public static native Filter newPropertyFilter(String property, String operator, double value) /*-{
		return new $wnd.persistence.PropertyFilter(property, operator, value);
	}-*/;

	public static Filter newPropertyFilter(String property, String operator, Date value) {
		return newPropertyFilter(property, operator, (double)value.getTime());
	}

	public static native Filter newAndFilter(Filter filter1, Filter filter2) /*-{
		return new $wnd.persistence.AndFilter(filter1, filter2);
	}-*/;

	public static native Filter newOrFilter(Filter filter1, Filter filter2) /*-{
		return new $wnd.persistence.OrFilter(filter1, filter2);
	}-*/;

}
