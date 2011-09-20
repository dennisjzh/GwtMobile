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
 * 
 * Authors:
 * 	Zhihua (Dennis) Jiang
 * 	ash
 */


package com.gwtmobile.ui.client.page;

import com.google.gwt.core.client.GWT;

/**
 * Represents a history navigation model that can be for moving between
 * pages.
 */
public interface PageHistory {
	
	public static PageHistory Instance = GWT.create(SerialPageHistory.class);
	
	/**
	 * Light-weight page mapping interface that is used to retrieve page
	 * implementations as required. Note, implementations are required
	 * to accommodate caching strategies if required.
	 */
	public interface Mapper {
		public Page getPage(String pageName);
	}

	public void add(Page page);
	
	public void navigate(String token);
	
	public void navigate(String pageName, String params);

	public Page current();

	public Page from();

	public Page back();
	
	public void goBack(Page fromPage, Object returnValue);

	public void setMapper(Mapper mapper);
	
	public void startUp(Object param);
	
	public void setReturnValue(Object returnValue);
    
	public Object getReturnValue();

}