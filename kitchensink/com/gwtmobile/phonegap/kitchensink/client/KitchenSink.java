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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtmobile.phonegap.client.Device;
import com.gwtmobile.phonegap.client.Event;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.page.PageHistory;
import com.gwtmobile.ui.client.widgets.Button;

public class KitchenSink implements EntryPoint {

	public static MainUi mainUi = new MainUi();
	
	@Override
	public void onModuleLoad() {

		Device.overrideBackButton();
		Event.onBackKeyDown(new Event.Callback() {			
			@Override
			public void onEventFired() {
				onBackKeyDown();
			}
		});

//		PhoneGap.onDeviceReady(new Callback() {			
//			@Override
//			public void onEventFired() {
				Page.load(mainUi);
//			}
//		});
		
	}
	
    public void onBackKeyDown() {
    	if (PageHistory.from() == null) {
    		Device.exitApp();    		
    	}
    	else {
    		if (!PageHistory.current().getClass().toString().endsWith(".EventUi")) {
    			// emulate click on the header back button for page transition to show.
            	emulateClickOnBackButton();    	
    		}
    	}    		
    }

	protected void emulateClickOnBackButton() {
		HTMLPanel current = (HTMLPanel) PageHistory.current().getWidget();
		PhoneGapHeaderPanel header = (PhoneGapHeaderPanel) current.getWidget(0);
		Button left = header.getLeftButton();
		NativeEvent event = Document.get().createClickEvent(1, 1, 1, 1, 1, false, false, false, false);
		left.getElement().dispatchEvent(event);
	}
    
//    private native void doToast(String msg) /*-{
//	    $wnd.PhoneGap.exec(null, null, "GwtMobile", "toast", [msg]);
//	}-*/; 
//
//    private native void requestFocus() /*-{
//	    $wnd.PhoneGap.exec(null, null, "GwtMobile", "requestFocus", []);
//	}-*/; 

}
