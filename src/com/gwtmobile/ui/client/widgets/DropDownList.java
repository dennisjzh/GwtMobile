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

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.utils.Utils;


public class DropDownList extends PanelBase 
	implements FocusHandler, BlurHandler, ChangeHandler, HasValueChangeHandlers<String> {

	ListBox _listBox = new ListBox();
	
	public DropDownList() {
		super();
		_panel.add(_listBox);
		if (!Utils.isIOS()) {
			_panel.add(new HTMLPanel(""));
			_panel.add(new HTMLPanel(""));
			_panel.add(new HTMLPanel(""));
		}
		setStyleName("DropDownList");
		_listBox.addFocusHandler(this);
		_listBox.addBlurHandler(this);
		_listBox.addChangeHandler(this);
	}
	
	@Override
	protected void onUnload() {
		removeStyleName("Focus");
		super.onUnload();
	}
	
	@Override
	public void onFocus(FocusEvent event) {
		addStyleName("Focus");
	}

	@Override
	public void onBlur(BlurEvent event) {
		removeStyleName("Focus");
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		int index = _listBox.getSelectedIndex();
		String value = _listBox.getValue(index);
		ValueChangeEvent.fire(this, value);
	}

	@Override
	public void add(Widget w) {
		assert w instanceof DropDownItem : "Can only contain DropDownItem in DropDownList.";
		DropDownItem item = (DropDownItem) w;
		_listBox.addItem(item.getText(), item.getValue());
	}

	public String getSelectedText() {
		int index = _listBox.getSelectedIndex();
		if (index >= 0) {
			return _listBox.getItemText(index);
		}
		else {
			return null;
		}
	}

	public String getSelectedValue() {
		int index = _listBox.getSelectedIndex();
		if (index >= 0) {
			return _listBox.getValue(index);
		}
		else {
			return null;
		}
	}

	public ListBox getListBox() {
		return _listBox;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}

}
