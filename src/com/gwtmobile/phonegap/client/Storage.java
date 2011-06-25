/*
 * Copyright (c) 2011 Zhihua (Dennis) Jiang
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

package com.gwtmobile.phonegap.client;


public class Storage {

	public static class LocalStorage {
		
		public native static String key(int index) /*-{
			return $wnd.localStorage.key(index);
		}-*/;

		//TODO: other value types?
		public native static void setItem(String key, String value) /*-{
			$wnd.localStorage.setItem(key, value);
		}-*/;
		
		public native static String getItem(String key) /*-{
			return $wnd.localStorage.getItem(key);
		}-*/;
		
		public native static void removeItem(String key) /*-{
			$wnd.localStorage.removeItem(key);
		}-*/;

		public native static void clear() /*-{
			$wnd.localStorage.clear();
		}-*/;
	}


}
