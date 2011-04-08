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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


public class CollectionImpl<T extends Persistable> implements Collection<T> {
	protected JavaScriptObject nativeObject;
	protected EntityInternal<T> entity;


	public CollectionImpl(JavaScriptObject nativeObject, EntityInternal<T> entity) {
		this.nativeObject = nativeObject;
		this.entity = entity;
	}

	public Collection<T> newCollection(JavaScriptObject nativeObject) {
		return new CollectionImpl<T>(nativeObject, entity);
	}

	@Override
	public Collection<T> filter(String property, String operator, boolean value) {
		return newCollection(filter(nativeObject, property, operator, value));
	}
	private native JavaScriptObject filter(JavaScriptObject nativeObject, String property, String operator, boolean value) /*-{
	 	return nativeObject.filter(property, operator, value);
	}-*/;
	@Override
	public Collection<T> filter(String property, String operator, char value) {
		return newCollection(filter(nativeObject, property, operator, new String(new char[] {value})));
	}
	@Override
	public Collection<T> filter(String property, String operator, int value) {
		return newCollection(filter(nativeObject, property, operator, value));
	}
	private native JavaScriptObject filter(JavaScriptObject nativeObject, String property, String operator, int value) /*-{
		return nativeObject.filter(property, operator, value);
	}-*/;
	@Override
	public Collection<T> filter(String property, String operator, double value) {
		return newCollection(filter(nativeObject, property, operator, value));
	}
	private native JavaScriptObject filter(JavaScriptObject nativeObject, String property, String operator, double value) /*-{
		return nativeObject.filter(property, operator, value);
	}-*/;
	@Override
	public Collection<T> filter(String property, String operator, String value) {
		return newCollection(filter(nativeObject, property, operator, value));
	}
	private native JavaScriptObject filter(JavaScriptObject nativeObject, String property, String operator, String value) /*-{
		return nativeObject.filter(property, operator, value);
	}-*/;
	@Override
	public Collection<T> filter(String property, String operator, Date value) {
		return newCollection(filter(nativeObject, property, operator, (double)value.getTime()));
	}

	@Override
	public Collection<T> order(String property, boolean ascending) {
		return newCollection(order(nativeObject, property, ascending));
	}
	private native JavaScriptObject order(JavaScriptObject nativeObject, String property, boolean ascending) /*-{
		return nativeObject.order(property, ascending);
	}-*/;

	@Override
	public Collection<T> limit(int n) {
		return newCollection(limit(nativeObject, n));
	}
	private native JavaScriptObject limit(JavaScriptObject nativeObject, int n) /*-{
		return nativeObject.limit(n);
	}-*/;

	@Override
	public Collection<T> skip(int n) {
		return newCollection(skip(nativeObject, n));
	}
	private native JavaScriptObject skip(JavaScriptObject nativeObject, int n) /*-{
		return nativeObject.skip(n);
	}-*/;

	@Override
	public Collection<T> prefetch(String rel) {
		return newCollection(prefetch(nativeObject, rel));
	}
	private native JavaScriptObject prefetch(JavaScriptObject nativeObject, String rel) /*-{
		return nativeObject.prefetch(rel);
	}-*/;

	@Override
	public void add(T obj) {
		add(nativeObject, ((PersistableInternal)obj).getNativeObject());
	}
	private native void add(JavaScriptObject nativeObject, JavaScriptObject obj) /*-{
		nativeObject.add(obj);
	}-*/;

	@Override
	public void remove(T obj) {
		remove(nativeObject, ((PersistableInternal)obj).getNativeObject());
	}
	private native void remove(JavaScriptObject nativeObject, JavaScriptObject obj) /*-{
		nativeObject.remove(obj);
	}-*/;

	@Override
	public void destroyAll(Transaction tx, Callback callback) {
		destroyAll(nativeObject, tx, callback);
	}
	@Override
	public void destroyAll(Callback callback) {
		destroyAll(nativeObject, null, callback);
	}
	private native void destroyAll(JavaScriptObject nativeObject, Transaction tx, Callback callback) /*-{
		nativeObject.destroyAll(tx, function() {
			callback.@com.gwtmobile.persistence.client.Callback::onSuccess()();
		})
	}-*/;	

	@Override
	public void count(Transaction tx, ScalarCallback<Integer> callback) {
		count(nativeObject, this, tx, callback);
	}
	@Override
	public void count(ScalarCallback<Integer> callback) {
		count(nativeObject, this, null, callback);
	}
	private native void count(JavaScriptObject nativeObject, CollectionImpl<T> self, Transaction tx, ScalarCallback<Integer> callback) /*-{
		nativeObject.count(tx, function(n) {
			self.@com.gwtmobile.persistence.client.CollectionImpl::processCallback(ILcom/gwtmobile/persistence/client/ScalarCallback;)(n, callback);
		})
	}-*/;

	@Override
	public void list(Transaction tx, CollectionCallback<T> callback) {
		list(nativeObject, this, tx, callback);
	}
	@Override
	public void list(CollectionCallback<T> callback) {
		list(nativeObject, this, null, callback);
	}
	private native void list(JavaScriptObject nativeObject, CollectionImpl<T> self, Transaction tx, CollectionCallback<T> callback) /*-{
		nativeObject.list(tx, function(results) {
			self.@com.gwtmobile.persistence.client.CollectionImpl::processCallback(Lcom/google/gwt/core/client/JsArray;Lcom/gwtmobile/persistence/client/CollectionCallback;)(results, callback);
		})
	}-*/;

	@Override
	public void each(Transaction tx, ScalarCallback<T> callback) {
		each(nativeObject, this, tx, callback);
	}
	@Override
	public void each(ScalarCallback<T> callback) {
		each(nativeObject, this, null, callback);
	}
	@Override
	public void forEach(Transaction tx, ScalarCallback<T> callback) {
		each(nativeObject, this, tx, callback);
	}
	@Override
	public void forEach(ScalarCallback<T> callback) {
		each(nativeObject, this, null, callback);
	}
	private native void each(JavaScriptObject nativeObject, CollectionImpl<T> self, Transaction tx, ScalarCallback<T> callback) /*-{
		nativeObject.each(tx, function(result) {
			self.@com.gwtmobile.persistence.client.CollectionImpl::processCallback(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/gwtmobile/persistence/client/ScalarCallback;)(result, callback);
		})
	}-*/;	

	@Override
	public void one(Transaction tx, ScalarCallback<T> callback) {
		one(nativeObject, this, tx, callback);
	}
	@Override
	public void one(ScalarCallback<T> callback) {
		one(nativeObject, this, null, callback);
	}
	private native void one(JavaScriptObject nativeObject, CollectionImpl<T> self, Transaction tx, ScalarCallback<T> callback) /*-{
		nativeObject.one(tx, function(result) {
			self.@com.gwtmobile.persistence.client.CollectionImpl::processCallback(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/gwtmobile/persistence/client/ScalarCallback;)(result, callback);
		})
	}-*/;		


	@SuppressWarnings("unused")
	private void processCallback(JsArray<JavaScriptObject> results, CollectionCallback<T> callback) {
		T[] array = entity.newInstanceArray(results.length());
		for (int i = 0; i < array.length; i++) {
			array[i] = entity.newInstance(results.get(i));
		}
		callback.onSuccess(array);
	}

	@SuppressWarnings("unused")
	private void processCallback(JavaScriptObject result, ScalarCallback<T> callback) {
		callback.onSuccess(entity.newInstance(result));
	}

	@SuppressWarnings("unused")
	private void processCallback(int result, ScalarCallback<Integer> callback) {
		callback.onSuccess(result);
	}
	
	public Collection<T> or(Filter filter) {
		return newCollection(or(nativeObject, filter));
	}
	private native JavaScriptObject or(JavaScriptObject nativeObject, JavaScriptObject filter) /*-{
		return nativeObject.or(filter);
	}-*/;
	
	public Collection<T> and(Filter filter) {
		return newCollection(and(nativeObject, filter));
	}
	private native JavaScriptObject and(JavaScriptObject nativeObject, JavaScriptObject filter) /*-{
		return nativeObject.and(filter);
	}-*/;
}