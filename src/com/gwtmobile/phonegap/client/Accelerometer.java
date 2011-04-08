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

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

public class Accelerometer {
	
	public static native void getCurrentAcceleration(Callback callback) /*-{
		$wnd.navigator.accelerometer.getCurrentAcceleration(function(acceleration) {
	    	callback.@com.gwtmobile.phonegap.client.Accelerometer.Callback::onSuccess(Lcom/gwtmobile/phonegap/client/Accelerometer$Acceleration;)(acceleration);
	    }, function() {
	    	callback.@com.gwtmobile.phonegap.client.Accelerometer.Callback::onError()();
	    });
	}-*/;

    public static String watchAcceleration(Callback callback) {
    	return watchAcceleration(callback, (JavaScriptObject)null);
	}
	
    public static String watchAcceleration(Callback callback, Options options) {
    	return watchAcceleration(callback, options.getOptions());
	}
	
    private static native String watchAcceleration(Callback callback, JavaScriptObject options) /*-{
	    var id = $wnd.navigator.accelerometer.watchAcceleration(function(acceleration) {
	    	callback.@com.gwtmobile.phonegap.client.Accelerometer.Callback::onSuccess(Lcom/gwtmobile/phonegap/client/Accelerometer$Acceleration;)(acceleration);
	    }, function() {
	    	callback.@com.gwtmobile.phonegap.client.Accelerometer.Callback::onError()();
	    }, options);
	    return id;
	}-*/;

	public static native void clearWatch(String watchId) /*-{
		$wnd.navigator.accelerometer.clearWatch(watchId);
	}-*/;
        

    public interface Callback {
    	void onSuccess(Acceleration accel);
    	void onError();
    }
    
    public static class Acceleration extends JavaScriptObject {
    	protected Acceleration() {};
    	
    	public native final int getX() /*-{
			return this.x;
		}-*/;
	
    	public native final int getY() /*-{
			return this.y;
		}-*/;
	
    	public native final int getZ() /*-{
			return this.z;
		}-*/;

    	public final Date getTimeStamp() {
    		return new Date((long)getTimeStampNative());
    	}
    
    	private native final double getTimeStampNative() /*-{
			return this.timestamp;
		}-*/;
    }
    
    public static class Options {

    	@SuppressWarnings("unused")
		private Options self = this;
		private JavaScriptObject options = JavaScriptObject.createObject();
    	
    	public native Options frequency(int f) /*-{
    		this.@com.gwtmobile.phonegap.client.Accelerometer.Options::options.frequency = f;
    		return this.@com.gwtmobile.phonegap.client.Accelerometer.Options::self;
    	}-*/;
    	
    	private JavaScriptObject getOptions() {
			return options;    		
    	}

    }

}
