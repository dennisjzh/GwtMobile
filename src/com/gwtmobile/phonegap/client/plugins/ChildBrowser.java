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

package com.gwtmobile.phonegap.client.plugins;

import com.gwtmobile.phonegap.client.Utils;

public class ChildBrowser {
	
	static {
		if (Utils.isIOS()) {
			install();
		}
	}

	public native static String showWebPage(String url, boolean usePhoneGap) /*-{
		return $wnd.plugins.childBrowser.showWebPage(url, usePhoneGap);
	}-*/;
	
	private native static void install() /*-{
		return $wnd.ChildBrowser.install();
	}-*/;

}
