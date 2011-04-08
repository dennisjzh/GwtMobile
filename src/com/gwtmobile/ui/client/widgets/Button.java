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

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.gwtmobile.ui.client.event.DragController;
import com.gwtmobile.ui.client.event.DragEvent;
import com.gwtmobile.ui.client.event.DragEventsHandler;

public class Button extends HTML implements DragEventsHandler{

	private boolean _isDisabled = false;
	
    public Button() {
        setStyleName("Button");
    }
    
    public Button(String caption, ClickHandler handler) {
        this();
        setHTML(caption);
        this.addClickHandler(handler);
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
    	if (!_isDisabled) {
            addStyleName("Pressed");
    	}
    }

    @Override
    public void onDragMove(DragEvent e) {
    	if (!_isDisabled) {
    		removeStyleName("Pressed");       
    	}
    }

    @Override
    public void onDragEnd(DragEvent e) {
    	if (!_isDisabled) {
    		removeStyleName("Pressed");        
    	}
    }
    
    public void setDisabled(boolean disabled) {
    	_isDisabled = disabled;
    	if (disabled) {
    		addStyleName("Disabled");
    	}
    	else {
    		removeStyleName("Disabled");
    	}
    }
    
    public boolean isDisabled() {
    	return _isDisabled;
    }
}
