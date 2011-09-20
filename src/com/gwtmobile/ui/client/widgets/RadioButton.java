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

package com.gwtmobile.ui.client.widgets;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.gwtmobile.ui.client.utils.Utils;


public class RadioButton extends com.google.gwt.user.client.ui.RadioButton 
	implements IsGwtMobileWidget {

    private IsGwtMobileWidgetHelper _widgetHelper = new IsGwtMobileWidgetHelper();
	
	public RadioButton() {
		super(null);
		if (Utils.isAndroid() && Utils.isWVGA()) {
			setStyleName("RadioButton");
			DivElement div = Document.get().createDivElement();
			this.getElement().insertFirst(div);
		}
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		_widgetHelper.CheckInitialLoad(this);
	}

	@Override
	public void setValue(Boolean value) {
		setValue(value, true);
	}
	
	@Override
	public void setValue(Boolean checked, boolean fireEvents) {
		super.setValue(checked, fireEvents);
		updateCheckedStyle(checked);
	}

	protected void updateCheckedStyle(Boolean checked) {
		if (checked) {
			addStyleName("Selected");
		}
		else {
			removeStyleName("Selected");       
		}
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
