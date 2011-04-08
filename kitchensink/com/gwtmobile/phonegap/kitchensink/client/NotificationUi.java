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
import com.gwtmobile.phonegap.client.Notification;
import com.gwtmobile.phonegap.client.Notification.Callback;
import com.gwtmobile.phonegap.client.Notification.ConfirmCallback;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class NotificationUi extends Page {

	@UiField HTML text;

	private static NotificationUiBinder uiBinder = GWT
			.create(NotificationUiBinder.class);

	interface NotificationUiBinder extends UiBinder<Widget, NotificationUi> {
	}

	public NotificationUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		alert();
    		break;
    	case 1:
    		confirm();
    		break;
    	case 2:
    		beep();
    		break;
    	case 3:
    		vibrate();
    		break;
    	case 4:
    		blink();
    		break;
    	case 5:
    		activity();
    		break;
    	case 6:
    		progress();
    		break;
    	}
    }

    public void alert() {
    	Notification.alert("This is an alert message", new Callback() {
			@Override
			public void onComplete() {
				text.setHTML("Received callback from alert.");
			}
		}, "Alert", "Okey-Dokey");
    	
    }   

    public void confirm() {
    	Notification.confirm("This is a confirmation message", new ConfirmCallback() {
			@Override
			public void onComplete(int result) {
				text.setHTML("You selected " + (result == 1 ? "Yes" : "No"));
			}
		}, "Confirm", "Yes,No");
    }   

    public void beep() {
    	Notification.beep(1);
    	text.setHTML("heard the beep?");
    }   

    public void vibrate() {
    	Notification.vibrate(100);
    	text.setHTML("felt the vibration?");
    }   

    public void blink() {
    	Notification.blink(3, "red");
    	text.setHTML("saw the light blinking?");
    }   

    public void activity() {
    	Notification.activityStart();
    	new Timer(){
			@Override
			public void run() {
				Notification.activityStop();
			}
		}.schedule(3000);
    }   

    public void progress() {
    	Notification.progressStart("Downloading", "Please be patient...");
    	new Timer(){
    		int value = 0;
			@Override
			public void run() {
				Notification.progressValue(value++);
				if (value > 100) {
					Notification.progressStop();
					this.cancel();
				}
			}
		}.scheduleRepeating(20);
    }       
}
