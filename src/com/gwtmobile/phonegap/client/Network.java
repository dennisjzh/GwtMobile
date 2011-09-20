/*
 * Copyright (c) 2010 Zhihua (Dennis) Jiang
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

import com.google.gwt.core.client.JavaScriptObject;



public class Network {
	
	public native static ConnectionType getConnectionType() /*-{
		return $wnd.navigator.network.connection.type;
	}-*/;
	
	public static class ConnectionType extends JavaScriptObject {
		
		protected ConnectionType() {};
		
		public final native boolean isUnknown() /*-{
			return this == $wnd.Connection.UNKNOWN;
		}-*/;
	
		public final native boolean isEthernet() /*-{
			return this == $wnd.Connection.ETHERNET;
		}-*/;

		public final native boolean isWifi() /*-{
			return this == $wnd.Connection.WIFI;
		}-*/;

		public final native boolean isCell2G() /*-{
			return this == $wnd.Connection.CELL_2G;
		}-*/;

		public final native boolean isCell3G() /*-{
			return this == $wnd.Connection.CELL_3G;
		}-*/;

		public final native boolean isCell4G() /*-{
			return this == $wnd.Connection.CELL_4G;
		}-*/;

		public final native boolean isNone() /*-{
			return this == $wnd.Connection.NONE;
		}-*/;
	
	}
	
}
