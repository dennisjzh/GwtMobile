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

import java.util.Iterator;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class Tab extends WidgetBase implements HasWidgets {
    
    private TabHeader _header;
    private TabContent _content;

    public Tab() {
    }
    
    public TabHeader getHeader() {
        return _header;
    }    
    public TabContent getContent() {
        return _content;
    } 
    
    @Override
    public void add(Widget w) {    	
        if (this.getWidget() == null) {
        	assert w instanceof TabHeader : "Expected a TabHeader widget in a Tab";
            _header = (TabHeader)w;
            initWidget(_header);
        }
        else {
        	assert w instanceof TabContent : "Expected a TabContent widget in a Tab";
            _content = (TabContent) w;
        }
    }

    @Override
    public void clear() {
    }

    @Override
    public Iterator<Widget> iterator() {
        return null;
    }

    @Override
    public boolean remove(Widget w) {
        return false;
    }

}
