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
    		create();
    		break;
    	case 1:
    		find();
    		break;
    	case 2:
    		cloneContact();
    		break;
    	case 3:
    		delete();
    		break;
    	}
    }

    public void create() {
		final Contact contact = Contacts.newInstance();
		contact.setDisplayName("Plumber");
		contact.setNickname("Plumber");
		
		ContactName name = ContactName.newInstance();
		name.setGivenName("Jane");
		name.setFamilyName("Doe");
		contact.setDisplayName(name.getGivenName() + " " + name.getFamilyName());
		contact.setName(name);
		
		contact.save(new Callback() {			
			@Override
			public void onSuccess() {
				text.setHTML("Contact " + contact.getName().getGivenName() + " " 
						+ contact.getName().getFamilyName() + " created.");
			}			
			@Override
			public void onError(ContactError error) {
				text.setHTML("Contact creation failed.<br/>" + error.getCode());
			}
		});
	}

    public void find() {
		try {
			Contacts.find(new ContactFields("nickname", "name"), new ContactFindCallback() {
				@Override
				public void onSuccess(JsArray<Contact> contacts) {
					text.setHTML("Find contact " + contacts.length());
					for (int i = 0; i < contacts.length(); i++) {
						text.setHTML(text.getHTML() + "<br/> " + contacts.get(i).getNickname() + 
								" (" + contacts.get(i).getName().getGivenName() +
								" " + contacts.get(i).getName().getFamilyName() + ")");
					}
				}
				@Override
				public void onError(ContactError error) {
					text.setHTML("Contact find failed.<br/>" + error.getCode());
				}
			}, new ContactFindOptions().filter("Plumber"));
		
		} 
		catch (Exception exception) {
			text.setHTML(exception.getCause().toString());
		}
	}
    
    private void delete() {
		Contacts.find(new ContactFields("nickname", "name"), new ContactFindCallback() {
			@Override
			public void onSuccess(JsArray<Contact> contacts) {
				if (contacts.length() > 0) {
					for (int i = 0; i < contacts.length(); i++) {
						final Contact contact = contacts.get(i);
						contact.remove(new Callback() {
							@Override
							public void onSuccess() {
								text.setHTML(contact.getNickname() + 
										" (" + contact.getName().getGivenName() +
										" " + contact.getName().getFamilyName() + 
										") removed.<br/>" + text.getHTML());
							}
							@Override
							public void onError(ContactError error) {
								text.setHTML(text.getHTML() + "<br/> Failed to remove contact. " + error.getCode());
							}
						});					
					}
				}
				else {
					text.setHTML("Contact to delete not found.<br/>");
				}
			}
			@Override
			public void onError(ContactError error) {
				text.setHTML("Failed to find contact to delete.<br/>" + error.getCode());
			}
		}, new ContactFindOptions().filter("Plumber"));
    }
    
    private void cloneContact() {
		Contacts.find(new ContactFields("nickname", "name"), new ContactFindCallback() {
			@Override
			public void onSuccess(JsArray<Contact> contacts) {
				if (contacts.length() > 0) {
					final Contact contact = contacts.get(0);
					final Contact clone = contact.clone();
					clone.getName().setGivenName(clone.getName().getGivenName() + "-Clone");
					clone.getName().setFamilyName(clone.getName().getFamilyName() + "-Clone");
					clone.setNickname(clone.getNickname() + "-Clone");
					clone.setDisplayName(clone.getDisplayName() + "-Clone");
					clone.save(new Callback() {
						@Override
						public void onSuccess() {
							text.setHTML(clone.getNickname() + 
									" (" + clone.getName().getGivenName() +
									" " + clone.getName().getFamilyName() + 
									") saved.<br/>");
						}
						@Override
						public void onError(ContactError error) {
							text.setHTML(text.getHTML() + "<br/> Failed to save cloned contact. " + error.getCode());
						}
					});
				}
				else {
					text.setHTML(text.getHTML() + "<br/> Contact to clone not found.<br/>");
				}
			}
			@Override
			public void onError(ContactError error) {
				text.setHTML("Failed to find contact to clone.<br/>" + error.getCode());
			}
		}, new ContactFindOptions().filter("Plumber"));
    }

}
