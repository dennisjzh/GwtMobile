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

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.gwtmobile.ui.client.event.DragController;
import com.gwtmobile.ui.client.event.DragEvent;
import com.gwtmobile.ui.client.event.DragEventsHandler;

public class Slider extends WidgetBase 
	implements DragEventsHandler, HasValueChangeHandlers<Integer> {

	protected int _value = 0;
	protected FlowPanel _panel = new FlowPanel();	
	protected HTML _label = new HTML(_value + "");	
	protected HTML _slider = new HTML();	
	
    public Slider() {
    	_panel.add(_label);
        _slider.setHTML("<div></div><div></div><div></div>");
    	_panel.add(_slider);
    	initWidget(_panel);
        setStyleName("Slider");
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        DragController.get().addDragEventsHandler(this);
    }
    
    @Override
    public void onUnload() {
        DragController.get().removeDragEventsHandler(this);
    }
    
    @Override
    public void onDragStart(DragEvent e) {
    	DragController.get().captureDragEvents(this);
    	int value = computeNewValue(e);
   		setValue(value);
    }

    @Override
    public void onDragMove(DragEvent e) {
    	e.stopPropagation();
    	int value = computeNewValue(e);
   		setValue(value);
    }

    @Override
    public void onDragEnd(DragEvent e) {
    	DragController.get().releaseCapture(this);
    }
    
    public void setValue(int value) {
    	if (_value != value) {
        	_value = value;
        	updateSliderPosition();
        	ValueChangeEvent.fire(this, _value);
    	}
    }
    
	public int getValue() {
    	return _value;
    }
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Integer> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}
	
	public void setDisplayValue (boolean display) {
		if (display) {
			_label.removeStyleName("Hide");
		}
		else {
			_label.addStyleName("Hide");
		}
	}
	
    private int computeNewValue(DragEvent e) {
    	Element ele = getElement();
    	int offset = (int)e.X - ele.getAbsoluteLeft();
    	int width = ele.getClientWidth();
    	int value = offset * 100 / width;
    	if (value > 100) {
    		value = 100;
    	}
    	else if (value < 0) {
    		value = 0;
    	}
    	return value;
    }

	private void updateSliderPosition() {
    	_label.setHTML(_value + "");
    	Element slider = getSliderElement();
		slider.getStyle().setWidth(_value, Unit.PCT);
	}

	private Element getSliderElement() {
    	return (Element) _slider.getElement().getChild(1);
	}
}
