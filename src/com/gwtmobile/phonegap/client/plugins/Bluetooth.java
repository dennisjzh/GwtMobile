/* Copyright (c) 2011 - Andago
 * 
 * author: Daniel Tizón
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


public class Bluetooth {
	
	
	public static native void isBTEnabled(Callback callback) /*-{
	$wnd.plugins.bluetooth.isBTEnabled(null, function(state) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onSuccess(Z)(state);
    },function(message) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onError(Ljava/lang/String;)(message);
    });
 }-*/;
	
	
	public static native void enableBT(Callback callback) /*-{
	$wnd.plugins.bluetooth.enableBT(null, function(state) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onSuccess(Z)(state);
    },function(message) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onError(Ljava/lang/String;)(message);
    });
 }-*/;
	
	
	
	public static native void disableBT(Callback callback) /*-{
	$wnd.plugins.bluetooth.disableBT(null, function(state) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onSuccess(Z)(state);
    },function(message) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onError(Ljava/lang/String;)(message);
    });
 }-*/;
	
	
	public static native void listDevices(sCallback callback) /*-{
	$wnd.plugins.bluetooth.listDevices(null, function(result) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.sCallback::onSuccess(Ljava/lang/String;)(result);
    },function(message) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.sCallback::onError(Ljava/lang/String;)(message);
    });
 }-*/;
	
	
	public static native void pairBT(String address,Callback callback) /*-{
	$wnd.plugins.bluetooth.pairBT(address, function(state) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onSuccess(Z)(state);
    },function(message) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onError(Ljava/lang/String;)(message);
    });
 }-*/;
	
	
	public static native void listBoundDevices(sCallback callback) /*-{
	$wnd.plugins.bluetooth.listBoundDevices(null, function(result) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.sCallback::onSuccess(Ljava/lang/String;)(result);
    },function(message) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.sCallback::onError(Ljava/lang/String;)(message);
    });
 }-*/;
	
	
	
	public static native void stopDiscovering(Callback callback) /*-{
	$wnd.plugins.bluetooth.stopDiscovering(null, function(state) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onSuccess(Z)(state);
    },function(message) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onError(Ljava/lang/String;)(message);
    });
 }-*/;
	
	
	
	public static native void isBound(String address,Callback callback) /*-{
	$wnd.plugins.bluetooth.isBound(address, function(state) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onSuccess(Z)(state);
    },function(message) {
    	callback.@com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback::onError(Ljava/lang/String;)(message);
    });
 }-*/;
	
	
   public interface Callback {
    	void onSuccess(boolean state);
    	void onError(String message); 
    }	
   
   
   public interface sCallback {
   	void onSuccess(String result);
   	void onError(String message); 
   }	


}
