/*
 * Copyright (c) 2011 Zhihua (Dennis) Jiang
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
import com.gwtmobile.ui.client.utils.Utils;
import com.gwtmobile.ui.client.widgets.RadioButton;
import com.gwtmobile.ui.client.widgets.RadioButtonGroup;

public class RadioButtonPage extends Page{

	@UiField RadioButtonGroup radiogroup1;
	@UiField RadioButtonGroup radiogroup2;
	
	private static RadioButtonPageUiBinder uiBinder = GWT
			.create(RadioButtonPageUiBinder.class);

	interface RadioButtonPageUiBinder extends UiBinder<Widget, RadioButtonPage> {
	}

	public RadioButtonPage() {
		initWidget(uiBinder.createAndBindUi(this));	
	}
	
    @UiHandler("radiogroup1")
    void onRadioGroup1SelectionChanged(SelectionChangedEvent e) {
    	RadioButton radio = (RadioButton) radiogroup1.getWidget(e.getSelection());
    	Utils.Console("group1 " + e.getSelection() + " " + radio.getText());
    }

    @UiHandler("radiogroup2")
    void onRadioGroup2SelectionChanged(SelectionChangedEvent e) {
    	RadioButton radio = (RadioButton) radiogroup2.getWidget(e.getSelection());
    	Utils.Console("group2 " + e.getSelection() + " " + radio.getText());
    }
}
