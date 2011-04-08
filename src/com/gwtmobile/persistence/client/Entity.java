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

public interface Entity<T extends Persistable> {	
	public T newInstance();
	public Collection<T> all();
	public void load(Transaction transaction, String id, ScalarCallback<T> callback);
	public void load(String id, ScalarCallback<T> callback);
	// It sucks that Java does not support generics on primitive types...
	public void findBy(Transaction transaction, String property, boolean value, ScalarCallback<T> callback);
	public void findBy(String property, boolean value, ScalarCallback<T> callback);
	public void findBy(Transaction transaction, String property, char value, ScalarCallback<T> callback);
	public void findBy(String property, char value, ScalarCallback<T> callback);
	// also cover byte and short.
	public void findBy(Transaction transaction, String property, int value, ScalarCallback<T> callback);
	public void findBy(String property, int value, ScalarCallback<T> callback);
	// also cover float and long.	
	public void findBy(Transaction transaction, String property, double value, ScalarCallback<T> callback);
	public void findBy(String property, double value, ScalarCallback<T> callback);
	public void findBy(Transaction transaction, String property, String value, ScalarCallback<T> callback);
	public void findBy(String property, String value, ScalarCallback<T> callback);
	public void findBy(Transaction transaction, String property, Date value, ScalarCallback<T> callback);
	public void findBy(String property, Date value, ScalarCallback<T> callback);
	public void index(String column);
	public void index(String column, boolean unique);
	public void index(String[] columns);
	public void index(String[] columns, boolean unique);
}
