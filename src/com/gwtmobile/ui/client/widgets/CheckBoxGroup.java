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

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.DragController;
import com.gwtmobile.ui.client.event.DragEvent;
import com.gwtmobile.ui.client.event.DragEventsHandler;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.event.SelectionChangedHandler;
import com.gwtmobile.ui.client.utils.Utils;

public class CheckBoxGroup extends PanelBase 
	implements HasWidgets, ClickHandler, DragEventsHandler, ValueChangeHandler<Boolean> {

	private int _pressed = -1;

    public CheckBoxGroup() {
    	super();
        addDomHandler(this, ClickEvent.getType());
        setStyleName("CheckBoxGroup");
		addStyleName("Vertical");
    }
    
    @Override
    public void onLoad() {
    	super.onLoad();
    	DragController.get().addDragEventsHandler(this);
    }
    
    @Override
    protected void onUnload() {
    	super.onUnload();
    	DragController.get().removeDragEventsHandler(this);
    }

    public HandlerRegistration addSelectionChangedHandler(SelectionChangedHandler handler) {
        return this.addHandler(handler, SelectionChangedEvent.TYPE);
    }
    
    @Override
    public void onClick(ClickEvent e) {
        final EventTarget target = e.getNativeEvent().getEventTarget();
        String targetTagName = ((Element)target.cast()).getTagName().toUpperCase();
        Utils.Console("onClick target " + targetTagName);
        if (targetTagName.equals("LABEL")) {
        	return;		// if check box label is click, another (simulated) click event with
        				// check box INPUT as target will fire after this one. So this click event
        				// can be safely ignored.
        }
        Element div = Element.as(target);
        while (!div.getNodeName().toUpperCase().equals("SPAN") || 
                div.getParentElement() != this.getElement()) {
            div = div.getParentElement();
            if (div == null) {
                Utils.Console("CheckBoxGroup onClick: span not found");
                return;
            }
        }
        final int index = DOM.getChildIndex(this.getElement(), (com.google.gwt.user.client.Element)div);        
        com.google.gwt.user.client.ui.CheckBox checkbox = 
        	(com.google.gwt.user.client.ui.CheckBox) _panel.getWidget(index);
    	Utils.Console("onClick " + checkbox.getValue());
    	if (targetTagName.equals("INPUT")) {
        	Utils.Console("onClick value changed");
        	checkbox.setValue(checkbox.getValue());	// if target is check box INPUT, check box value is 
        											// already changed when click event is fired.
        											// just need to set its current value back to the check box
        											// to update style.
    	}
    	else {
        	checkbox.setValue(!checkbox.getValue());
    	}
    	
    	Scheduler.get().scheduleDeferred(new ScheduledCommand() {			
			@Override
			public void execute() {
				SelectionChangedEvent selectionChangedEvent = new SelectionChangedEvent(index, target);
				fireEvent(selectionChangedEvent);
			}
		});
    }
    
    public ArrayList<Integer> getCheckedIndices() {
    	ArrayList<Integer> checkedList = new ArrayList<Integer>(_panel.getWidgetCount());
        for (int i = 0; i < _panel.getWidgetCount(); i++) {
        	com.google.gwt.user.client.ui.CheckBox radio = 
        		(com.google.gwt.user.client.ui.CheckBox) _panel.getWidget(i);
            if (radio.getValue()) {
            	checkedList.add(i);
            }
        }
        return checkedList;
    }

    public ArrayList<Widget> getCheckedWidgets() {
    	ArrayList<Widget> checkedList = new ArrayList<Widget>(_panel.getWidgetCount());
        for (int i = 0; i < _panel.getWidgetCount(); i++) {
        	com.google.gwt.user.client.ui.CheckBox radio = 
        		(com.google.gwt.user.client.ui.CheckBox) _panel.getWidget(i);
            if (radio.getValue()) {
            	checkedList.add(radio);
            }
        }
        return checkedList;
    }
    
    @Override
    public void add(Widget w) {
    	assert w instanceof CheckBox 
    		: "Can only contain CheckBox widgets in CheckBoxGroup";
    	CheckBox checkbox = (CheckBox) w;
        _panel.add(checkbox);
		checkbox.addValueChangeHandler(this);
    }
    
    public void setVertical(boolean vertical) {
    	if (vertical) {
    		addStyleName("Vertical");
    		removeStyleName("Horizontal");
    	}
    	else {
    		addStyleName("Horizontal");
    		removeStyleName("Vertical");
    	}
    }
    
	@Override
	public void onValueChange(ValueChangeEvent<Boolean> event) {
		Utils.Console("onValueChange " + event.getValue() + " " + event.getSource().getClass());
	}

	@Override
	public void onDragStart(DragEvent e) {
		_pressed = Utils.getTargetItemIndex(getElement(), e.getNativeEvent().getEventTarget());
    	if (_pressed >= 0) {
    		Widget item = getWidget(_pressed);
    		item.addStyleName("Pressed");
    	}
	}
	
	@Override
	public void onDragMove(DragEvent e) {
		if (_pressed >= 0) {
    		Widget item = getWidget(_pressed);
    		item.removeStyleName("Pressed");
    		_pressed = -1;
		}
	}

	@Override
	public void onDragEnd(DragEvent e) {
		onDragMove(e);
	}
	
}
