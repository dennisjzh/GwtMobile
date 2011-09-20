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
import com.google.gwt.core.client.JsArray;

public class FileMgr {
	
	protected FileMgr() {}

	// New instance creation
	public static native FileReader newFileReader() /*-{
		return new $wnd.FileReader();
	}-*/;
	
	public static native FileTransfer newFileTransfer() /*-{
		return new $wnd.FileTransfer();
	}-*/;

	public static void requestFileSystem(LocalFileSystem localFileSystem, FileSystemCallback callback)  {
		requestFileSystemNative(localFileSystem.ordinal(), callback);
	}
	
	private static native void requestFileSystemNative(int type, FileSystemCallback callback) /*-{
		$wnd.requestFileSystem(type, 0, function(success){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileSystemCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$FileSystem;)(success);
		}, function(error){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FileSystemCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
		});
	}-*/;

	public enum LocalFileSystem {
		TEMPORARY,
		PERSISTENT
	};

	// fileMgr methods are not in phonegap doc yet. Only selected APIs are wrapped.
	
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

	public static native void getFreeDiskSpace(FreeDiskSpaceCallback callback) /*-{
		$wnd.navigator.fileMgr.getFreeDiskSpace(function(freeDiskSpace){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FreeDiskSpaceCallback::onSuccess(D)(freeDiskSpace);
		}, function(error){
			callback.@com.gwtmobile.phonegap.client.FileMgr.FreeDiskSpaceCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
		});
	}-*/;

	
	// File System
	public static class FileSystem extends JavaScriptObject {

		protected FileSystem() {};
		
		public final native String getName() /*-{
			return this.name;
		}-*/;
		
		public final native DirectoryEntry getRoot() /*-{
			return this.root;
		}-*/;
	}
	
	// Directory Entry
	public static class DirectoryEntry extends Entry {

		protected DirectoryEntry() {};
		
		public final native DirectoryReader createReader() /*-{
			return this.createReader();
		}-*/;

		public final void getDirectory(String path, FileOptions options, EntryCallback callback) {
			getDirectoryNative(path, options == null ? null : options.getOptions(), callback);
		}
		
		private final native void getDirectoryNative(String path, JavaScriptObject options, EntryCallback callback) /*-{
			this.getDirectory(path, options, function(success){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$Entry;)(success);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;	
		
		public final void getFile(String path, FileOptions options, EntryCallback callback) {
			getFileNative(path, options.getOptions(), callback);
		}
		
		private final native void getFileNative(String path, JavaScriptObject options, EntryCallback callback) /*-{
			this.getFile(path, options, function(success){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$Entry;)(success);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;	
	
		public final native void removeRecursively(FileMgrCallback callback) /*-{
			this.removeRecursively(function(success){
				callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onSuccess(Z)(true);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;	
		
	}
	
	// Flags
	public static class FileOptions {
		FileOptions self = this;
		JavaScriptObject options = JavaScriptObject.createObject();

		public native FileOptions create(boolean create) /*-{
			this.@com.gwtmobile.phonegap.client.FileMgr.FileOptions::options.create = create;
			return this.@com.gwtmobile.phonegap.client.FileMgr.FileOptions::self;
		}-*/;

		public native FileOptions exclusive(boolean exclusive) /*-{
			this.@com.gwtmobile.phonegap.client.FileMgr.FileOptions::options.exclusive = exclusive;
			return this.@com.gwtmobile.phonegap.client.FileMgr.FileOptions::self;
		}-*/;

		private JavaScriptObject getOptions() {
			return options;
		}
	}


	// Directory Reader
	public static class DirectoryReader extends Entry {

		protected DirectoryReader() {};
		
		public final native void readEntries(ReaderCallback callback) /*-{
			this.readEntries(function(success){
				$entry(@com.gwtmobile.phonegap.client.FileMgr.DirectoryReader::processCallback(Lcom/google/gwt/core/client/JsArray;Lcom/gwtmobile/phonegap/client/FileMgr$ReaderCallback;)(success, callback));
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.ReaderCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;
		
		public final static void processCallback(JsArray<JavaScriptObject> results, ReaderCallback callback) {
			Entry[] entries = new Entry[results.length()];
			for (int i = 0; i < results.length(); i++) {
				entries[i] = (Entry) results.get(i);
			}
			callback.onSuccess(entries);
		}
	}
	
	// File Entry
	public static class FileEntry extends Entry {

		protected FileEntry() {};
		
		public final native FileWriter createWriter(FileWriterCallback callback) /*-{
			this.createWriter(function(success){
				callback.@com.gwtmobile.phonegap.client.FileMgr.FileWriterCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$FileWriter;)(success);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.FileWriterCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;	
	
		public final native File file(FileCallback callback) /*-{
			this.file(function(success){
				callback.@com.gwtmobile.phonegap.client.FileMgr.FileCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$File;)(success);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.FileCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;	

	}
	
	// File
	public final static class File extends JavaScriptObject {
		
		protected File() {};

		public final native String getName() /*-{
			return this.name;
		}-*/;
		
		public final native String getFullPath() /*-{
			return this.fullPath;
		}-*/;
	
		@SuppressWarnings("deprecation")
		public final Date getLastModifiedDate() {
			return new Date(Date.parse(getLastModifiedDateNative()));
		};
	
		private final native String getLastModifiedDateNative() /*-{
			return this.lastModifiedDate;
		}-*/;
	
		public final long getSize() {
			return (long) getSizeNative();
		};

		private final native double getSizeNative() /*-{
			return this.size;
		}-*/;

	
	}

	// Entry Base
	public static class Entry extends JavaScriptObject {

		protected Entry() {};
		
		public final native Boolean isFile() /*-{
			return this.isFile;
		}-*/;
		
		public final native Boolean isDirectory() /*-{
			return this.isDirectory;
		}-*/;

		public final native String getName() /*-{
			return this.name;
		}-*/;
		
		public final native String getFullPath() /*-{
			return this.fullPath;
		}-*/;
		
		public final native String getMetadata(MetadataCallback callback) /*-{
			this.getMetadata(function(success){
				callback.@com.gwtmobile.phonegap.client.FileMgr.MetadataCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$Metadata;)(success);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.MetadataCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;

		public final native void moveTo(Entry entry, String newName, EntryCallback callback) /*-{
			this.moveTo(entry, newName, function(success){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$Entry;)(success);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;

		public final native void copyTo(Entry entry, String newName, EntryCallback callback) /*-{
			this.copyTo(entry, newName, function(success){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$Entry;)(success);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;

		public final native String toURI() /*-{
			return this.toURI();
		}-*/;

		public final native void remove(FileMgrCallback callback) /*-{
			this.remove(function(){
				callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onSuccess(Z)(true);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;

		
		public final native void getParent(EntryCallback callback) /*-{
			this.getParent(function(success){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$Entry;)(success);
			}, function(error){
				callback.@com.gwtmobile.phonegap.client.FileMgr.EntryCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileError;)(error);
			});
		}-*/;

	}
	
	// Metadata
	public static class Metadata extends JavaScriptObject {

		protected Metadata() {};
		
		@SuppressWarnings("deprecation")
		public final Date getModificationTime() {
			return new Date(Date.parse(getModificationTimeNative()));
		};
	
		private final native String getModificationTimeNative() /*-{
			return this.modificationTime;
		}-*/;
	
	}

	//FileBase
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

		public final native void onProgress(EventCallback callback) /*-{
			this.onprogress = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;
		
		public final native void onAbort(EventCallback callback) /*-{
			this.onabort = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;
	
		public final native void onError(EventCallback callback) /*-{
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
		
		public final native void onLoadStart(EventCallback callback) /*-{
			this.onloadstart = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;
	
		public final native void onLoad(EventCallback callback) /*-{
			this.onload = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;

		public final native void onLoadEnd(EventCallback callback) /*-{
			this.onloadend = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;

		public final native void readAsDataURL(File file) /*-{
			this.readAsDataURL(file);
		}-*/;

		public final native void readAsText(File file) /*-{
			this.readAsText(file);
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
		
		public final native void onWriteStart(EventCallback callback) /*-{
			this.onwritestart = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;

		public final native void onWrite(EventCallback callback) /*-{
			this.onwrite = function(event) {
				callback.@com.gwtmobile.phonegap.client.FileMgr.EventCallback::onEvent(Lcom/gwtmobile/phonegap/client/FileMgr$Event;)(event);
			};
		}-*/;

		public final native void onWriteEnd(EventCallback callback) /*-{
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
		SYNTAX_ERR,
		INVALID_MODIFICATION_ERR,
		QUOTA_EXCEEDED_ERR,
		TYPE_MISMATCH_ERR,
		PATH_EXISTS_ERR
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
	
	public interface EventCallback {
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
	
	public interface MetadataCallback {
		void onSuccess(Metadata metadata);
		void onError(FileError error);
	}

	public interface EntryCallback {
		void onSuccess(Entry entry);
		void onError(FileError error);
	}
	
	public interface ReaderCallback {
		void onSuccess(Entry[] entries);
		void onError(FileError error);
	}
	
	public interface FileWriterCallback {
		void onSuccess(FileWriter writer);
		void onError(FileError error);
	}

	public interface FileCallback {
		void onSuccess(File file);
		void onError(FileError error);
	}
	
	public interface FileSystemCallback {
		void onSuccess(FileSystem result);
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

	public static class FileTransfer extends JavaScriptObject {

		protected FileTransfer() {};
		
		public final native String upload(String filePath, String serverUrl, FileTransferCallback callback, FileTransferOptions options) /*-{
			return this.upload(filePath, serverUrl, function(result) {
					callback.@com.gwtmobile.phonegap.client.FileMgr.FileTransferCallback::onSuccess(Lcom/gwtmobile/phonegap/client/FileMgr$FileTransferResult;)(result);
				}, function(error) {
					callback.@com.gwtmobile.phonegap.client.FileMgr.FileTransferCallback::onError(Lcom/gwtmobile/phonegap/client/FileMgr$FileTransferError;)(error);
				}, options);
		}-*/;
	
	}

	public interface FileTransferCallback {
		void onSuccess(FileTransferResult result);
		void onError(FileTransferError error);
	}

	public static class FileTransferResult extends JavaScriptObject {

		protected FileTransferResult() {};
		
		public final native int getBytesSent() /*-{
			return this.bytesSent;
		}-*/;
	
		public final native int getResponseCode() /*-{
			return this.responseCode;
		}-*/;
			
		public final native String getResponse() /*-{
			return this.response;
		}-*/;
		
	}

	public static class FileTransferError extends JavaScriptObject {

		protected FileTransferError() {};
		
		public final FileTransferErrorCode getCode() {
			return FileTransferErrorCode.values()[getCodeNative()];
		}
		
		private final native int getCodeNative() /*-{
			return this.code - 1; //code in phonegap is 1 based. 
		}-*/;
	}

	public enum FileTransferErrorCode {
		FILE_NOT_FOUND_ERR, 
		INVALID_URL_ERR, 
		CONNECTION_ERR
	}

	public static class FileTransferOptions extends JavaScriptObject {
		
		protected FileTransferOptions() {};

		public static FileTransferOptions newInstance() {
			return (FileTransferOptions) JavaScriptObject.createObject();
		}

		public final native FileTransferOptions fileKey(String fileKey) /*-{
			this.fileKey = fileKey;
			return this;
		}-*/;
	
		public final native FileTransferOptions fileName(String fileName) /*-{
			this.fileName = fileName;
			return this;
		}-*/;

		public final native FileTransferOptions mimeType(String mimeType) /*-{
			this.mimeType = mimeType;
			return this;
		}-*/;

		public final native FileTransferOptions params(String params) /*-{
			this.params = params;
			return this;
		}-*/;
	}


}
