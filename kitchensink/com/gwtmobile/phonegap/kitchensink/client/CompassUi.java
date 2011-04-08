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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.phonegap.client.Compass;
import com.gwtmobile.phonegap.client.Compass.Callback;
import com.gwtmobile.phonegap.client.Compass.Options;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class CompassUi extends Page {

	@UiField HTML text;
	String watchId;

	private static CompassUiUiBinder uiBinder = GWT
			.create(CompassUiUiBinder.class);

	interface CompassUiUiBinder extends UiBinder<Widget, CompassUi> {
	}

	public CompassUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onUnload() {
		clearWatch();
		super.onUnload();
	}
	
    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		getCurrentHeading();
    		break;
    	case 1:
    		watchHeading();
    		break;
    	case 2:
    		clearWatch();
    		break;
    	}
    }

    public void getCurrentHeading() {
		Compass.getCurrentHeading(new Callback() {			
			@Override
			public void onSuccess(float heading) {
				text.setHTML("Current Heading:<br/>" + heading);				
			}			
			@Override
			public void onError() {
				text.setText("Error");
			}
		});
	}

    public void watchHeading() {
		watchId = Compass.watchHeading(new Callback() {			
			@Override
			public void onSuccess(float heading) {
				text.setHTML("Watch Heading:<br/>" + heading);				
			}			
			@Override
			public void onError() {
				text.setHTML("Error");
			}
		}, new Options().frequency(100));
	}

    public void clearWatch() {
    	if (watchId != null) {
    		Compass.clearWatch(watchId);
    		text.setText("");
    		watchId = null;
    	}
	}
}
