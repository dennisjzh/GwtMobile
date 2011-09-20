/*
 * Copyright 2011 Zhihua (Dennis) Jiang
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

/**
 * Author: smithimage (http://github.com/smithimage)
 * Date: 7/26/11
 * Time: 11:44 PM
 */

package com.gwtmobile.persistence.migration.client;

import com.google.gwt.core.client.JavaScriptObject;

public class TestMigration {

	public void test() {
		Migrator migrator = new Migrator();

		migrator.defineMigration(1, new Migrator.Migration() {
			@Override
			public native JavaScriptObject getMigration()/*-{
				return {
					up : function() {
						this.createTable('Test', function(t) {
							t.text('name');
							t.text('description');
						});
					},
					down : function() {
						this.dropTable('Test');
					}
				}
			}-*/;
		});
	}
}
