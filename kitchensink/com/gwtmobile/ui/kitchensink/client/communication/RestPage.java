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

import org.restlet.client.resource.Result;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class RestPage extends Page {

	private static RestPageUiBinder uiBinder = GWT.create(RestPageUiBinder.class);
	
	@UiField HTML text;

	interface RestPageUiBinder extends UiBinder<Widget, RestPage> {
	}

	public RestPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		makeRestCall();
    		break;
    	}
    }

	private void makeRestCall() {
		String url = "http://gwtmobile-services.appspot.com/rest/World?" + 
				(int)(Math.random() * 10000);	//IO exception if caching.
		RestServiceProxy restResource = GWT.create(RestServiceProxy.class);
		//restResource.getClientResource().get(MediaType.APPLICATION_JSON);
		restResource.getClientResource().setReference(url);
		restResource.represent(new Result<Greeting>() {
		    @Override
			public void onFailure(Throwable throwable) {
		       text.setHTML("Error: " + throwable);
		    }

		    @Override
			public void onSuccess(Greeting greeting) {
		    	text.setHTML(greeting.getFrom() + ": " + greeting.getMessage());
		    }
		});
	}
	
}
