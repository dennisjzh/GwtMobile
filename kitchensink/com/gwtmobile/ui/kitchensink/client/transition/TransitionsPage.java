/*
 * Copyright (c) 2010 Zhihua (Dennis) Jiang
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

package com.gwtmobile.ui.kitchensink.client.transition;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.page.Transition;
import com.gwtmobile.ui.client.widgets.ListPanel;

public class TransitionsPage extends Page {

	@UiField ListPanel list;
	TransitionDemoPage demo = new TransitionDemoPage();
	
	private static TransitionPageUiBinder uiBinder = GWT
			.create(TransitionPageUiBinder.class);

	interface TransitionPageUiBinder extends UiBinder<Widget, TransitionsPage> {
	}

	public TransitionsPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

    @UiHandler("list")
    void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		demo.header.setCaption("Slide");
    		this.goTo(demo, Transition.SLIDE);
    		break;
    	case 1:
    		demo.header.setCaption("Slide Up");
    		this.goTo(demo, Transition.SLIDEUP);
    		break;
    	case 2:
    		demo.header.setCaption("Slide Down");
    		this.goTo(demo, Transition.SLIDEDOWN);
    		break;
    	case 3:
    		demo.header.setCaption("Fade");
    		this.goTo(demo, Transition.FADE);
    		break;
    	case 4:
    		demo.header.setCaption("Pop");
    		this.goTo(demo, Transition.POP);
    		break;
    	case 5:
    		demo.header.setCaption("Swap");
    		this.goTo(demo, Transition.SWAP);
    		break;
    	case 6:
    		demo.header.setCaption("Flip");
    		this.goTo(demo, Transition.FLIP);
    		break;
//    	case 7:
//    		demo.header.setCaption("Cube");
//    		this.goTo(demo, Transition.CUBE);
//    		break;
    	}
    }

}
