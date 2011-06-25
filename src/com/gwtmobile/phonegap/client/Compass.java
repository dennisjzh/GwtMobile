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

public class Compass {
	
	public static native void getCurrentHeading(Callback callback) /*-{
		$wnd.navigator.compass.getCurrentHeading(function(heading) {
	    	callback.@com.gwtmobile.phonegap.client.Compass.Callback::onSuccess(F)(heading);
	    }, function() {
	    	callback.@com.gwtmobile.phonegap.client.Compass.Callback::onError()();
	    });
	}-*/;

    public static String watchHeading(Callback callback) {
    	return watchHeading(callback, (JavaScriptObject)null);
	}
	
    public static String watchHeading(Callback callback, Options options) {
    	return watchHeading(callback, options.getOptions());
	}
	
    private static native String watchHeading(Callback callback, JavaScriptObject options) /*-{
	    var id = $wnd.navigator.compass.watchHeading(function(heading) {
	    	callback.@com.gwtmobile.phonegap.client.Compass.Callback::onSuccess(F)(heading);
	    }, function() {
	    	callback.@com.gwtmobile.phonegap.client.Compass.Callback::onError()();
	    }, options);
	    return id;
	}-*/;

	public static native void clearWatch(String watchId) /*-{
		$wnd.navigator.compass.clearWatch(watchId);
	}-*/;
        

    public interface Callback {
    	void onSuccess(float heading);
    	void onError();
    }
    
    public static class Options {

		private Options self = this;
		private JavaScriptObject options = JavaScriptObject.createObject();
    	
    	public native Options frequency(int f) /*-{
    		this.@com.gwtmobile.phonegap.client.Compass.Options::options.frequency = f;
    		return this.@com.gwtmobile.phonegap.client.Compass.Options::self;
    	}-*/;
    	
    	private JavaScriptObject getOptions() {
			return options;    		
    	}

    }

}
