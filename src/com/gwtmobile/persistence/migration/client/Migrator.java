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
 * Time: 1:06 PM
 */

package com.gwtmobile.persistence.migration.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Migrator {

	public interface Migration {
		JavaScriptObject getMigration();
	}

	public interface MigrationCallback {
		void onSuccess();
	}

	private JavaScriptObject persistence;

	public Migrator() {
		persistence = getPersistence();
	}

	public void init(MigrationCallback callback) {
		init(persistence, callback);
	}

	private native JavaScriptObject getPersistence()/*-{
		return $wnd.persistence;
	}-*/;

	private native void init(JavaScriptObject persistence, MigrationCallback callback)/*-{
		persistence.migrations
				.init(function() {
					callback.@com.gwtmobile.persistence.migration.client.Migrator.MigrationCallback::onSuccess()();
				});
	}-*/;

	public void defineMigration(int version, Migration migration) {
		defineMigration(persistence, version, migration.getMigration());
	}

	public void migrate() {
		migrate(persistence);
	}

	public void migrate(MigrationCallback callback) {
		migrate(persistence, callback);
	}

	public void migrate(int version, MigrationCallback callback) {
		migrate(persistence, version, callback);

	}

	private native void defineMigration(JavaScriptObject persistence,
			int version, JavaScriptObject migration)/*-{
		persistence.defineMigration(version, migration);
	}-*/;

	private native void migrate(JavaScriptObject persistence)/*-{
		persistence.migrate();
	}-*/;

	private native void migrate(JavaScriptObject persistence, MigrationCallback callback)/*-{
		persistence
				.migrate(function() {
					callback.@com.gwtmobile.persistence.migration.client.Migrator.MigrationCallback::onSuccess()();
				});
	}-*/;

	private native void migrate(JavaScriptObject persistence, int version, MigrationCallback callback)/*-{
		persistence
				.migrate(
						version,
						function() {
							callback.@com.gwtmobile.persistence.migration.client.Migrator.MigrationCallback::onSuccess()();
						});
	}-*/;
}
