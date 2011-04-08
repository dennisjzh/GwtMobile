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
import com.google.gwt.user.client.ui.HTML;
import com.gwtmobile.ui.client.event.DragController;
import com.gwtmobile.ui.client.event.DragEvent;
import com.gwtmobile.ui.client.event.DragEventsHandler;


public class AccordionHeader extends PanelBase implements ClickHandler, DragEventsHandler {

	public AccordionHeader() {
	    this.addDomHandler(this, ClickEvent.getType());    
        this.add(new AccordionArrow());
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
	
    @Override
    public void onClick(ClickEvent event) {
        AccordionStack parent = (AccordionStack) this.getParent().getParent();
        parent.toggle();
    }
    
	@Override
	public void onDragEnd(DragEvent e) {
		removeStyleName("Pressed");
	}

	@Override
	public void onDragMove(DragEvent e) {
		removeStyleName("Pressed");
	}

	@Override
	public void onDragStart(DragEvent e) {
		addStyleName("Pressed");		
	}

	
    static class AccordionArrow extends HTML {    	
    	public AccordionArrow() {
    		//super("<div/>");
    		setStyleName("AccordionArrow");
    	}
    }


}
