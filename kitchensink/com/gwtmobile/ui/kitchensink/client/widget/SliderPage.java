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
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.page.Page;

public class SliderPage extends Page{

	@UiField Label value1;
	
	private static FlipSwitchPageUiBinder uiBinder = GWT
			.create(FlipSwitchPageUiBinder.class);

	interface FlipSwitchPageUiBinder extends UiBinder<Widget, SliderPage> {
	}

	public SliderPage() {
		initWidget(uiBinder.createAndBindUi(this));	
	}
	
	@UiHandler("slider1")
	public void onValueChangeSlider1(ValueChangeEvent<Integer> e) {
		value1.setText(e.getValue() + "");
	}
}
