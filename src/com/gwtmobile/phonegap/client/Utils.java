/*
 * Copyright (c) 2010 - 2011 Zhihua (Dennis) Jiang
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

import com.google.gwt.user.client.Window;

public class Utils {

	public static native void Console(String msg) /*-{
		var log = $doc.getElementById('log');
		if (log) {
			log.innerHTML = msg + '<br/>' + log.innerHTML;
		}
		else {
          if ($wnd.console) {
              $wnd.console.log(msg);
          }
		}
	}-*/;

    public static boolean isAndroid() {
    	return Window.Navigator.getUserAgent().contains("Android");
    }
    
    public static boolean isIOS() {
    	return Window.Navigator.getUserAgent().contains("iPhone") || 
    			Window.Navigator.getUserAgent().contains("iPod");
    }
    
}
