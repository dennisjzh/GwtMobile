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
import com.google.gwt.core.client.JsArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.phonegap.client.Contacts;
import com.gwtmobile.phonegap.client.Contacts.Callback;
import com.gwtmobile.phonegap.client.Contacts.Contact;
import com.gwtmobile.phonegap.client.Contacts.ContactError;
import com.gwtmobile.phonegap.client.Contacts.ContactFields;
import com.gwtmobile.phonegap.client.Contacts.ContactFindCallback;
import com.gwtmobile.phonegap.client.Contacts.ContactFindOptions;
import com.gwtmobile.phonegap.client.Contacts.ContactName;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;

public class ContactsUi extends Page {

	@UiField HTML text;
	String watchId;

	private static ContactsUiUiBinder uiBinder = GWT
			.create(ContactsUiUiBinder.class);

	interface ContactsUiUiBinder extends UiBinder<Widget, ContactsUi> {
	}

	public ContactsUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		//create();
    		break;
    	case 1:
    		find();
    		break;
    	case 2:
    		break;
    	case 3:
    		break;
    	}
    }

    public void create() {
		Contact contact = Contacts.newInstance();
		contact.setDisplayName("Plumber");
		contact.setNickname("Plumber");
		
		ContactName name = ContactName.newInstance();
		name.setGivenName("Jane");
		name.setFamilyName("Doe");
		contact.setName(name);
		
		contact.save(new Callback() {			
			@Override
			public void onSuccess() {
				text.setHTML("Contact created.");
			}			
			@Override
			public void onError(ContactError error) {
				text.setHTML("Contact creation failed.<br/>" + error);
			}
		});
	}

    public void find() {
		try {
			
			Contacts.find(new ContactFields("nickname"), new ContactFindCallback() {
				@Override
				public void onSuccess(JsArray<Contact> contacts) {
					text.setHTML("Find contact " + contacts.length());
					if (contacts.length() > 0) {
						text.setHTML(text.getHTML() + "<br/>Nickname: " + contacts.get(0).getNickname());
					}
				}
				@Override
				public void onError(ContactError error) {
					text.setHTML("Contact find failed.<br/>" + error);
				}
			}, new ContactFindOptions().filter("Plumber"));
		
		} catch (Exception exception) {
			
			text.setHTML(exception.getCause().toString());
			
		}
		
	}


}
