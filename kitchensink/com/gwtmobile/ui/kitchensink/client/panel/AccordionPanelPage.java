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
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.widgets.AccordionPanel;
import com.gwtmobile.ui.client.widgets.HeaderPanel;

public class AccordionPanelPage extends Page {

	@UiField HeaderPanel header;
	@UiField AccordionPanel accordion;
	
	private static AccordionPanelPageUiBinder uiBinder = GWT
			.create(AccordionPanelPageUiBinder.class);

	interface AccordionPanelPageUiBinder extends UiBinder<Widget, AccordionPanelPage> {
	}

	public AccordionPanelPage() {
		initWidget(uiBinder.createAndBindUi(this));				
	}

}
