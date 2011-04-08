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

public interface Collection<T extends Persistable>{

	// Java amazingly does not support generics on primitive types...
	public Collection<T> filter(String property, String operator, boolean value);
	public Collection<T> filter(String property, String operator, char value);
	public Collection<T> filter(String property, String operator, int value);
	public Collection<T> filter(String property, String operator, double value);
	public Collection<T> filter(String property, String operator, String value);
	public Collection<T> filter(String property, String operator, Date value);
	public Collection<T> prefetch(String rel);
	public Collection<T> order(String property, boolean ascending);
	public Collection<T> limit(int n);
	public Collection<T> skip(int n);
	public void add(T obj);
	public void remove(T obj);
	public void list(Transaction tx, CollectionCallback<T> callback);
	public void list(CollectionCallback<T> callback);
	public void each(Transaction tx, ScalarCallback<T> callback);
	public void each(ScalarCallback<T> callback);
	public void forEach(Transaction tx, ScalarCallback<T> callback);
	public void forEach(ScalarCallback<T> callback);
	public void one(Transaction tx, ScalarCallback<T> callback);
	public void one(ScalarCallback<T> callback);
	public void destroyAll(Transaction tx, Callback callback);
	public void destroyAll(Callback callback);
	public void count(Transaction tx, ScalarCallback<Integer> callback);
	public void count(ScalarCallback<Integer> callback);
	public Collection<T> or(Filter filter);
	public Collection<T> and(Filter filter);
}
