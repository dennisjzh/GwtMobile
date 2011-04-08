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

package com.gwtmobile.ui.kitchensink.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.widgets.HeaderPanel;
import com.gwtmobile.ui.client.widgets.SlidePanel;
import com.gwtmobile.ui.client.widgets.Slide;
import com.gwtmobile.ui.client.widgets.SlidePanel.SlideProvider;

public class SlidePanelPage extends Page implements SlideProvider{

	@UiField HeaderPanel header;
	@UiField SlidePanel slider;
	
	private static SlidePanelPageUiBinder uiBinder = GWT
			.create(SlidePanelPageUiBinder.class);

	interface SlidePanelPageUiBinder extends UiBinder<Widget, SlidePanelPage> {
	}

	public SlidePanelPage() {
		initWidget(uiBinder.createAndBindUi(this));		
		
		slider.setSlideCount(10);
		slider.setSlideProvider(this);

		header.setLeftButtonClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				if (slider.getCurrentSlideIndex() > 0) {
					slider.previous();
				}
				else {
					goBack(null);
				}
			}
		});

		header.setRightButtonClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				slider.next();
			}
		});
	}

	@Override
	public Slide loadSlide(int index) {
		if (index < 2) {
			return null;
		}
		Slide slide = new Slide();
		slide.addStyleName("Slide-Content");
		slide.add(new HTML("Slide Me!"));
		slide.add(new HTML("Dynamic Slide " + index));
		return slide;		
	}

}
