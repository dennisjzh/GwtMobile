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
import com.gwtmobile.phonegap.client.Network;
import com.gwtmobile.phonegap.client.Network.ConnectionType;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class NetworkUi extends Page {

	@UiField HTML text;
	
	private static NetworkUiUiBinder uiBinder = GWT.create(NetworkUiUiBinder.class);
	
	interface NetworkUiUiBinder extends UiBinder<Widget, NetworkUi> {
	}

	public NetworkUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		getConnectionType();
    		break;
    	}
    }

    private void getConnectionType() {
    	ConnectionType type = Network.getConnectionType();
		text.setHTML("type=" + type.toString() + "<br/>" + 
				"UNKNOWN:" + type.isUnknown() + "<br/>" +  	
				"ETHERNET:" + type.isEthernet() + "<br/>" +  	
				"WIFI:" + type.isWifi() + "<br/>" +  	
				"CELL_2G:" + type.isCell2G() + "<br/>" +  	
				"CELL_3G:" + type.isCell3G() + "<br/>" +  	
				"CELL_4G:" + type.isCell4G() + "<br/>" +  	
				"NONE:" + type.isNone() + "<br/>" 	
		);
	}

}
