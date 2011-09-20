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
import java.util.Iterator;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.event.DragController;
import com.gwtmobile.ui.client.event.SwipeEvent;
import com.gwtmobile.ui.client.event.SwipeEventsHandler;
import com.gwtmobile.ui.client.page.Transition;

//FIXME: inherit from PanelBase
public class SlidePanel extends WidgetBase implements HasWidgets, SwipeEventsHandler, HasValueChangeHandlers<Boolean> {

    protected FlowPanel _panel = new FlowPanel();
    protected int _count = 0;
    protected int _current = 0;
    protected SlideProvider _slideProvider = null;
    protected ArrayList<Widget> _slides = new ArrayList<Widget>();
    protected boolean _rotate = false;

    public SlidePanel() {
        initWidget(_panel);
        setStyleName("SlidePanel");
    }

    public void setSlideCount(int count) {
    	_count = count;
    }
    
    public int getSlideCount() {
    	return _count > 0 ? _count : _slides.size();
    }
    
    public void setSlideProvider(SlideProvider provider) {
		_slideProvider = provider;
	}

	public SlideProvider getSlideProvider() {
		return _slideProvider;
	}

	@Override
	public void onInitialLoad() {
    	super.onInitialLoad();
		_current = 0;
    	Slide slide = getSlide(_current);
		if (slide != null) {
			_panel.add(slide);
		}
    }

	public Slide getSlide(int index) {
		Slide slide = null;
    	if (_slideProvider != null) {
    		slide = _slideProvider.loadSlide(index);
    	}
		if (slide == null && index < _slides.size() ) {
			slide = (Slide) _slides.get(index);
		}
		return slide;
	}
    
	@Override
	public void onLoad() {
		super.onLoad();		
        DragController.get().addSwipeEventsHandler(this);
	}
	
	@Override
	protected void onUnload() {
        DragController.get().removeSwipeEventHandler(this);
		super.onUnload();
	}
	
	@Override
	public void onSwipeHorizontal(SwipeEvent e) {
		if (e.getSpeed() < 0) { //swipe to next
			next();
		}
		else {					//swipe to previous
			previous();
		}
	}

	@Override
	public void onSwipeVertical(SwipeEvent e) {
	}
	
	public void next() {
		if (_current >= getSlideCount() - 1) {
			if (!_rotate) {
				return;
			} else {
				_current = -1;
			}
		}
		_current++;
    	moveNext();
	}

	protected void moveNext() {
		Slide to = getSlide(_current);
    	Slide from = (Slide) _panel.getWidget(0);
    	Transition transition = Transition.SLIDE;
    	ValueChangeEvent.fire(this, true);
    	transition.start(from, to, _panel, false);
	}

	public void previous() {
		if (_current <= 0) {
			if (!_rotate) {
				return;
			} else {
				_current = getSlideCount();
			}
		}
		_current--;
		movePrevious();
	}

	protected void movePrevious() {
		Slide to = getSlide(_current);
    	Slide from = (Slide) _panel.getWidget(0);
    	Transition transition = Transition.SLIDE;
    	ValueChangeEvent.fire(this, false);
    	transition.start(from, to, _panel, true);
	}

	@Override
	public void onTransitionEnd() {
		super.onTransitionEnd();
		_panel.remove(0);
	}
	
	public int getCurrentSlideIndex() {
		return _current;
	}
	
	@Override
	public void add(Widget w) {
		assert (w instanceof Slide) : "Can only add Slide widgets to SlidePanel.";
		_slides.add(w);
	}

	@Override
	public void clear() {
		_slides.clear();
		
	}

	@Override
	public Iterator<Widget> iterator() {
		return _slides.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return _slides.remove(w);
	}
	
	public void setRotate(boolean rotate)
	{
		_rotate = rotate;
	}
	
	public boolean isRotate()
	{
	    return _rotate;
	}
	
	
	/********************* interface SlideProvider *******************/

	public interface SlideProvider {
		Slide loadSlide(int index);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Boolean> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}
}