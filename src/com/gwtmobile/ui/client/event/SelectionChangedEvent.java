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

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.shared.GwtEvent;

public class SelectionChangedEvent extends GwtEvent<SelectionChangedHandler> {

	public static final Type<SelectionChangedHandler> TYPE = new Type<SelectionChangedHandler>();
	private int _selection;
	private EventTarget _target;
	
	public SelectionChangedEvent(int selection, EventTarget target) {
		_selection = selection;
		_target = target;
	}
	
	public int getSelection() {
		return _selection;
	}
	
	public EventTarget getTarget() {
	    return _target;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SelectionChangedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectionChangedHandler handler) {
		handler.onSelectionChanged(this);
	}

}
