/* Copyright (c) 2011 Zhihua (Dennis) Jiang
 * 
 * author: Daniel Tizón
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
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page; 
import com.gwtmobile.phonegap.client.plugins.Bluetooth;
import com.gwtmobile.phonegap.client.plugins.Bluetooth.Callback;
import com.gwtmobile.phonegap.client.plugins.Bluetooth.StringCallback;

public class BluetoothUi extends Page {

	@UiField
	HTML text;

	private static BluetoothUiUiBinder uiBinder = GWT
			.create(BluetoothUiUiBinder.class);

	interface BluetoothUiUiBinder extends UiBinder<Widget, BluetoothUi> {
	}

	public BluetoothUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
		switch (e.getSelection()) {
		case 0:
			isBTEnabled();
			break;
		case 1:
			enableBT();
			break;
		case 2:
			disableBT();
			break;
		case 3:
			listDevices();
			break;
		case 4:
			pairBT("6C:9B:02:44:FA:BF");
			break;
		case 5:
			listBoundDevices();
			break;
		case 6:
			stopDiscovering();
			break;
		case 7:
			isBound("6C:9B:02:44:FA:BF");
			break;
		}
	}

	public void isBTEnabled() {
		Bluetooth.isBTEnabled(new Callback() {
			@Override
			public void onSuccess(boolean result) {
				try {
					String state = "";
					if (result)
						state = "Enabled";
					if (!result)
						state = "Disabled";
					text.setHTML("Result:<br/>" + state);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onError(String message) {
				text.setHTML("Error: " + message);
			}
		});
	}

	public void enableBT() {
		Bluetooth.enableBT(new Callback() {
			@Override
			public void onSuccess(boolean result) {
				try {
					String state = "";
					if (result)
						state = "Success";
					if (!result)
						state = "Error";
					text.setHTML("Result:<br/>" + state);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String message) {
				text.setHTML("Error: " + message);
			}
		});
	}

	public void disableBT() {
		Bluetooth.disableBT(new Callback() {
			@Override
			public void onSuccess(boolean result) {
				try {
					String state = "";
					if (result)
						state = "Success";
					if (!result)
						state = "Error";
					text.setHTML("Result:<br/>" + state);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String message) {
				text.setHTML("Error: " + message);
			}
		});
	}

	public void listDevices() {
		Bluetooth.listDevices(new StringCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					String textHTML = "";
					JSONValue value = JSONParser.parseLenient(result);
					JSONArray devicesArray = value.isArray();

					if (devicesArray != null) {

						textHTML = "Result:";

						for (int i = 0; i < devicesArray.size(); i++) {
							JSONObject deviceObj = devicesArray.get(i)
									.isObject();
							textHTML = textHTML + "<br/>"
									+ deviceObj.get("name");
						}

						text.setHTML(textHTML);

					}
				} catch (Exception e) {
					e.printStackTrace();
					text.setHTML("Error: " + e.getMessage());
				}
			}

			@Override
			public void onError(String message) {
				text.setHTML("Error: " + message);
			}
		});
	}

	public void pairBT(String address) {
		Bluetooth.pairBT(address, new Callback() {
			@Override
			public void onSuccess(boolean result) {
				try {
					String state = "";
					if (result)
						state = "Success";
					if (!result)
						state = "Error";
					text.setHTML("Result:<br/>" + state);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String message) {
				text.setHTML("Error: " + message);
			}
		});
	}

	public void listBoundDevices() {
		Bluetooth.listBoundDevices(new StringCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					String textHTML = "";
					JSONValue value = JSONParser.parseLenient(result);
					JSONArray devicesArray = value.isArray();

					if (devicesArray != null) {
						textHTML = "Result:";
						for (int i = 0; i < devicesArray.size(); i++) {
							JSONObject deviceObj = devicesArray.get(i).isObject();
							textHTML = textHTML + "<br/>"
									+ deviceObj.get("name");
						}
						text.setHTML(textHTML);
					}
				} catch (Exception e) {
					e.printStackTrace();
					text.setHTML("Error: " + e.getMessage());
				}
			}

			@Override
			public void onError(String message) {
				text.setHTML("Error: " + message);
			}
		});
	}

	public void stopDiscovering() {
		Bluetooth.stopDiscovering(new Callback() {
			@Override
			public void onSuccess(boolean result) {
				try {
					String state = "";
					if (result)
						state = "Success";
					if (!result)
						state = "Error";
					text.setHTML("Result:<br/>" + state);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onError(String message) {
				text.setHTML("Error: " + message);
			}
		});
	}

	public void isBound(String address) {
		Bluetooth.isBound(address, new Callback() {
			@Override
			public void onSuccess(boolean result) {
				try {
					String state = "";
					if (result)
						state = "Yes";
					if (!result)
						state = "No";
					text.setHTML("Result:<br/>" + state);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onError(String message) {
				text.setHTML("Error: " + message);
			}
		});
	}

}
