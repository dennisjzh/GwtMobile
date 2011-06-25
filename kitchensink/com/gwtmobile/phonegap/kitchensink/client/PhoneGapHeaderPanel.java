package com.gwtmobile.phonegap.kitchensink.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtmobile.phonegap.client.plugins.ChildBrowser;
import com.gwtmobile.ui.client.widgets.HeaderPanel;

public class PhoneGapHeaderPanel extends HeaderPanel {

	private String _doc;
	
	public PhoneGapHeaderPanel() {
		this.setLeftButton("Back");
		this.setRightButton("Doc");
		this.setRightButtonClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				String url = "http://docs.phonegap.com/";
				String caption = PhoneGapHeaderPanel.this.getCaption();
				url = url + "phonegap_" + getDocument() + "_" + getDocument() + 
					".md.html#" + caption;
				ChildBrowser.showWebPage(url, true);
			}
		});
	}
	
	public void setDocument(String doc) {
		_doc = doc;
	}
	
	public String getDocument() {
		if (_doc == null) {
			return getCaption().toLowerCase();
		}
		else {
			return _doc;
		}
		
	}
}
