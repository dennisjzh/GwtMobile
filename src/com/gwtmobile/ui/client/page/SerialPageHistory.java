/*
 * Copyright (c) 2011 Zhihua (Dennis) Jiang
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
 * 
 */

package com.gwtmobile.ui.client.page;

import java.util.Stack;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.gwtmobile.ui.client.utils.Utils;

/**
 * This implementation works well for a native application. However, for 
 * a hyrbrid, native/webapp consider using the browser navigation model.
 * 
 * @see BrowserPageHistory
 */
public class SerialPageHistory implements PageHistory {
	private Stack<Page> _history = new Stack<Page>();
	private Object _returnValue; 

	@Override
	public void add(Page page) {
		_history.push(page);
	}
	
	@Override
	public void navigate(String token) {
		// DO nothing
	}

	@Override
	public void navigate(String pageName, String params) {
		// DO nothing
	}

	@Override
	public Page current() {
		if (_history.isEmpty()) {
			return null;
		}
		return _history.peek();
	}
	
	@Override
	public Page from() {
		int size =_history.size();
		if (size < 2) {
			return null;
		}
		return _history.elementAt(size - 2);
	}
    
	@Override
	public Page back() {
        if (_history.isEmpty()) {
            return null;
        }
        return _history.pop();
    }
	
	@Override
	public void goBack(Page fromPage, Object returnValue) {
		setReturnValue(returnValue);
		final Page toPage = from();
		if (toPage == null) {
			// exit app here.
			return;
		}
		Element focus = Utils.getActiveElement();
		focus.blur();
		final Transition transition = fromPage.getTransition();
		if (transition != null) {
			transition.start(fromPage, toPage, RootLayoutPanel.get(), true);
		}
		else {
			Transition.start(fromPage, toPage, RootLayoutPanel.get());
		}
	}

	@Override
	public void setMapper(Mapper mapper) {
		// Do nothing b/c mapping is not required serial navigation
	}
	
	@Override
	public void startUp(Object param) {
		Page initial = (Page) param;
		Page.load(initial);
	}
	
	@Override
	public void setReturnValue(Object returnValue) {
        _returnValue = returnValue;
    }
    
	@Override
	public Object getReturnValue() {
        return _returnValue;
    }

}