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

public class Media extends JavaScriptObject {
	
	protected Media() {}
	
	public static native Media newInstance(String src, Callback callback) /*-{
		return new $wnd.Media(src, function() {
			callback.@com.gwtmobile.phonegap.client.Media.Callback::onSuccess()();
		}, function(error) {
			callback.@com.gwtmobile.phonegap.client.Media.Callback::onError(Lcom/gwtmobile/phonegap/client/Media$MediaError;)(error);
		});
	}-*/;

	public native final void play() /*-{
		this.play();
	}-*/;

	public native final void pause() /*-{
		this.pause();
	}-*/;

	public native final void stop() /*-{
		this.stop();
	}-*/;
	
	public native final void release() /*-{
		if ($wnd.navigator.userAgent.indexOf("Android") != -1) {
			this.release();
		} 
	}-*/;

	public native final void startRecord() /*-{
		if (typeof this.startRecord == 'function') {
			this.startRecord();
		}
		else {
			this.startAudioRecord();
		}
	}-*/;

	public native final void stopRecord() /*-{
		if (typeof this.stopRecord == 'function') {
			this.stopRecord();
		}
		else {
			this.stopAudioRecord();
		}
	}-*/;

	public native final void getCurrentPosition(PositionCallback callback) /*-{
		this.getCurrentPosition(function(position) {
			callback.@com.gwtmobile.phonegap.client.Media.PositionCallback::onSuccess(I)(position);
		}, function(error) {
			callback.@com.gwtmobile.phonegap.client.Media.PositionCallback::onError(Lcom/gwtmobile/phonegap/client/Media$MediaError;)(error);
		});
	}-*/;

	public native final int getDuration() /*-{
		return this.getDuration();
	}-*/;

    public interface Callback {
    	void onSuccess();
    	void onError(MediaError error);
    }
    
    public interface PositionCallback {
    	void onSuccess(int position);
    	void onError(MediaError error);
    }
    
    public enum MediaErrorCode {
    	MEDIA_ERR_ABORTED, MEDIA_ERR_NETWORK, MEDIA_ERR_DECODE, MEDIA_ERR_NONE_SUPPORTED };
    
    public static class MediaError extends JavaScriptObject {
    	
    	protected MediaError() {}
    	
    	// TODO: PhoneGap Android version does not follow its doc.
    	public final MediaErrorCode getCode() {
    		return MediaErrorCode.values()[getCodeNative()];
    	}
    	
    	private native final int getCodeNative() /*-{
			return this.code;
		}-*/;
    	    	
    	public native final String getMessage() /*-{
			return this.message;
		}-*/;
	    	
    }
    

}
