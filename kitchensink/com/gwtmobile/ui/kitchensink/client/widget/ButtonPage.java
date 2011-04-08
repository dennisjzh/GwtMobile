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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.page.Page;

public class ButtonPage extends Page{

	private static ButtonPageUiBinder uiBinder = GWT
			.create(ButtonPageUiBinder.class);

	interface ButtonPageUiBinder extends UiBinder<Widget, ButtonPage> {
	}

	public ButtonPage() {
		initWidget(uiBinder.createAndBindUi(this));	
	}
	
	@UiHandler("ok1")
	public void onClickOk1(ClickEvent e) {
		Window.alert("You clicked OK1");
	}
	
	@UiHandler("ok2")
	public void onClickOk2(ClickEvent e) {
		Window.alert("You clicked OK2");
	}
	
	@UiHandler("cancel2")
	public void onClickCancel2(ClickEvent e) {
		Window.alert("You clicked Cancel2");
	}
	
	@UiHandler("yes3")
	public void onClickYes3(ClickEvent e) {
		Window.alert("You clicked Yes3");
	}
	
	@UiHandler("no3")
	public void onClickNo3(ClickEvent e) {
		Window.alert("You clicked No3");
	}
	
	@UiHandler("cancel3")
	public void onClickCancel(ClickEvent e) {
		Window.alert("You clicked Cancel3");
	}
}
