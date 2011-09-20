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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.phonegap.client.Media;
import com.gwtmobile.phonegap.client.Media.Callback;
import com.gwtmobile.phonegap.client.Media.MediaError;
import com.gwtmobile.phonegap.client.Media.PositionCallback;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.utils.Utils;

public class MediaUi extends Page {

	private static MediaUiUiBinder uiBinder = GWT.create(MediaUiUiBinder.class);
	
	Media media;
	@UiField HTML text;
	Timer timer;
	
	interface MediaUiUiBinder extends UiBinder<Widget, MediaUi> {
	}

	public MediaUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		
		String src = null;
		if (Utils.isAndroid()) {
			src = "myrecording.mp3";
		}
		else {
			src = "abcsong.wav";
		}
		media = Media.newInstance(src, new Callback() {			
			@Override
			public void onSuccess() {
				text.setHTML("Media Success");
				timer.cancel();
			}
			
			@Override
			public void onError(MediaError error) {
				text.setHTML("Media Error<br/>" +
						"Code: " + error.getCode() + "<br/>" +
						"Message: " + error.getMessage());
			}
		});
		
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		release();
	}
	
    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		startRecord();
    		break;
    	case 1:
    		stopRecord();
    		break;
    	case 2:
    		play();
    		break;
    	case 3:
    		pause();
    		break;
    	case 4:
    		stop();
    		break;
    	case 5:
    		release();
    		break;
    	}
    }


    public void play() {
    	text.setHTML("Playing...");
		media.play();
		timer = new Timer() {
			@Override
			public void run() {
				if (Utils.isAndroid()) {
					media.getCurrentPosition(new PositionCallback() {				
						@Override
						public void onSuccess(int position) {
							int duration = media.getDuration();
							text.setHTML(position + " / " + duration);
						}				
						@Override
						public void onError(MediaError error) {
							text.setHTML("Get Current Position Error<br/>" +
									"Code: " + error.getCode() + "<br/>" +
									"Message: " + error.getMessage());
						}
					});
				}
				else if (Utils.isIOS()) {
					text.setHTML(text.getHTML() + ".");
				}
			}
		};
		timer.scheduleRepeating(1000);
	}

    public void pause() {
		timer.cancel();
		media.pause();
    	text.setHTML("Paused");
	}

    public void stop() {
		timer.cancel();
		media.stop();
    	text.setHTML("Stopped");
	}
    
    public void startRecord() {
    	text.setHTML("Recording. Say or sing something.<br/>");    	
    	media.startRecord();
    	timer = new Timer() {
			@Override
			public void run() {
				text.setHTML(text.getHTML() + ".");
			}
		};
		timer.scheduleRepeating(1000);
    }

    public void stopRecord() {
    	media.stopRecord();
    	timer.cancel();
    	text.setHTML("Recording stopped.");
    }
    
    public void release() {
    	media.release();
    	text.setHTML("Media released.");
    }
}
