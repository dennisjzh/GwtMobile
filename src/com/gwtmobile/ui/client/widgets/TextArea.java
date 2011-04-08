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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Element;
import com.gwtmobile.ui.client.utils.Utils;

public class TextArea extends com.google.gwt.user.client.ui.TextArea 
	implements FocusHandler, BlurHandler, KeyUpHandler {

	TextArea() {
		setStyleName("TextArea");
		addFocusHandler(this);
		addBlurHandler(this);
		addKeyUpHandler(this);
	}
	
	@Override
	public void onFocus(FocusEvent event) {
		this.addStyleName("Focus");
	}

	@Override
	public void onBlur(BlurEvent event) {
		this.removeStyleName("Focus");
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		Element ele = getElement();
		final int extraLineHeight = 15; 
		int scrollHeight = ele.getScrollHeight();
		int clientHeight = ele.getClientHeight();
		Utils.Console(scrollHeight + " " + clientHeight);
		if ( clientHeight < scrollHeight ) {
			ele.getStyle().setHeight(scrollHeight + extraLineHeight, Unit.PX);
		}
	}

}
