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

import com.gwtmobile.ui.client.utils.Utils;



public class Event {
	
	static {
		//TODO: remove patch with 0.9.6
		if (Utils.isAndroid()) {
			patch();
		}
	}
    
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

	public interface Callback {
		public void onEventFired();
	}
	
	// The patch replaces instanceof Function with typeof 'function'
	private native static void patch() /*-{	
		if ($wnd.PhoneGap == null || $wnd.PhoneGap.Channel == null) {
			return;
		}

		$wnd.PhoneGap.Channel.prototype.subscribe = function(f, c, g) {
		    // need a function to call
		    if (f == null) { return; }
		
		    var func = f;
		    if (typeof c == "object" && typeof f == 'function') { func = PhoneGap.close(c, f); }
		
		    g = g || func.observer_guid || f.observer_guid || this.guid++;
		    func.observer_guid = g;
		    f.observer_guid = g;
		    this.handlers[g] = func;
		    return g;
		};
		
		$wnd.PhoneGap.Channel.prototype.subscribeOnce = function(f, c) {
		    var g = null;
		    var _this = this;
		    var m = function() {
		        f.apply(c || null, arguments);
		        _this.unsubscribe(g);
		    }
		    if (this.fired) {
			    if (typeof c == "object" && typeof f == 'function') { f = PhoneGap.close(c, f); }
		        f.apply(this, this.fireArgs);
		    } else {
		        g = this.subscribe(m);
		    }
		    return g;
		};
		
		$wnd.PhoneGap.Channel.prototype.unsubscribe = function(g) {
		    if (typeof g == 'function') { g = g.observer_guid; }
		    this.handlers[g] = null;
		    delete this.handlers[g];
		};				
		
		$wnd.PhoneGap.Channel.prototype.fire = function(e) {
		    if (this.enabled) {
		        var fail = false;
		        for (var item in this.handlers) {
		            var handler = this.handlers[item];
		            if (typeof handler == 'function') {
		                var rv = (handler.apply(this, arguments)==false);
		                fail = fail || rv;
		            }
		        }
		        this.fired = true;
		        this.fireArgs = arguments;
		        return !fail;
		    }
		    return true;
		};

		$wnd.PhoneGap.clone = function(obj) {
			if(!obj) { 
				return obj;
			}
		
			if(obj instanceof Array){
				var retVal = new Array();
				for(var i = 0; i < obj.length; ++i){
					retVal.push(PhoneGap.clone(obj[i]));
				}
				return retVal;
			}
		
			if (typeof obj == 'function') {
				return obj;
			}
		
			if(!(obj instanceof Object)){
				return obj;
			}
		
		    if (obj instanceof Date) {
		        return obj;
		    }
		    
			retVal = new Object();
			for(i in obj){
				if(!(i in retVal) || retVal[i] != obj[i]) {
					retVal[i] = PhoneGap.clone(obj[i]);
				}
			}
			return retVal;
		};

	}-*/;

}
