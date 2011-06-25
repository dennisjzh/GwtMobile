/* Copyright (c) 2010 Zhihua (Dennis) Jiang
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

package com.gwtmobile.phonegap.kitchensink.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.phonegap.client.Storage.LocalStorage;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class StorageUi extends Page {

	private static StorageUiUiBinder uiBinder = GWT.create(StorageUiUiBinder.class);
	
	@UiField HTML text;

	interface StorageUiUiBinder extends UiBinder<Widget, StorageUi> {
	}

	public StorageUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		key();
    		break;
    	case 1:
    		setItem();
    		break;
    	case 2:
    		getItem();
    		break;
    	case 3:
    		removeItem();
    		break;
    	case 4:
    		clear();
    		break;
    	}
    }
    
    private void key() {
    	String key = LocalStorage.key(0);
    	text.setHTML("Key name at location 0 '" + key + "'");
    }

    private void setItem() {
    	String key = "key1";
    	String value = "value1";
    	LocalStorage.setItem(key, value);
    	text.setHTML("Item set, Key: " + key + ", Value: " + value);
    }

    private void getItem() {
    	String key = "key1";
    	String value = LocalStorage.getItem(key);
    	text.setHTML("Value on Key " + key + ": " + value);
    }

    private void removeItem() {
    	String key = "key1";
    	LocalStorage.removeItem(key);
    	text.setHTML("Storage with key " + key + "removed");
    }

    private void clear() {
    	LocalStorage.clear();
    	text.setHTML("Storage cleared.");
    }

}
