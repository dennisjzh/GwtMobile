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

package com.gwtmobile.ui.kitchensink.client.communication;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class RpcPage extends Page {

	private static RpcPageUiBinder uiBinder = GWT.create(RpcPageUiBinder.class);
	
	@UiField HTML text;

	interface RpcPageUiBinder extends UiBinder<Widget, RpcPage> {
	}

	public RpcPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		makeRpcCall();
    		break;
    	}
    }

	private void makeRpcCall() {
		final RpcServiceAsync greetingService = GWT
		.create(RpcService.class);
		
		//The code below sets service entry point to a server url.
		ServiceDefTarget service = (ServiceDefTarget) greetingService;
		String address = "http://gwtmobile-services.appspot.com/rpc";
		service.setServiceEntryPoint(address);
		
		greetingService.greetServer("GWT Mobile UI", new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				text.setHTML(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				text.setHTML(caught.getMessage());
			}
		});
	}
}
