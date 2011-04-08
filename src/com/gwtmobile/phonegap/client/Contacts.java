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

package com.gwtmobile.phonegap.client;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

public class Contacts {
	
	public static final native Contact newInstance() /*-{
		return new $wnd.navigator.service.contacts.create();
	}-*/;

	public static final void find(ContactFields fields, ContactFindCallback callback, ContactFindOptions options) {
		find(fields.getFields(), callback, options.getOptions());
	}
	
	private static final native void find(JsArrayString fields, ContactFindCallback callback, JavaScriptObject options) /*-{
		$wnd.navigator.service.contacts.find(fields, function(contacts) {
			callback.@com.gwtmobile.phonegap.client.Contacts.ContactFindCallback::onSuccess(Lcom/google/gwt/core/client/JsArray;)(contacts);
		}, function(error) {
			callback.@com.gwtmobile.phonegap.client.Contacts.ContactFindCallback::onError(Lcom/gwtmobile/phonegap/client/Contacts$ContactError;)(error);
		}, options);
	}-*/;
	
	
	public static class Contact extends JavaScriptObject {
		
		protected Contact() {}
		
		public final native void save(Callback callback) /*-{
			this.save(function() {
				callback.@com.gwtmobile.phonegap.client.Contacts.Callback::onSuccess()();
			}, function(error) {
				callback.@com.gwtmobile.phonegap.client.Contacts.Callback::onError(Lcom/gwtmobile/phonegap/client/Contacts$ContactError;)(error);
			});
		}-*/;
		
		public final native Contact clone() /*-{
			return this.clone();
		}-*/;
		
		public final native void remove(Callback callback) /*-{
			this.remove(function() {
				callback.@com.gwtmobile.phonegap.client.Contacts.Callback::onSuccess()();
			}, function(error) {
				callback.@com.gwtmobile.phonegap.client.Contacts.Callback::onError(Lcom/gwtmobile/phonegap/client/Contacts$ContactError;)(error);
			});
		}-*/;
		
		public final native String getId() /*-{
			return this.id;
		}-*/;
	
		public final native void setId(String id) /*-{
			this.id = id;
		}-*/;

		public final native String getDisplayName() /*-{
			return this.displayName;
		}-*/;
	
		public final native void setDisplayName(String displayName) /*-{
			this.displayName = displayName;
		}-*/;

		public final native ContactName getName() /*-{
			return this.name;
		}-*/;
	
		public final native void setName(ContactName name) /*-{
			this.name = name;
		}-*/;

		public final native String getNickname() /*-{
			return this.nickname;
		}-*/;
	
		public final native void setNickname(String nickname) /*-{
			this.nickname = nickname;
		}-*/;

		public final native JsArray<ContactField> getPhoneNumbers() /*-{
			return this.phoneNumbers;
		}-*/;
	
		public final native void getPhoneNumbers(JsArray<ContactField> phoneNumbers) /*-{
			this.phoneNumbers = phoneNumbers;
		}-*/;

		public final native JsArray<ContactField> getEmails() /*-{
			return this.emails;
		}-*/;
	
		public final native void setEmails(JsArray<ContactField> emails) /*-{
			this.emails = emails;
		}-*/;

		public final native JsArray<ContactAddress> getAddresses() /*-{
			return this.addresses;
		}-*/;
	
		public final native void setAddresses(JsArray<ContactAddress> addresses) /*-{
			this.addresses = addresses;
		}-*/;

		public final native JsArray<ContactField> getIMs() /*-{
			return this.ims;
		}-*/;
	
		public final native void setIMs(JsArray<ContactField> ims) /*-{
			this.ims = ims;
		}-*/;
	
		public final native JsArray<ContactOrganization> getOrganizations() /*-{
			return this.organizations;
		}-*/;
	
		public final native void getOrganizations(JsArray<ContactOrganization> organizations) /*-{
			this.organizations = organizations;
		}-*/;
	
		public final native String getPublished() /*-{
			return this.published;
		}-*/;
	
		public final native void setPublished(String published) /*-{
			this.published = published;
		}-*/;
	
		public final native String getUpdated() /*-{
			return this.updated;
		}-*/;
	
		public final native void setUpdated(String updated) /*-{
			this.updated = updated;
		}-*/;
	
		public final native String getBirthday() /*-{
			return this.birthday;
		}-*/;
	
		public final native void setBirthday(String birthday) /*-{
			this.birthday = birthday;
		}-*/;
	
		public final native String getAnniversary() /*-{
			return this.anniversary;
		}-*/;
	
		public final native void setAnniversary(String anniversity) /*-{
			this.anniversary = anniversity;
		}-*/;
	
		public final native String getGender() /*-{
			return this.gender;
		}-*/;
	
		public final native void setGender(String gender) /*-{
			this.gender = gender;
		}-*/;
	
		public final native String getNote() /*-{
			return this.note;
		}-*/;
	
		public final native void setNote(String note) /*-{
			this.note = note;
		}-*/;
	
		public final native String getPreferredUsername() /*-{
			return this.preferredUsername;
		}-*/;
	
		public final native void setPreferredUsername(String preferredUsername) /*-{
			this.preferredUsername = preferredUsername;
		}-*/;
	
		public final native JsArray<ContactField> getPhotos() /*-{
			return this.photos;
		}-*/;
	
		public final native void setPhotos(JsArray<ContactField> photos) /*-{
			this.photos = photos;
		}-*/;
	
		public final native JsArray<ContactField> getTags() /*-{
			return this.tags;
		}-*/;
	
		public final native void setTags(JsArray<ContactField> tags) /*-{
			this.tags = tags;
		}-*/;
	
		public final native JsArray<ContactField> getRelationships() /*-{
			return this.relationships;
		}-*/;
	
		public final native void setRelationships(JsArray<ContactField> relationships) /*-{
			this.relationships = relationships;
		}-*/;
	
		public final native JsArray<ContactField> getURLs() /*-{
			return this.urls;
		}-*/;
	
		public final native void setURLs(JsArray<ContactField> urls) /*-{
			this.urls = urls;
		}-*/;
	
		public final native JsArray<ContactAccount> getAccounts() /*-{
			return this.accounts;
		}-*/;
	
		public final native void setAccounts(JsArray<ContactAccount> accounts) /*-{
			this.accounts = accounts;
		}-*/;
	
		public final native String getUTCOffset() /*-{
			return this.utcOffset;
		}-*/;
	
		public final native void setUTCOffset(String utcOffset) /*-{
			this.utcOffset = utcOffset;
		}-*/;
	
		public final native boolean getConnected() /*-{
			return this.connected;
		}-*/;

		public final native void setConnected(boolean connected) /*-{
			this.connected = connected;
		}-*/;
	}

	public static class ContactName extends JavaScriptObject {
		
		protected ContactName() {};
		
		public static final ContactName newInstance() {
			return (ContactName) JavaScriptObject.createObject();
		}
		
		public final native String getFormatted() /*-{
			return this.formatted;
		}-*/;

		public final native void setFormatted(String formatted) /*-{
			this.formatted = formatted;
		}-*/;

		public final native String getFamilyName() /*-{
			return this.familyName;
		}-*/;

		public final native void setFamilyName(String familyName) /*-{
			this.familyName = familyName;
		}-*/;

		public final native String getGivenName() /*-{
			return this.givenName;
		}-*/;

		public final native void setGivenName(String givenName) /*-{
			this.givenName = givenName;
		}-*/;

		public final native String getMiddleName() /*-{
			return this.middleName;
		}-*/;

		public final native void setMiddleName(String middleName) /*-{
			this.middleName = middleName;
		}-*/;

		public final native String getHororificPrefix() /*-{
			return this.hororificPrefix;
		}-*/;

		public final native void setHororificPrefix(String hororificPrefix) /*-{
			this.hororificPrefix = hororificPrefix;
		}-*/;

		public final native String getHonorificSuffix() /*-{
			return this.honorificSuffix;
		}-*/;

		public final native void setHonorificSuffix(String honorificSuffix) /*-{
			this.honorificSuffix = honorificSuffix;
		}-*/;

	}

	public static class ContactField extends JavaScriptObject {
		
		protected ContactField() {};
		
		public static final ContactField newInstance() {
			return (ContactField) JavaScriptObject.createObject();
		}

		public final native String getType() /*-{
			return this.type;
		}-*/;

		public final native void setType(String type) /*-{
			this.type = type;
		}-*/;

		public final native String getValue() /*-{
			return this.value;
		}-*/;

		public final native void setValue(String value) /*-{
			this.value = value;
		}-*/;

		public final native boolean getPrimary() /*-{
			return this.primary;
		}-*/;

		public final native void setPrimary(boolean primary) /*-{
			this.primary = primary;
		}-*/;

	}
	
	public static class ContactAddress extends JavaScriptObject {
		
		protected ContactAddress() {};
		
		public static final ContactAddress newInstance() {
			return (ContactAddress) JavaScriptObject.createObject();
		}

		public final native String getFormatted() /*-{
			return this.formatted;
		}-*/;

		public final native void setFormatted(String formatted) /*-{
			this.formatted = formatted;
		}-*/;

		public final native String getStreetAddress() /*-{
			return this.streetAddress;
		}-*/;

		public final native void setStreetAddress(String streetAddress) /*-{
			this.streetAddress = streetAddress;
		}-*/;

		public final native String getLocality() /*-{
			return this.locality;
		}-*/;

		public final native void getLocality(String locality) /*-{
			this.locality = locality;
		}-*/;

		public final native String getRegion() /*-{
			return this.region;
		}-*/;

		public final native void setRegion(String region) /*-{
			this.region = region;
		}-*/;

		public final native String getPostalCode() /*-{
			return this.postalCode;
		}-*/;

		public final native void setPostalCode(String postalCode) /*-{
			this.postalCode = postalCode;
		}-*/;

		public final native String getCountry() /*-{
			return this.country;
		}-*/;

		public final native void setCountry(String country) /*-{
			this.country = country;
		}-*/;

	}

	public static class ContactOrganization extends JavaScriptObject {
		
		protected ContactOrganization() {};
		
		public static final ContactOrganization newInstance() {
			return (ContactOrganization) JavaScriptObject.createObject();
		}

		public final native String getName() /*-{
			return this.name;
		}-*/;

		public final native void setName(String name) /*-{
			this.name = name;
		}-*/;

		public final native String getDepartment() /*-{
			return this.department;
		}-*/;

		public final native void setDepartment(String department) /*-{
			this.department = department;
		}-*/;

		public final native String getTitle() /*-{
			return this.title;
		}-*/;

		public final native void setTitle(String title) /*-{
			this.title = title;
		}-*/;

		public final native String getStartDate() /*-{
			return this.startDate;
		}-*/;

		public final native void setStartDate(String startDate) /*-{
			this.startDate = startDate;
		}-*/;

		public final native String getEndDate() /*-{
			return this.endDate;
		}-*/;

		public final native void setEndDate(String endDate) /*-{
			this.endDate = endDate;
		}-*/;

		public final native String getLocation() /*-{
			return this.location;
		}-*/;

		public final native void setLocation(String location) /*-{
			this.location = location;
		}-*/;

		public final native String getDescription() /*-{
			return this.description;
		}-*/;

		public final native void setDescription(String description) /*-{
			this.description = description;
		}-*/;

	}

	public static class ContactAccount extends JavaScriptObject {
		
		protected ContactAccount() {};
		
		public static final ContactAccount newInstance() {
			return (ContactAccount) JavaScriptObject.createObject();
		}

		public final native String getDomain() /*-{
			return this.domain;
		}-*/;

		public final native void setDomain(String domain) /*-{
			this.domain = domain;
		}-*/;

		public final native String getUserName() /*-{
			return this.username;
		}-*/;

		public final native void setUserName(String username) /*-{
			this.username = username;
		}-*/;

		public final native String getUserId() /*-{
			return this.userid;
		}-*/;

		public final native void getUserId(String userid) /*-{
			this.userid = userid;
		}-*/;

	}
	
	public interface Callback {
		void onSuccess();
		void onError(ContactError error); 
	}
	
	public interface ContactFindCallback {
		void onSuccess(JsArray<Contact> contacts);
		void onError(ContactError error); 
	}
	
	public enum ContactErrorCode {
		UNKNOWN_ERROR, 
		INVALID_ARGUMENT_ERROR, 
		NOT_FOUND_ERROR, 
		TIMEOUT_ERROR, 
		PENDING_OPERATION_ERROR, 
		IO_ERROR, 
		NOT_SUPPORTED_ERROR, 
		PERMISSION_DENIED_ERROR
	}
	
	public static class ContactError extends JavaScriptObject {
		
		protected ContactError() {};
		
		public final ContactErrorCode getCode() {
			return ContactErrorCode.values()[getCodeNative()];
		}
		
		private final native int getCodeNative() /*-{
			return this.code;
		}-*/;
	}
	
	public static class ContactFields {
		
		JsArrayString fields = (JsArrayString) JsArrayString.createArray();
		
		public ContactFields(String field1, String... fields) {
			this.fields.push(field1);
			if (fields != null) {
				for (String field : fields) {
					this.fields.push(field);
				} 
			}
		};
		
		public JsArrayString getFields() {
			return fields;
		}
	}

	public static class ContactFindOptions {
		ContactFindOptions self = this;
		JavaScriptObject options = JavaScriptObject.createObject();

		public native ContactFindOptions filter(String filter) /*-{
			this.@com.gwtmobile.phonegap.client.Contacts.ContactFindOptions::options.filter = filter;
			return this.@com.gwtmobile.phonegap.client.Contacts.ContactFindOptions::self;
		}-*/;

		public native ContactFindOptions multiple(boolean multiple) /*-{
			this.@com.gwtmobile.phonegap.client.Contacts.ContactFindOptions::options.multiple = multiple;
			return this.@com.gwtmobile.phonegap.client.Contacts.ContactFindOptions::self;
		}-*/;

		public native ContactFindOptions limit(int limit) /*-{
			this.@com.gwtmobile.phonegap.client.Contacts.ContactFindOptions::options.limit = limit;
			return this.@com.gwtmobile.phonegap.client.Contacts.ContactFindOptions::self;
		}-*/;

		public ContactFindOptions updatedSince(Date updatedSince) {
			return updatedSince(updatedSince.toString());
		}

		private native ContactFindOptions updatedSince(String updatedSince) /*-{
			this.@com.gwtmobile.phonegap.client.Contacts.ContactFindOptions::options.updatedSince = filter;
			return this.@com.gwtmobile.phonegap.client.Contacts.ContactFindOptions::self;
		}-*/;

		private JavaScriptObject getOptions() {
			return options;
		}
	}

}
