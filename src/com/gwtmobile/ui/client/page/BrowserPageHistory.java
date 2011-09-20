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
 * Authors:
 * 	Zhihua (Dennis) Jiang
 * 	ash
 */

package com.gwtmobile.ui.client.page;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.gwtmobile.ui.client.utils.Utils;

/**
 * This is a web app oriented history navigation model. This navigation model
 * used the browser's forward/back/direct url reference scheme.
 * 
 * If your app needs to run as a web application as well as a native 
 * application then add a reference the following to your GWT module:
 *
 * <pre>
 * &lt;replace-with
 *     class="com.gwtmobile.ui.client.page.BrowserPageHistory"&gt;
 *         &lt;when-type-is
 *         class="com.gwtmobile.ui.client.page.SerialPageHistory" /&gt;
 * &lt;/replace-with&gt;
 * </pre>
 * 
 * @see PageHistory#navigateTo(String, String)
 * @see PageHistory.Mapper
 */
public class BrowserPageHistory extends SerialPageHistory implements ValueChangeHandler<String> {
	private Mapper _mapper;

	public BrowserPageHistory() {
		History.addValueChangeHandler(this);
	}
	
	@Override
	public void navigate(String token) {
		History.newItem(token, true);
	}
	
	@Override
	public void navigate(String pageName, String params) {
		String token = pageName + (params == null? "" : ":" + params);
		navigate(token);
	}

	@Override
	public void goBack(Page fromPage, Object returnValue) {
		History.back();
	}

	@Override
	public void setMapper(Mapper mapper) {
		_mapper = mapper;		
	}
	
	@Override
	public void startUp(Object param) {
		String token = History.getToken();
		loadPage(token);
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
		loadPage(token);
	}

	protected void loadPage(String token) {
		final String[] tokenRef = parseParams(token);
		final Page page = _mapper.getPage(tokenRef[0]);
		if (page == null) // FIXME: Maybe throw an IllegalArgumentException?
			Utils.Console("No page registered for history token:" + token);
		else {
			page.tokenStateInfo = tokenRef[1];
			Page current = current();
			if (current == null) {
				Page.load(page);
				page.initNavigationIfRequired();
			} else
				current.goTo(page);
		}
	}
	
	private String[] parseParams(String token) {
		String[] ret = {token, null};
		int pos = token.indexOf(':');
		if (pos > -1) {
			ret[0] = token.substring(0, pos);
			ret[1] = token.substring(pos+1);
		}
		return ret;
	}
}