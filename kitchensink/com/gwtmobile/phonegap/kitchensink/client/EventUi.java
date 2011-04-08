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

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.phonegap.client.Event;
import com.gwtmobile.phonegap.client.Event.Callback;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.widgets.BackButton;
import com.gwtmobile.ui.client.widgets.HeaderPanel;

public class EventUi extends Page {

	private static EventUiUiBinder uiBinder = GWT.create(EventUiUiBinder.class);
	
	@UiField HTML text;
	@UiField HeaderPanel header;

	interface EventUiUiBinder extends UiBinder<Widget, EventUi> {
	}

	public EventUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	@Override
	public void onLoad() {
		super.onLoad();
		
		BackButton back = (BackButton) header.getLeftButton();
		back.setReturnParameter("Back");
		
		Event.onDeviceReady(new Callback() {			
			@Override
			public void onEventFired() {
				text.setHTML((new Date()).toString() + ": onDeviceReady<br/>" + text.getHTML());
			}
		});
		
		Event.onPause(new Callback() {			
			@Override
			public void onEventFired() {
				text.setHTML((new Date()).toString() + ": onPause<br/>" + text.getHTML());
			}
		});

		Event.onResume(new Callback() {			
			@Override
			public void onEventFired() {
				text.setHTML((new Date()).toString() + ": onResume<br/>" + text.getHTML());
			}
		});

		Event.onBackKeyDown(new Callback() {			
			@Override
			public void onEventFired() {
				text.setHTML((new Date()).toString() + ": onBackKeyDown<br/>" + text.getHTML());
			}
		});

		Event.onMenuKeyDown(new Callback() {			
			@Override
			public void onEventFired() {
				text.setHTML((new Date()).toString() + ": onMenuKeyDown<br/>" + text.getHTML());
			}
		});
		
		Event.onSearchKeyDown(new Callback() {			
			@Override
			public void onEventFired() {
				text.setHTML((new Date()).toString() + ": onSearchKeyDown<br/>" + text.getHTML());
			}
		});
		
	}
	
	@Override
	public void goBack(Object returnValue) {
		if (returnValue != null && returnValue.equals("Back")) {
			super.goBack(returnValue);
		}
	}
}
