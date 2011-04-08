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

package com.gwtmobile.ui.kitchensink.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.widgets.ListPanel;

public class ListPanelPage extends Page{

	@UiField ListPanel list1, list2;
	
	private static ListPanelPageUiBinder uiBinder = GWT
			.create(ListPanelPageUiBinder.class);

	interface ListPanelPageUiBinder extends UiBinder<Widget, ListPanelPage> {
	}

	public ListPanelPage() {
		initWidget(uiBinder.createAndBindUi(this));		
	}

    @UiHandler("list1")
    void onList1SelectionChanged(SelectionChangedEvent e) {
    	Window.alert("You select item " + e.getSelection() + " on list 1.");    	
    }

    @UiHandler("list2")
    void onList2SelectionChanged(SelectionChangedEvent e) {
    	Window.alert("You select item " + e.getSelection() + " on list 2.");    	
    }

}
