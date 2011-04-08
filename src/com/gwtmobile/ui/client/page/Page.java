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

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.utils.Utils;
import com.gwtmobile.ui.client.widgets.WidgetBase;

public abstract class Page extends WidgetBase {

	private Transition _transition;
	private static Transition _defaultTransition = Transition.SLIDE;

	@Override
	protected void initWidget(Widget widget) {
		super.initWidget(widget);    	
		setStyleName("Page");    
		//TODO: use permutation instead?
		if (Utils.isAndroid()) {
			addStyleName("Android");
		}
		else if (Utils.isIOS()) {
			addStyleName("iOS");
		}
		else {
			addStyleName("Desktop");
		}
	}

	@Override
	public void onTransitionEnd() {
		final Page to, from;
		if (PageHistory.from() == null || PageHistory.from() != Page.this) {  //goto
			Utils.Console("goto");
			from = PageHistory.current();
			to = this;
			PageHistory.add(to);
			//TODO: change to use scheduler deferred command.
			Timer timer = new Timer() {                
				@Override
				public void run() {
					to.onNavigateTo();
				}
			};
			timer.schedule(1);
		}
		else {             //goback
			Utils.Console("goback");
			from = PageHistory.current();
			PageHistory.back();
			to = PageHistory.current();
			Timer timer = new Timer() {                
				@Override
				public void run() {
					to.onNavigateBack(from, PageHistory.getReturnValue());
				}
			};
			timer.schedule(1);
		}       
	}

	protected void onNavigateTo() {
	}

	protected void onNavigateBack(Page from, Object object) {
	}

	public void goTo(final Page toPage, final Transition transition) {
		final Page fromPage = this;
		toPage.setTransition(transition);
		if (transition != null) {
			transition.start(fromPage, toPage, RootLayoutPanel.get(), false);
		}
		else {
			Transition.start(fromPage, toPage, RootLayoutPanel.get());
		}
	}

	public void goBack(Object returnValue) {
		final Page fromPage = this;
		PageHistory.setReturnValue(returnValue);
		final Page toPage = PageHistory.from();
		if (toPage == null) {
			// exit app here.
			return;
		}
		final Transition transition = fromPage.getTransition();
		if (transition != null) {
			transition.start(fromPage, toPage, RootLayoutPanel.get(), true);
		}
		else {
			Transition.start(fromPage, toPage, RootLayoutPanel.get());
		}
	}

	void setTransition(Transition transition) {
		_transition = transition;
	}

	Transition getTransition() {
		return _transition;
	}

	public static void load(Page mainPage) {
		setPageResolution();
		RootLayoutPanel.get().add(mainPage);
		PageHistory.add(mainPage);
	}

	public static void setDefaultTransition(Transition transition) {
		_defaultTransition = transition;
	}

	public void goTo(final Page toPage) {
		goTo(toPage, _defaultTransition);
	}

	@Override
	public Widget getWidget() {	//make getWidget() public
		return super.getWidget();
	}
	
	private static void setPageResolution() {
		int ratio = getDevicePixelRatio();
		if (ratio == 2) {	//iphone 4. screen size on iphone does not change despite the dp ratio. 
			Document.get().getDocumentElement().setClassName("HVGA");
		}
		else if (ratio == 1.5) {
			Document.get().getDocumentElement().setClassName("WVGA");
		}
		else if (ratio == 0.75) {
			Document.get().getDocumentElement().setClassName("QVGA");
		}
		else {
			Document.get().getDocumentElement().setClassName("HVGA");
		}
	}

	public static native int getDevicePixelRatio() /*-{
		return $wnd.devicePixelRatio;
	}-*/;

}
