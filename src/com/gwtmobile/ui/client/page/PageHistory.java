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

package com.gwtmobile.ui.client.page;

import java.util.Stack;

public class PageHistory {

	private static Stack<Page> _history = new Stack<Page>();
	private static Object _returnValue; 
	
	public static void add(Page page) {
		_history.push(page);
	}
	
	public static Page current() {
		if (_history.isEmpty()) {
			return null;
		}
		return _history.peek();
	}
	
	public static Page from() {
		int size =_history.size();
		if (size < 2) {
			return null;
		}
		return _history.elementAt(size - 2);
	}
    
	public static Page back(Object returnValue) {
        _returnValue = returnValue;
        return back();
    }
    
	public static Page back() {
        if (_history.isEmpty()) {
            return null;
        }
        return _history.pop();
    }
    
	public static void setReturnValue(Object returnValue) {
        _returnValue = returnValue;
    }
    
	public static Object getReturnValue() {
        return _returnValue;
    }
}
