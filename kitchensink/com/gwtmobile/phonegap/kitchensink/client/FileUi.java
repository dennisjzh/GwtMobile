/* Copyright (c) 2010 Zhihua (Dennis) Jiang
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

package com.gwtmobile.phonegap.kitchensink.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.phonegap.client.File;
import com.gwtmobile.phonegap.client.File.Callback;
import com.gwtmobile.phonegap.client.File.Event;
import com.gwtmobile.phonegap.client.File.FileError;
import com.gwtmobile.phonegap.client.File.FileMgrCallback;
import com.gwtmobile.phonegap.client.File.FileReader;
import com.gwtmobile.phonegap.client.File.FileWriter;
import com.gwtmobile.phonegap.client.File.FreeDiskSpaceCallback;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class FileUi extends Page {

	private static FileUiUiBinder uiBinder = GWT.create(FileUiUiBinder.class);
	
	@UiField HTML text;
	FileWriter writer;
	FileReader reader;
	String dirName;
	String fileName;
	
	interface FileUiUiBinder extends UiBinder<Widget, FileUi> {
	}

	public FileUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				init();
			}
		});
	}

	private void init() {
		text.setHTML("");
		for (String path : File.getRootPaths()) {
			text.setHTML(text.getHTML() + path + "<br>");
		}
		dirName = File.getRootPaths()[0] + "/gwtmobile-phonegap/";
		fileName = dirName + "kitchensink-file.txt";
		writer = File.newWriterInstance(fileName, true);
		reader = File.newReaderInstance();

		Callback callback = new Callback() {			
			@Override
			public void onEvent(Event evt) {
				text.setHTML("Event Type: " + evt.getType() + "<br/>" + 
						"FileName: " + evt.getTarget().getFileName() + "<br/>" +  
						"Result: " + evt.getTarget().getResult() + "<br/>" +  
						(evt.getType().equals("error") ? ("Error: " + evt.getTarget().getError().getCode() + "<br/>") : "") +  
						text.getText());
			}
		};
		
		reader.onLoad(callback);
		reader.onLoadStart(callback);
		reader.onLoadEnd(callback);
		reader.onProgress(callback);
		reader.onAbort(callback);
		reader.onError(callback);
		
		writer.onWriteStart(callback);
		writer.onWriteEnd(callback);
		writer.onWrite(callback);
		writer.onProgress(callback);
		writer.onAbort(callback);
		writer.onError(callback);
	}
	
    @UiHandler("list0")
	void onList0SelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		createDirectory();
    		break;
    	case 1:
    		testDirectoryExists();
    		break;
    	case 2:
    		testFileExists();
    		break;
    	case 3:
    		deleteDirectory();
    		break;
    	case 4:
    		deleteFile();
    		break;
    	case 5:
    		getFreeDiskSpace();
    		break;
    	}
    }

    @UiHandler("list1")
	void onList1SelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		write();
    		break;
    	case 1:
    		truncate();
    		break;
    	case 2:
    		seek();
    		break;
    	case 3:
    		readAsDataURL();
    		break;
    	case 4:
    		readAsText();
    		break;
    	case 5:
    		abort();
    		break;
    	}
    }

    public void write() {
		writer.write("Hello from gwtmobile-phonegap<br/>");
	}

    public void truncate() {
		writer.truncate(20);
	}

    public void seek() {
		writer.seek(10);
	}

    public void readAsDataURL() {
		reader.readAsDataURL(fileName);
	}

    public void readAsText() {
		reader.readAsText(fileName);
	}

    public void abort() {
		writer.abort();
	}

    public void testFileExists() {
		File.testFileExists(fileName, new FileMgrCallback() {			
			@Override
			public void onSuccess(boolean success) {
				text.setHTML("File: " + fileName + "<br/>FileExists: " + success);
			}			
			@Override
			public void onError(FileError error) {
				text.setHTML(error + "");
			}
		});
	}

    public void testDirectoryExists() {
		File.testDirectoryExists(dirName, new FileMgrCallback() {			
			@Override
			public void onSuccess(boolean success) {
				text.setHTML("Dir: " + dirName + "<br/>DirExists: " + success);
			}			
			@Override
			public void onError(FileError error) {
				text.setHTML(error + "");
			}
		});
	}

    public void createDirectory() {
		File.createDirectory(dirName, new FileMgrCallback() {			
			@Override
			public void onSuccess(boolean success) {
				text.setHTML("Dir: " + dirName + "<br/>Create Dir: " + success);
			}			
			@Override
			public void onError(FileError error) {
				text.setHTML(error + "");
			}
		});
	}

    public void deleteDirectory() {
		File.deleteDirectory(dirName, new FileMgrCallback() {			
			@Override
			public void onSuccess(boolean success) {
				text.setHTML("Dir: " + dirName + "<br/>Delete Dir: " + success);
			}			
			@Override
			public void onError(FileError error) {
				text.setHTML(error + "");
			}
		});
	}

    public void deleteFile() {
		File.deleteFile(fileName, new FileMgrCallback() {			
			@Override
			public void onSuccess(boolean success) {
				text.setHTML("File: " + fileName + "<br/>Delete file: " + success);
			}			
			@Override
			public void onError(FileError error) {
				text.setHTML(error + "");
			}
		});
	}

    public void getFreeDiskSpace() {
		File.getFreeDiskSpace(new FreeDiskSpaceCallback() {			
			@Override
			public void onSuccess(double freeDiskSpace) {
				text.setHTML("Free Disk Space: " + freeDiskSpace);
			}			
			@Override
			public void onError(FileError error) {
				text.setHTML(error + "");
			}
		});
	}

}
