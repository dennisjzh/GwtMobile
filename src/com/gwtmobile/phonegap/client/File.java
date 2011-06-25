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
import com.google.gwt.core.client.JsArrayString;

public class File {
	
	protected File() {}
	
	public static native FileReader newReaderInstance() /*-{
		return new $wnd.FileReader();
	}-*/;
	
	public static native FileWriter newWriterInstance(String filePath) /*-{
		return new $wnd.FileWriter(filePath);
	}-*/;

	public static native FileWriter newWriterInstance(String filePath, boolean append) /*-{
		return new $wnd.FileWriter(filePath, append);
	}-*/;
	
	// fileMgr methods are not in phonegap doc yet.
	
	public static String[] getRootPaths() {
		JsArrayString jsArray = getRootPathsNative();
		String[] array = new String[jsArray.length()];
		for (int i = 0; i < jsArray.length(); i++) {
			array[i] = jsArray.get(i);
		}
		return array;
	}
	
	private static native JsArrayString getRootPathsNative() /*-{
		return $wnd.navigator.fileMgr.getRootPaths();
	}-*/;

	public static String[] getFileBasePaths() {
		JsArrayString jsArray = getFileBasePathsNative();
		String[] array = new String[jsArray.length()];
		for (int i = 0; i < jsArray.length(); i++) {
			array[i] = jsArray.get(i);
		}
		return array;
	}

	private static native JsArrayString getFileBasePathsNative() /*-{
		return $wnd.navigator.fileMgr.getFileBasePathsNative();
	}-*/;

	public static native void testFileExists(String fileName, FileMgrCallback callback) /*-{
		$wnd.navigator.fileMgr.testFileExists(fileName, function(success){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onSuccess(Z)(success);
		}, function(error){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
		});
	}-*/;

	public static native void testDirectoryExists(String dirName, FileMgrCallback callback) /*-{
		$wnd.navigator.fileMgr.testDirectoryExists(dirName, function(success){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onSuccess(Z)(success);
		}, function(error){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
		});
	}-*/;

	public static native void createDirectory(String dirName, FileMgrCallback callback) /*-{
		$wnd.navigator.fileMgr.createDirectory(dirName, function(success){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onSuccess(Z)(success);
		}, function(error){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
		});
	}-*/;

	public static native void deleteDirectory(String dirName, FileMgrCallback callback) /*-{
		$wnd.navigator.fileMgr.deleteDirectory(dirName, function(success){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onSuccess(Z)(success);
		}, function(error){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
		});
	}-*/;

	public static native void deleteFile(String fileName, FileMgrCallback callback) /*-{
		$wnd.navigator.fileMgr.deleteFile(fileName, function(success){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onSuccess(Z)(success);
		}, function(error){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
		});
	}-*/;

	public static native void getFreeDiskSpace(FreeDiskSpaceCallback callback) /*-{
		$wnd.navigator.fileMgr.getFreeDiskSpace(function(freeDiskSpace){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FreeDiskSpaceCallback::onSuccess(D)(freeDiskSpace);
		}, function(error){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FreeDiskSpaceCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
		});
	}-*/;


	public static class FileBase extends JavaScriptObject {
		
		protected FileBase() {};
		
		protected final native int getReadyStateNative() /*-{
			return this.readyState;
		}-*/;

		// TODO: phonegap doc does not mention this property on the reader. but the code seems to differ.
		public final native String getFileName() /*-{
			return this.fileName;
		}-*/;

		// move this method to base so onload event does not have to cast target to a reader.
		// writer does not have this property and this method should not be called on a writer.
		public final native String getResult() /*-{
			return this.result;
		}-*/;

		public final native FileError getError() /*-{
			return this.error;
		}-*/;

		public final native void onProgress(Callback callback) /*-{
			this.onprogress = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;
		
		public final native void onAbort(Callback callback) /*-{
			this.onabort = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;
	
		public final native void onError(Callback callback) /*-{
			this.onerror = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;	
	
		public final native void abort() /*-{
			this.abort();
		}-*/;
	}

	public static class FileReader extends FileBase {
		
		protected FileReader() {};
		
		public enum ReadyState { EMPTY, LOADING, DONE };
		
		public final ReadyState getReadyState() {
			return ReadyState.values()[getReadyStateNative()];
		}
		
		public final native void onLoadStart(Callback callback) /*-{
			this.onloadstart = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;
	
		public final native void onLoad(Callback callback) /*-{
			this.onload = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;

		public final native void onLoadEnd(Callback callback) /*-{
			this.onloadend = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;

		public final native void readAsDataURL(String fileName) /*-{
			this.readAsDataURL(fileName);
		}-*/;

		public final native void readAsText(String fileName) /*-{
			this.readAsText(fileName);
		}-*/;

	}

	public static class FileWriter extends FileBase {
		
		protected FileWriter() {};

		public enum ReadyState { INIT, WRITING, DONE };
		
		public final ReadyState getReadyState() {
			return ReadyState.values()[getReadyStateNative()];
		}
		
		public final long getLength() {
			return (long) getLengthNative();
		};

		private final native double getLengthNative() /*-{
			return this.length;
		}-*/;

		public final long getPosition() {
			return (long) getPositionNative();
		};

		private final native double getPositionNative() /*-{
			return this.position;
		}-*/;
		
		public final native void onWriteStart(Callback callback) /*-{
			this.onwritestart = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;

		public final native void onWrite(Callback callback) /*-{
			this.onwrite = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;

		public final native void onWriteEnd(Callback callback) /*-{
			this.onwriteend = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;

		public final void seek(long offset) {
			seek((double)offset);
		}
		
		private final native void seek(double offset) /*-{
			this.seek(offset);
		}-*/;
		
		public final void truncate(long size) {
			truncate((double)size);
		}
		
		private final native void truncate(double size) /*-{
			this.truncate(size);
		}-*/;
		
		public final native void write(String text) /*-{
			this.write(text);
		}-*/;

	}
	
	public enum FileErrorCode {
		NO_ERROR,	//addition to phonegap codes to fill the 0 position.
		NOT_FOUND_ERR,
		SECURITY_ERR,
		ABORT_ERR,
		NOT_READABLE_ERR,
		ENCODING_ERR,
		NO_MODIFICATION_ALLOWED_ERR,
		INVALID_STATE_ERR,
		SYNTAX_ERR
	};
	
	public static class FileError extends JavaScriptObject {
		
		protected FileError() {};
		
		public final FileErrorCode getCode() {
			return FileErrorCode.values()[getCodeNative()];
		}
		
		private final native int getCodeNative() /*-{
			// Hey phonegap, where is the consistency?
			return this.code || this;
		}-*/;
	}
	
	public interface Callback {
		void onEvent(Event evt);
	}
	
	public interface FileMgrCallback {
		void onSuccess(boolean success);
		void onError(FileError error);
	}
	
	public interface FreeDiskSpaceCallback {
		void onSuccess(double freeDiskSpace);
		void onError(FileError error);
	}
	
	public static class Event extends JavaScriptObject {

		protected Event() {};
		
		public final native String getType() /*-{
			return this.type;
		}-*/;
	
		public final native FileBase getTarget() /*-{
			return this.target;
		}-*/;
	
		
	}

}
