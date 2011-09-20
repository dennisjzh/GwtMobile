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
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class JsonpPage extends Page {

	private static JsonpPageUiBinder uiBinder = GWT.create(JsonpPageUiBinder.class);
	
	@UiField HTML text;

	interface JsonpPageUiBinder extends UiBinder<Widget, JsonpPage> {
	}

	public JsonpPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		makeJsonpCall();
    		break;
    	}
    }

	private void makeJsonpCall() {
		String url = "http://gwtmobile-services.appspot.com/jsonp";
	 JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
	 jsonp.requestObject(url,
	     new AsyncCallback<Hello>() {
	       @Override
	       public void onFailure(Throwable throwable) {
	    	   text.setHTML("Error: " + throwable);
	       }

	       @Override
	       public void onSuccess(Hello hello) {
	    	   text.setHTML(hello.world());
	       }
	     });
	}
	
	private static class Hello extends JavaScriptObject {
		
		protected Hello() {}
		
		public final native String world() /*-{
			return this.Hello;
		}-*/;   
	}
}
