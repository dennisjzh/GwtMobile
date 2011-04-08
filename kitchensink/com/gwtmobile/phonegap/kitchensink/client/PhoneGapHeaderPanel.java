package com.gwtmobile.phonegap.kitchensink.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwtmobile.ui.client.utils.Utils;
import com.gwtmobile.ui.client.widgets.HeaderPanel;

public class PhoneGapHeaderPanel extends HeaderPanel {

	public PhoneGapHeaderPanel() {
		this.setLeftButton("Back");
		this.setRightButton("Doc");
		this.setRightButtonClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				String url = "http://docs.phonegap.com/";
				String caption = PhoneGapHeaderPanel.this.getCaption();
				url = url + "phonegap_" + caption.toLowerCase() + "_" + caption.toLowerCase() + 
					".md.html#" + caption;
				Utils.loadUrl(url);
			}
		});
	}
}
