/* Copyright (c) 2011 Zhihua (Dennis) Jiang
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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.phonegap.client.Camera;
import com.gwtmobile.phonegap.client.Camera.DestinationType;
import com.gwtmobile.phonegap.client.Camera.SourceType;
import com.gwtmobile.phonegap.client.FileMgr;
import com.gwtmobile.phonegap.client.FileMgr.FileTransfer;
import com.gwtmobile.phonegap.client.FileMgr.FileTransferCallback;
import com.gwtmobile.phonegap.client.FileMgr.FileTransferError;
import com.gwtmobile.phonegap.client.FileMgr.FileTransferOptions;
import com.gwtmobile.phonegap.client.FileMgr.FileTransferResult;
import com.gwtmobile.phonegap.client.Notification;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class FileTransferUi extends Page {

	@UiField HTML text;
	private static FileTransferUiUiBinder uiBinder = GWT.create(FileTransferUiUiBinder.class);
	
	interface FileTransferUiUiBinder extends UiBinder<Widget, FileTransferUi> {
	}

	public FileTransferUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		transferFile();
    		break;
    	}
    }

    void transferFile() {
    	Camera.getPicture(new Camera.Callback() {
			
			@Override
			public void onSuccess(String imageData) {
				String serverUrl = "http://gwtmobile-services.appspot.com/filetransfer";
		    	FileTransfer ft = FileMgr.newFileTransfer();
		    	final String lb = "<br/>";
		    	Notification.activityStart("Uploading", "Hang in there...");
		    	ft.upload(imageData, serverUrl, new FileTransferCallback() {
					@Override
					public void onSuccess(FileTransferResult result) {
						Notification.activityStop();
						text.setHTML("response code: " + result.getResponseCode() + lb +
								"bytes sent: " + result.getBytesSent() + lb +
								result.getResponse());
					}
					@Override
					public void onError(FileTransferError error) {
						Notification.activityStop();
						text.setHTML(error.getCode().toString());
					}
				}, FileTransferOptions.newInstance()
					.fileKey("file")
					.fileName(imageData.substring(imageData.lastIndexOf('/') + 1))
					.mimeType("image/jpeg"));
			}
			
			@Override
			public void onError(String message) {
				text.setHTML("Error: " + message);
			}
		}, new Camera.Options()
		.quality(50)
		.sourceType(SourceType.CAMERA)
		.destinationType(DestinationType.FILE_URI)
		.allowEdit(false));
    }
}
