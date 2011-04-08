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
import com.gwtmobile.ui.client.page.PageHistory;

public class BackButton extends Button implements ClickHandler {

    private Object _parameter;
    
    public BackButton() {
        setHTML("Back");
        this.addClickHandler(this);
        setStyleName("BackButton");
    }

    public BackButton(String caption, ClickHandler handler) {
        setHTML(caption);
        this.addClickHandler(handler);
        setStyleName("BackButton");
    }

    @Override
    public void setHTML(String html) {
    	super.setHTML("<span class=\"Pointer\"></span>" + "<span class=\"Button\">" + html + "</span>");
    }
    
    public void setReturnParameter(Object parameter) {
        _parameter = parameter;
    }

    @Override
    public void onClick(ClickEvent event) {
        PageHistory.current().goBack(_parameter);
    }
}
