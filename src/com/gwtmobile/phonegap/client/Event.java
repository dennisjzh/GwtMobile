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

public class Event {
	
	public native static void onDeviceReady(Callback callback) /*-{
		//Have to manually fire the event for iOS if device is already initialized.
		if ($wnd.navigator.userAgent.indexOf("Android") == -1 
			&& $wnd.device != null && $wnd.device.uuid != null) {
			callback.@com.gwtmobile.phonegap.client.Event.Callback::onEventFired()();
		}
		else {
		    $doc.addEventListener("deviceready", function() {
		    	callback.@com.gwtmobile.phonegap.client.Event.Callback::onEventFired()();
		    }, false);
		}
	}-*/;
	
	public native static void onPause(Callback callback) /*-{
	    $doc.addEventListener("pause", function() {
	    	callback.@com.gwtmobile.phonegap.client.Event.Callback::onEventFired()();
	    }, false);
	}-*/;

	public native static void onResume(Callback callback) /*-{
	    $doc.addEventListener("resume", function() {
	    	callback.@com.gwtmobile.phonegap.client.Event.Callback::onEventFired()();
	    }, false);
	}-*/;

	// below are Android specific events.
	
	public native static void onBackButton(Callback callback) /*-{
	    $doc.addEventListener("backbutton", function() {
	    	callback.@com.gwtmobile.phonegap.client.Event.Callback::onEventFired()();
	    }, false);
	}-*/;

	public native static void onMenuButton(Callback callback) /*-{
	    $doc.addEventListener("menubutton", function() {
	    	callback.@com.gwtmobile.phonegap.client.Event.Callback::onEventFired()();
	    }, false);
	}-*/;

	public native static void onSearchButton(Callback callback) /*-{
	    $doc.addEventListener("searchbutton", function() {
	    	callback.@com.gwtmobile.phonegap.client.Event.Callback::onEventFired()();
	    }, false);
	}-*/;

	public native static void onOnline(Callback callback) /*-{
	    $doc.addEventListener("online", function() {
	    	callback.@com.gwtmobile.phonegap.client.Event.Callback::onEventFired()();
	    }, false);
	}-*/;

	public native static void onOffline(Callback callback) /*-{
	    $doc.addEventListener("offline", function() {
	    	callback.@com.gwtmobile.phonegap.client.Event.Callback::onEventFired()();
	    }, false);
	}-*/;

	public interface Callback {
		public void onEventFired();
	}
	
}
