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

package com.gwtmobile.phonegap.client;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


public class Capture {

	public native static JsArray<ConfigurationData> getSupportedAudioModes() /*-{
		return $wnd.navigator.device.capture.supportedAudioModes;
	}-*/;
	
	public native static JsArray<ConfigurationData> getSupportedImageModes() /*-{
		return $wnd.navigator.device.capture.supportedImageModes;
	}-*/;
	
	public native static JsArray<ConfigurationData> getSupportedVideoModes() /*-{
		return $wnd.navigator.device.capture.supportedVideoModes;
	}-*/;
	
	
	public static class ConfigurationData extends JavaScriptObject {
			
		protected ConfigurationData() {}
		
		public final native String getType() /*-{
			return this.type;
		}-*/;

		public final native int getHeight() /*-{
			return this.height;
		}-*/;

		public final native int getWidth() /*-{
			return this.width;
		}-*/;
	}
	
	public static void captureAudio(CaptureCallback callback, CaptureOptions options) {
		captureAudio(callback, options.getOptions());
	}
	
	private native static void captureAudio(CaptureCallback callback, JavaScriptObject options) /*-{
		$wnd.navigator.device.capture.captureAudio(function(mediaFiles) {
	    	callback.@com.gwtmobile.phonegap.client.Capture.CaptureCallback::onSuccess(Lcom/google/gwt/core/client/JsArray;)(mediaFiles);
	    }, function(message) {
	    	callback.@com.gwtmobile.phonegap.client.Capture.CaptureCallback::onError(Lcom/gwtmobile/phonegap/client/Capture$CaptureError;)(message);
		}, options);
	}-*/;

	public static void captureImage(CaptureCallback callback, CaptureOptions options) {
		captureImage(callback, options.getOptions());
	}
	
	private native static void captureImage(CaptureCallback callback, JavaScriptObject options) /*-{
		$wnd.navigator.device.capture.captureImage(function(mediaFiles) {
	    	callback.@com.gwtmobile.phonegap.client.Capture.CaptureCallback::onSuccess(Lcom/google/gwt/core/client/JsArray;)(mediaFiles);
	    }, function(message) {
	    	callback.@com.gwtmobile.phonegap.client.Capture.CaptureCallback::onError(Lcom/gwtmobile/phonegap/client/Capture$CaptureError;)(message);
		}, options);
	}-*/;

	public static void captureVideo(CaptureCallback callback, CaptureOptions options) {
		captureVideo(callback, options.getOptions());
	}
	
	private native static void captureVideo(CaptureCallback callback, JavaScriptObject options) /*-{
		$wnd.navigator.device.capture.captureVideo(function(mediaFiles) {
	    	callback.@com.gwtmobile.phonegap.client.Capture.CaptureCallback::onSuccess(Lcom/google/gwt/core/client/JsArray;)(mediaFiles);
	    }, function(message) {
	    	callback.@com.gwtmobile.phonegap.client.Capture.CaptureCallback::onError(Lcom/gwtmobile/phonegap/client/Capture$CaptureError;)(message);
		}, options);
	}-*/;

	public interface CaptureCallback {
		void onSuccess(JsArray<MediaFile> mediaFiles);
		void onError(CaptureError error); 
	}
	
	public static class CaptureError extends JavaScriptObject {
		
		protected CaptureError() {};
		
		public final CaptureErrorCode getCode() {
			return CaptureErrorCode.values()[getCodeNative()];
		}
		
		private final native int getCodeNative() /*-{
			return this.code == 20 ? 4 : this.code;
		}-*/;
	}

	public enum CaptureErrorCode {
		CAPTURE_INTERNAL_ERR, 
		CAPTURE_APPLICATION_BUSY, 
		CAPTURE_INVALID_ARGUMENT, 
		CAPTURE_NO_MEDIA_FILES, 
		CAPTURE_NOT_SUPPORTED
	}

	public static class CaptureOptions {
		
		CaptureOptions self = this;
		JavaScriptObject options = JavaScriptObject.createObject();

		public native CaptureOptions limit(int limit) /*-{
			this.@com.gwtmobile.phonegap.client.Capture.CaptureOptions::options.limit = limit;
			return this.@com.gwtmobile.phonegap.client.Capture.CaptureOptions::self;
		}-*/;
		
		public native CaptureOptions duration(int duration) /*-{
			this.@com.gwtmobile.phonegap.client.Capture.CaptureOptions::options.duration = duration;
			return this.@com.gwtmobile.phonegap.client.Capture.CaptureOptions::self;
		}-*/;

		public native CaptureOptions mode(String mode) /*-{
			this.@com.gwtmobile.phonegap.client.Capture.CaptureOptions::options.mode = mode;
			return this.@com.gwtmobile.phonegap.client.Capture.CaptureOptions::self;
		}-*/;

		private JavaScriptObject getOptions() {
			return options;
		}
	}

	public static class MediaFile extends JavaScriptObject {
		
		protected MediaFile() {}
		
		public final native String getName() /*-{
			return this.name;
		}-*/;

		public final native String getFullPath() /*-{
			return this.fullPath;
		}-*/;
	
		public final native String getType() /*-{
			return this.type;
		}-*/;

		public final Date getLastModifiedDate() {
			return new Date(getLastModifiedDateNative() * 1000);
		};

		private final native int getLastModifiedDateNative() /*-{
			return this.lastModifiedDate;
		}-*/;

		public final native int getSize() /*-{
			return this.size;
		}-*/;

		public final native void getFormatData(GetFormatDataCallback callback) /*-{
			this.getFormatData(function(data) {
		    	callback.@com.gwtmobile.phonegap.client.Capture.GetFormatDataCallback::onSuccess(Lcom/gwtmobile/phonegap/client/Capture$MediaFileData;)(data);
		    }, function(message) {
		    	callback.@com.gwtmobile.phonegap.client.Capture.GetFormatDataCallback::onError(Ljava/lang/String;)(message);
			});
		}-*/;
	}
	
	public interface GetFormatDataCallback {
		void onSuccess(MediaFileData data);
		void onError(String message); 
	}

	public static class MediaFileData extends JavaScriptObject {
		
		protected MediaFileData() {}
		
		public final native String getCodecs() /*-{
			return this.codecs;
		}-*/;

		public final native int getBitrate() /*-{
			return this.bitrate;
		}-*/;
	
		public final native int getHeight() /*-{
			return this.height;
		}-*/;
	
		public final native int getWidth() /*-{
			return this.width;
		}-*/;

		public final native int getDuration() /*-{
			return this.duration;
		}-*/;
	}
	
}
