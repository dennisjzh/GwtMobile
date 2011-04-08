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

public class Device {

	public native static String getName() /*-{
		return $wnd.device.name;
	}-*/;

	public native static String getPhoneGap() /*-{
		return $wnd.device.phonegap;
	}-*/;

	public native static String getPlatform() /*-{
		return $wnd.device.platform;
	}-*/;

	public native static String getUUID() /*-{
		return $wnd.device.uuid;
	}-*/;
	
	public native static String getVersion() /*-{
		return $wnd.device.version;
	}-*/;

	// below are Android specific APIs.
	
	public native static void overrideBackButton() /*-{
		if ($wnd.device && typeof $wnd.device.overrideBackButton == "function") {
			$wnd.device.overrideBackButton();
		}
	}-*/;
	
	public native static void resetBackButton() /*-{
		if ($wnd.device && typeof $wnd.device.resetBackButton == "function") {
			$wnd.device.resetBackButton();
		}
	}-*/;

	public native static void exitApp() /*-{
		if ($wnd.device && typeof $wnd.device.exitApp == "function") {
			$wnd.device.exitApp();
		}
	}-*/;

}
