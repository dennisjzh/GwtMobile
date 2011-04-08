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

package com.gwtmobile.ui.client.event;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.user.client.Event;
import com.gwtmobile.ui.client.utils.Point;
import com.gwtmobile.ui.client.utils.Utils;

public class DragControllerDesktop extends DragController {

    DragControllerDesktop() {
    }
    
    @Override
    protected void registerEvents() {
        super.registerEvents();
        if (_dragStartListener == null) {
            _dragStartListener = Utils.addEventListener(_source.getElement(), "mousedown", true, this);
            _dragMoveListener = Utils.addEventListener(_source.getElement(), "mousemove", true, this);
            _dragEndListener = Utils.addEventListener(_source.getElement(), "mouseup", true, this);
        }
    }
    
    @Override
    protected void unregisterEvents() {
        super.unregisterEvents();
        if (_dragStartListener != null) {
            Utils.removeEventListener(_source.getElement(), "mousedown", true, _dragStartListener);
            Utils.removeEventListener(_source.getElement(), "mousemove", true, _dragMoveListener);
            Utils.removeEventListener(_source.getElement(), "mouseup", true, _dragEndListener);
            _dragStartListener = _dragMoveListener = _dragEndListener = null;
        }
    }

    
	public void onMouseDown(Event e) {
        EventTarget target = e.getEventTarget();
        boolean preventDefault = true;
        if (Element.is(target)) {
            Element ele = Element.as(target);
            //INPUT element will not get focus if default action is prevented.            
            if (Utils.isHtmlFormControl(ele)) {
                ele.focus();
                preventDefault = false;
            }
        }
        if (preventDefault) {
            e.preventDefault();   //prevent default action of selecting text            
            e.stopPropagation();
    		onStart(e, new Point(e.getClientX(), e.getClientY()));
        }
	}
	
    public void onMouseMove(Event e) {
		e.preventDefault();
		e.stopPropagation();
        onMove(e, new Point(e.getClientX(), e.getClientY()));
	}
	
	public void onMouseUp(Event e) {
		e.preventDefault();
		e.stopPropagation();
        onEnd(e, new Point(e.getClientX(), e.getClientY()));
	}
	
	@Override
    public void onBrowserEvent(Event e) {
		String type = e.getType();
        //Utils.Console(type + " from drag controller desktop.");
		if (type.equals("mousedown")) {
			onMouseDown(e);
		}
		else if (type.equals("mousemove")) {
			onMouseMove(e);
		}
		else if (type.equals("mouseup")) {
			onMouseUp(e);
		}
		else {
	        super.onBrowserEvent(e);
		}
	}
}
