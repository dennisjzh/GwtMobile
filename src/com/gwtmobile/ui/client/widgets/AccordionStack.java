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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.utils.Utils;


public class AccordionStack extends PanelBase {

    private AccordionHeader _header;
    private AccordionContent _content;
    
	@Override
    protected void onInitialLoad( ) {
        if (this.getStyleName().indexOf("Expand") == -1) {
        	close();
        }
	}
	
    @Override
    public void add(Widget w) {
        if (_header == null) {
            _header = (AccordionHeader) w;
        }
        else if (_content != null) {
            assert false : "Can only contain a header and a content.";
        }
        else {
            _content = (AccordionContent) w;
        }
        _panel.add(w);
    }

    public void close() {
        this.addStyleName("Collapse");
		this.removeStyleName("Expand");
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {			
			@Override
			public void execute() {
		        _content.setHeight("0px");
			}
		});
    }

    public void expand() {
        this.addStyleName("Expand");
        this.removeStyleName("Collapse");
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {			
			@Override
			public void execute() {
				Utils.Console(_content.getElement().getScrollHeight() - _content.getElement().getOffsetHeight() + "px");
		        _content.setHeight(_content.getElement().getScrollHeight() - _content.getElement().getOffsetHeight()+ "px");
			}
		});
    }
    
    public void toggle() {
        if (this.getStyleName().indexOf("Expand") == -1) {
        	expand();
        }
        else {
        	close();
        }        
    }
    
    public AccordionHeader getHeader() {
        return _header;
    }
    
    public AccordionContent getContent() {
        return _content;
    }
}
