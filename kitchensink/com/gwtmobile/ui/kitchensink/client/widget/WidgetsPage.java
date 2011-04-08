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

package com.gwtmobile.ui.kitchensink.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.widgets.ListPanel;

public class WidgetsPage extends Page {

	@UiField ListPanel list;
	ButtonPage button = new ButtonPage();
	CheckBoxPage checkBox = new CheckBoxPage();
	FlipSwitchPage flipSwitch = new FlipSwitchPage();
	RadioButtonPage radioButton = new RadioButtonPage();
	SliderPage slider = new SliderPage();
	TextBoxPage textBox = new TextBoxPage();
	DropDownListPage dropDownList = new DropDownListPage();
	
	private static WidgetsPageUiBinder uiBinder = GWT
			.create(WidgetsPageUiBinder.class);

	interface WidgetsPageUiBinder extends UiBinder<Widget, WidgetsPage> {
	}

	public WidgetsPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

    @UiHandler("list")
    void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		goTo(button);
    		break;
    	case 1:
    		goTo(checkBox);
    		break;
    	case 2:
    		goTo(dropDownList);
    		break;
    	case 3:
    		goTo(flipSwitch);
    		break;
    	case 4:
    		goTo(radioButton);
    		break;
    	case 5:
    		goTo(slider);
    		break;
    	case 6:
    		goTo(textBox);
    		break;
    	}
    }

}
