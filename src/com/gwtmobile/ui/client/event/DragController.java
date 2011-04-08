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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.utils.Point;
import com.gwtmobile.ui.client.utils.Utils;

public abstract class DragController implements EventListener {

    private List<DragEventsHandler> _dragEventHandlers = new ArrayList<DragEventsHandler>();
    private List<SwipeEventsHandler> _swipeEventHandlers = new ArrayList<SwipeEventsHandler>();
    protected DragEventsHandler _capturingDragEventsHandler = null;
    protected Widget _source;
	private boolean _isDown = false;
	private boolean _suppressNextClick = false;
	private long _lastDragTimeStamp = 0;
	private Point _lastDragPos = new Point(0, 0);
	private long _currDragTimeStamp = 0;
	private Point _currDragPos = new Point(0, 0);
	
	private JavaScriptObject _clickListener;
    protected JavaScriptObject _dragStartListener;
    protected JavaScriptObject _dragMoveListener;
    protected JavaScriptObject _dragEndListener;

	
	protected static DragController INSTANCE = GWT.create(DragController.class);

	DragController() {
	    Utils.Console("New DragController instance created");
	    init();
	}

    public static DragController get() {
        return INSTANCE;
    }
    
	protected void init() {
	    _source = RootLayoutPanel.get();
        registerEvents();
    }

    public void addDragEventsHandler(DragEventsHandler dragHandler) {
        _dragEventHandlers.add(dragHandler);
	}
	
	public void addSwipeEventsHandler(SwipeEventsHandler swipeHandler) {
        _swipeEventHandlers.add(swipeHandler);
	}
	
    public void removeDragEventsHandler(DragEventsHandler dragHandler) {
        _dragEventHandlers.remove(dragHandler);
    }

    public void removeSwipeEventHandler(SwipeEventsHandler swipeHandler) {
        _swipeEventHandlers.remove(swipeHandler);
    }   
	
	@Override
    public void onBrowserEvent(Event e) {
        String type = e.getType();
        if (type.equals("click")) {
            onClick(e);
        }
	}
	
    private void onClick(Event e) {
        if (_suppressNextClick) {
            e.stopPropagation();
            _suppressNextClick = false;
            Utils.Console("click suppressed");
        }
    }
 
	//TODO: May need an onPreStart event to indicate that mouse is down, but no movement yet,
	//      so onStart event can actually mean drag is indeed started.	
    protected void onStart(Event e, Point p) {
        _isDown = true;
        _suppressNextClick = false;
        Date currentDateTime = new Date();
        _lastDragTimeStamp = currentDateTime.getTime();
        _currDragTimeStamp = _lastDragTimeStamp; 
        _lastDragPos.clone(p);
        _currDragPos.clone(p);
        DragEvent dragEvent = new DragEvent(e, DragEvent.Type.Start, 
                p.X(), p.Y(), p.X() - _currDragPos.X(), p.Y() - _currDragPos.Y());          
        fireDragEvent(dragEvent);
        
    }
	
    protected void onMove(Event e, Point p) {
        if (_isDown) {
            if (p.equals(_currDragPos)) {
                Utils.Console("NO movement onMove");
                return;
            }
            _suppressNextClick = true;
            DragEvent dragEvent = new DragEvent(e, DragEvent.Type.Move, 
                    p.X(), p.Y(), p.X() - _currDragPos.X(), p.Y() - _currDragPos.Y());          
            fireDragEvent(dragEvent);
            _lastDragPos.clone(_currDragPos);
            _lastDragTimeStamp = _currDragTimeStamp;
            _currDragPos.clone(p);
            Date currentDateTime = new Date();
            _currDragTimeStamp = currentDateTime.getTime();            
        }
    }
    
    protected void onEnd(Event e, Point p) {
        if (_isDown) {
            _isDown = false;
            DragEvent dragEvent = new DragEvent(e, DragEvent.Type.End, 
                    p.X(), p.Y(), p.X() - _currDragPos.X(), p.Y() - _currDragPos.Y());          
            fireDragEvent(dragEvent);
            double distanceX = p.X() - _lastDragPos.X();
            double distanceY = p.Y() - _lastDragPos.Y();
            double distance;
            SwipeEvent.Type swipeType; 
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
            	distance = distanceX;
            	swipeType = SwipeEvent.Type.Horizontal;
            }
            else {
            	distance = distanceY;
            	swipeType = SwipeEvent.Type.Vertical;
            }
            Date currentDateTime = new Date();
            long time = currentDateTime.getTime() - _lastDragTimeStamp;
            double speed = distance / time;
            if (Math.abs(speed) > 0.2)
            {
                SwipeEvent swipeEvent = new SwipeEvent(e, swipeType, speed);
                fireSwipeEvent(swipeEvent);
            }
        }
    }
    
    protected void fireDragEvent(DragEvent e) {
    	if (_capturingDragEventsHandler != null) {
    		e.dispatch(_capturingDragEventsHandler);
    		return;
    	}
        EventTarget target = e.getNativeEvent().getEventTarget();
        Node node = Node.as(target);        
        if (!Element.is(node)) {
        	node = node.getParentNode();	//Text node
        }
        if (Element.is(node)) {
             Element ele = Element.as(target);
            int count = 0;
            while (ele != null) {
                for (DragEventsHandler handler : _dragEventHandlers) {
                    if (ele.equals(handler.getElement())) {
                        e.dispatch(handler);
                        count++;
                        if (e.getStopPropagation() || count == _dragEventHandlers.size()) {
                            return;
                        }
                    }
                }
                ele = ele.getParentElement();
            }
        }
    }

    protected void fireSwipeEvent(SwipeEvent e) {
        EventTarget target = e.getNativeEvent().getEventTarget();
        Node node = Node.as(target);        
        if (!Element.is(node)) {
        	node = node.getParentNode();	//Text node
        }
        if (Element.is(node)) {
            Element ele = Element.as(target);
            int count = 0;
            while (ele != null) {
                for (SwipeEventsHandler handler : _swipeEventHandlers) {
                    if (ele.equals(handler.getElement())) {
                        e.dispatch(handler);
                        count++;
                        if (e.getStopPropagation() || count == _swipeEventHandlers.size()) {
                            return;
                        }
                    }
                }
                ele = ele.getParentElement();
            }
        }
    }

    public void suppressNextClick() {
        _suppressNextClick = true;
    }

    protected void registerEvents() {
        if (_clickListener == null) {
            _clickListener = Utils.addEventListener(_source.getElement(), "click", true, this);
        }
    }
    
    protected void unregisterEvents() {
        if (_clickListener != null) {
            Utils.removeEventListener(_source.getElement(), "click", true, _clickListener);
            _clickListener = null;
        }
    }

    public void suspend() {
        unregisterEvents();
        Utils.Console("drag events suspended.");
    }

    public void resume() {
        registerEvents();
        Utils.Console("drag events resumed.");
    }
    
    public boolean captureDragEvents(DragEventsHandler cachingHandler) {
    	if (_capturingDragEventsHandler != null) {
    		return false;
    	}
    	_capturingDragEventsHandler = cachingHandler;
    	return true;
    }
    
    public boolean releaseCapture(DragEventsHandler cachingHandler) {
    	if (_capturingDragEventsHandler == null) {
    		return true;
    	}
    	if (_capturingDragEventsHandler != cachingHandler) {
    		return false;
    	}
    	_capturingDragEventsHandler = null;
    	return true;
    }
}
