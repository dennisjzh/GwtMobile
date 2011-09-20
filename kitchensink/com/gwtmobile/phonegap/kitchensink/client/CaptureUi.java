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
import com.google.gwt.core.client.JsArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.phonegap.client.Capture;
import com.gwtmobile.phonegap.client.Capture.CaptureCallback;
import com.gwtmobile.phonegap.client.Capture.CaptureError;
import com.gwtmobile.phonegap.client.Capture.CaptureOptions;
import com.gwtmobile.phonegap.client.Capture.MediaFile;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class CaptureUi extends Page {

	@UiField HTML text;
	private static CaptureUiUiBinder uiBinder = GWT.create(CaptureUiUiBinder.class);
	
	interface CaptureUiUiBinder extends UiBinder<Widget, CaptureUi> {
	}

	public CaptureUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		captureAudio();
    		break;
    	case 1:
    		captureImage();
    		break;
    	case 2:
    		captureVideo();
    		break;
    	}
    }

    CaptureCallback callback = new CaptureCallback() {			
		@Override
		public void onSuccess(JsArray<MediaFile> mediaFiles) {
			String lb = "<br/>";
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mediaFiles.length(); i++) {
				MediaFile mediaFile = mediaFiles.get(i);
				sb.append("Name:" + mediaFile.getName() + lb);
				sb.append("FullPath:" + mediaFile.getFullPath() + lb);
				sb.append("Type:" + mediaFile.getType() + lb);
				sb.append("LastModifiedDate:" + mediaFile.getLastModifiedDate() + lb);
				sb.append("size:" + mediaFile.getSize() + lb);
				sb.append(lb);
			}
			text.setHTML("Success: " + lb + sb.toString());
		}			
		@Override
		public void onError(CaptureError error) {
			text.setHTML("Error: " + error.getCode());
		}
	};

    void captureAudio() {
		Capture.captureAudio(callback, 
			new CaptureOptions()
				.limit(1)
				.duration(10)
		);
	}

    void captureImage() {
		Capture.captureImage(callback, 
			new CaptureOptions()
				.limit(1)
				.duration(10)
		);
	}

    void captureVideo() {
		Capture.captureVideo(callback, 
			new CaptureOptions()
				.limit(1)
				.duration(10)
		);
	}

}
