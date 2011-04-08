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
import com.gwtmobile.phonegap.client.Geolocation;
import com.gwtmobile.phonegap.client.Geolocation.Callback;
import com.gwtmobile.phonegap.client.Geolocation.Options;
import com.gwtmobile.phonegap.client.Geolocation.Position;
import com.gwtmobile.phonegap.client.Geolocation.PositionError;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class GeolocationUi extends Page {

	@UiField HTML text;
	String watchId;

	private static GeolocationUiUiBinder uiBinder = GWT
			.create(GeolocationUiUiBinder.class);

	interface GeolocationUiUiBinder extends UiBinder<Widget, GeolocationUi> {
	}

	public GeolocationUi() {
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
    		getCurrentPosition();
    		break;
    	case 1:
    		watchPosition();
    		break;
    	case 2:
    		clearWatch();
    		break;
    	}
    }

    public void getCurrentPosition() {
		Geolocation.getCurrentPosition(new Callback() {			
			@Override
			public void onSuccess(Position position) {
				text.setHTML("Current Position<br/>Latitude: " + position.getCoords().getLatitude() + "<br/>" + 
						"Longitude: " + position.getCoords().getLongitude() + "<br/>" +
						"Altitude: " + position.getCoords().getAltitude() + "<br/>" +
						"Accuracy: " + position.getCoords().getAccuracy() + "<br/>" +
						"Altitude Accuracy: " + position.getCoords().getAltitudeAccuracy() + "<br/>" +
						"Heading: " + position.getCoords().getHeading() + "<br/>" +
						"Speed: " + position.getCoords().getSpeed() + "<br/>" +
						"Timestamp: " + position.getTimestamp() + "<br/>"
						);				
			}			
			@Override
			public void onError(PositionError error) {
				text.setHTML("Error<br/>code:" + error.getCode() + "<br/>message: " + error.getMessage());
			}
		});
	}

    public void watchPosition() {
		watchId = Geolocation.watchPosition(new Callback() {			
			@Override
			public void onSuccess(Position position) {
				text.setHTML("Watch Position<br/>Latitude: " + position.getCoords().getLatitude() + "<br/>" + 
						"Longitude: " + position.getCoords().getLongitude() + "<br/>" +
						"Altitude: " + position.getCoords().getAltitude() + "<br/>" +
						"Accuracy: " + position.getCoords().getAccuracy() + "<br/>" +
						"Altitude Accuracy: " + position.getCoords().getAltitudeAccuracy() + "<br/>" +
						"Heading: " + position.getCoords().getHeading() + "<br/>" +
						"Speed: " + position.getCoords().getSpeed() + "<br/>" +
						"Timestamp: " + position.getTimestamp() + "<br/>"
						);				
			}			
			@Override
			public void onError(PositionError error) {
				text.setHTML("Error<br/>code:" + error.getCode() + "<br/>message: " + error.getMessage());
			}
		}, new Options().frequency(100).enableHighAccuracy(true).timeout(1000).maximumAge(1000));
	}

    public void clearWatch() {
    	if (watchId != null) {
    		Geolocation.clearWatch(watchId);
    		text.setHTML("");
    		watchId = null;
    	}
	}
}
