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
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.utils.Utils;
import com.gwtmobile.ui.client.widgets.DropDownList;

public class DropDownListPage extends Page {

	@UiField DropDownList movie;
	
	private static DropDownListPageUiBinder uiBinder = GWT
			.create(DropDownListPageUiBinder.class);

	interface DropDownListPageUiBinder extends UiBinder<Widget, DropDownListPage> {
	}

	public DropDownListPage() {
		initWidget(uiBinder.createAndBindUi(this));	
	}
	
	@UiHandler("car")
	public void onValueChangeCar(ValueChangeEvent<String> e) {
		Utils.Console(e.getValue());
	}

	@UiHandler("movie")
	public void onValueChangeMovie(ValueChangeEvent<String> e) {
		Utils.Console(movie.getSelectedText() + "(" + movie.getSelectedValue() + ")");
	}
	
	@UiHandler("city")
	public void onValueChangeCity(ValueChangeEvent<String> e) {
		Utils.Console(e.getValue());
	}

}
