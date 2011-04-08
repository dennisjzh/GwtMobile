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
import com.gwtmobile.phonegap.client.Accelerometer;
import com.gwtmobile.phonegap.client.Accelerometer.Acceleration;
import com.gwtmobile.phonegap.client.Accelerometer.Callback;
import com.gwtmobile.phonegap.client.Accelerometer.Options;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class AccelerometerUi extends Page {

	@UiField HTML text;
	String watchId;

	private static AccelerometerUiUiBinder uiBinder = GWT
			.create(AccelerometerUiUiBinder.class);

	interface AccelerometerUiUiBinder extends UiBinder<Widget, AccelerometerUi> {
	}

	public AccelerometerUi() {
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
    		getCurrentAcceleration();
    		break;
    	case 1:
    		watchAcceleration();
    		break;
    	case 2:
    		clearWatch();
    		break;
    	}
    }
    
    void getCurrentAcceleration() {
		Accelerometer.getCurrentAcceleration(new Callback() {			
			@Override
			public void onSuccess(Acceleration accel) {
				text.setHTML("Current Acceleration:<br/>X: " + accel.getX() + "<br/>Y: " + accel.getY() + "<br/>Z: " + accel.getZ());				
			}			
			@Override
			public void onError() {
				text.setHTML("Error");
			}
		});
	}

    void watchAcceleration() {
		watchId = Accelerometer.watchAcceleration(new Callback() {			
			@Override
			public void onSuccess(Acceleration accel) {
				text.setHTML("Watch Acceleration:<br/>X: " + accel.getX() + "<br/>Y: " + accel.getY() + "<br/>Z: " + accel.getZ());				
			}			
			@Override
			public void onError() {
				text.setHTML("Error");
			}
		}, new Options().frequency(100));
	}

    public void clearWatch() {
    	if (watchId != null) {
    		Accelerometer.clearWatch(watchId);
    		text.setHTML("");
    		watchId = null;
    	}
	}
}
