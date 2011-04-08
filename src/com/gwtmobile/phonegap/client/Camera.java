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

public class Camera {

	public static void getPicture(Callback callback) {
		getPicture(callback, (JavaScriptObject)null);
	}

	public static void getPicture(Callback callback, Options options) {
		getPicture(callback, options.getOptions());
	}

	private native static void getPicture(Callback callback, JavaScriptObject options) /*-{
		$wnd.navigator.camera.getPicture(function(imageData) {
	    	callback.@com.gwtmobile.phonegap.client.Camera.Callback::onSuccess(Ljava/lang/String;)(imageData);
	    }, function(message) {
	    	callback.@com.gwtmobile.phonegap.client.Camera.Callback::onError(Ljava/lang/String;)(message);
		}, options);
	}-*/;
	
	public interface Callback {
		void onSuccess(String imageData);
		void onError(String message); 
	}
	
	public enum DestinationType { DATA_URL, FILE_URI }

	public enum SourceType { PHOTOLIBRARY, CAMERA, SAVEDPHOTOALBUM }
	
    public static class Options {
    	Options self = this;
    	JavaScriptObject options = JavaScriptObject.createObject();
    	
    	public native Options quality(int q) /*-{
    		this.@com.gwtmobile.phonegap.client.Camera.Options::options.quality = q;
    		return this.@com.gwtmobile.phonegap.client.Camera.Options::self;
		}-*/;

    	// TODO: is there way to pass enum to javascript without using this method?
    	public Options destinationType(DestinationType d) {
    		return destinationType(d.ordinal());
    	}
    	
    	private native Options destinationType(int d) /*-{
			this.@com.gwtmobile.phonegap.client.Camera.Options::options.destinationType = d;			
    		return this.@com.gwtmobile.phonegap.client.Camera.Options::self;
		}-*/;

    	public Options sourceType(SourceType s) {
    		return sourceType(s.ordinal());
    	}
    	
    	private native Options sourceType(int s) /*-{
			this.@com.gwtmobile.phonegap.client.Camera.Options::options.sourceType = s;			
    		return this.@com.gwtmobile.phonegap.client.Camera.Options::self;
		}-*/;

    	public native Options allowEdit(boolean b) /*-{
			this.@com.gwtmobile.phonegap.client.Camera.Options::options.allowEdit = b;			
    		return this.@com.gwtmobile.phonegap.client.Camera.Options::self;
		}-*/;

		private JavaScriptObject getOptions() {
			return options;    		
    	}
    }
}
