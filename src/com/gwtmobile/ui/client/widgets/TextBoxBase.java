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

package com.gwtmobile.ui.client.widgets;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;

class TextBoxBase extends com.google.gwt.user.client.ui.TextBoxBase 
	implements FocusHandler, BlurHandler, IsGwtMobileWidget {

    private IsGwtMobileWidgetHelper _widgetHelper = new IsGwtMobileWidgetHelper();

	TextBoxBase(String type) {
	    super(createNumberInputElement(type));
		setStyleName("TextBox " + capitalize(type));
		addFocusHandler(this);
		addBlurHandler(this);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		_widgetHelper.CheckInitialLoad(this);
		
	}
	
	@Override
	public void onFocus(FocusEvent event) {
		this.addStyleName("Focus");
	}

	@Override
	public void onBlur(BlurEvent event) {
		this.removeStyleName("Focus");
	}

	private static native InputElement createNumberInputElement(String type)  /*-{
		var e = $doc.createElement("INPUT");
		e.type = type;
		return e;
	}-*/;
	
	private String capitalize(String input) {
		return input.substring(0, 1).toUpperCase() + 
				input.substring(1).toLowerCase();

	}

	@Override
	public void onInitialLoad() {
	}

	@Override
	public void onTransitionEnd() {
	}

	@Override
	public void setSecondaryStyle(String style) {
		_widgetHelper.setSecondaryStyle(this, style);
	}
}
