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

package com.gwtmobile.ui.kitchensink.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.widgets.ListPanel;
import com.gwtmobile.ui.kitchensink.client.panel.PanelsPage;
import com.gwtmobile.ui.kitchensink.client.transition.TransitionsPage;
import com.gwtmobile.ui.kitchensink.client.widget.WidgetsPage;

public class MainPage extends Page {

	@UiField ListPanel list;

	private static MainPageUiBinder uiBinder = GWT
	.create(MainPageUiBinder.class);

	interface MainPageUiBinder extends UiBinder<Widget, MainPage> {
	}

	public MainPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
		switch (e.getSelection()) {
		case 0:
			TransitionsPage transitions = new TransitionsPage();
			this.goTo(transitions);
			break;
		case 1:
			PanelsPage panels = new PanelsPage();
			this.goTo(panels);
			break;
		case 2:
			WidgetsPage widgets = new WidgetsPage();
			this.goTo(widgets);
			break;
		}
	}

}
