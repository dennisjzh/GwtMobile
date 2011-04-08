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
import com.gwtmobile.ui.client.widgets.CheckBox;
import com.gwtmobile.ui.client.widgets.CheckBoxGroup;

public class CheckBoxPage extends Page{

	@UiField CheckBoxGroup group1;
	@UiField CheckBoxGroup group2;
	
	private static CheckBoxPageUiBinder uiBinder = GWT
			.create(CheckBoxPageUiBinder.class);

	interface CheckBoxPageUiBinder extends UiBinder<Widget, CheckBoxPage> {
	}

	public CheckBoxPage() {
		initWidget(uiBinder.createAndBindUi(this));	
	}
	
    @UiHandler("group1")
    void onGroup1SelectionChanged(SelectionChangedEvent e) {
    	CheckBox radio = (CheckBox) group1.getWidget(e.getSelection());
    	Utils.Console("group1 " + e.getSelection() + " " + radio.getText());
    }

    @UiHandler("group2")
    void onGroup2SelectionChanged(SelectionChangedEvent e) {
    	CheckBox radio = (CheckBox) group2.getWidget(e.getSelection());
    	Utils.Console("group2 " + e.getSelection() + " " + radio.getText());
    }
}
