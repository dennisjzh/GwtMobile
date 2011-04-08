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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.DragController;
import com.gwtmobile.ui.client.event.DragEvent;
import com.gwtmobile.ui.client.event.DragEventsHandler;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.event.SelectionChangedHandler;
import com.gwtmobile.ui.client.utils.Utils;

public class ListPanel extends PanelBase implements ClickHandler, DragEventsHandler{

	private boolean _showArrow;
	private int _selected = -1;
	private boolean _selectable = true;
	
    public ListPanel() { 
        addDomHandler(this, ClickEvent.getType());
        setStyleName("ListPanel");
    }

    public HandlerRegistration addSelectionChangedHandler(SelectionChangedHandler handler) {
        return this.addHandler(handler, SelectionChangedEvent.TYPE);
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
    public void add(Widget w) {
    	if (w.getClass().toString().endsWith(".ListItem")) {
    		super.add(w);
    	}
    	else {
        	ListItem listItem = new ListItem();
        	super.add(listItem);    	
        	listItem.add(w);
        	if (_showArrow) {
	        	Chevron chevron = new Chevron();
	        	listItem.add(chevron);
        	}
    	}
    }

    @Override
    public void onClick(ClickEvent e) {
        if (_selected >= 0) {
    		ListItem item = (ListItem) getWidget(_selected);
    		if (!item.getDisabled()) {
	            SelectionChangedEvent selectionChangedEvent = new SelectionChangedEvent(_selected, 
	            	e.getNativeEvent().getEventTarget());
	            this.fireEvent(selectionChangedEvent);
	        	item.removeStyleName("Pressed");
    		}
    		_selected = -1;
        }
    }
    
    public void setShowArrow(boolean show) {
    	_showArrow = show;
    	for (int i = 0; i < getWidgetCount(); i++) {
    		ListItem listItem = (ListItem) getWidget(i);
			listItem.setShowArrowFromParent(show);
		}
    }
    
    public boolean getShowArrow() {
    	return _showArrow;
    }
    
    public void setSelectable(boolean selectable) {
    	_selectable  = selectable;
    }
    
    public boolean getSelectable() {
    	return _selectable;
    }
    
    @Override
    public void onDragStart(DragEvent e) {
    	if (_selectable) {
	    	_selected = Utils.getTargetItemIndex(getElement(), e.getNativeEvent().getEventTarget());
	    	if (_selected >= 0) {
	    		ListItem item = (ListItem) getWidget(_selected);
	    		if (!item.getDisabled()) {
		        	getWidget(_selected).addStyleName("Pressed");
	    		}
	    	}
    	}
    }

    @Override
    public void onDragMove(DragEvent e) {
    	if (_selected >= 0) {
        	getWidget(_selected).removeStyleName("Pressed");
    		_selected = -1;
    	}
    }

    @Override
    public void onDragEnd(DragEvent e) {
    	if (_selected >= 0) {
        	getWidget(_selected).removeStyleName("Pressed");
    		//_selected = -1; need to keep the selected value for click event.
    	}
    }
    
    static class Chevron extends HTML {    	
    	public Chevron() {
    		super("<div class=\"Chevron\"><span></span><span></span></div>");
    	}
    }

}
