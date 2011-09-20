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
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.ui.client.utils.Utils;
import com.gwtmobile.ui.client.widgets.WidgetBase;

//FIXME: extends PanelBase?
public abstract class Page extends WidgetBase {
	final static private String CONSUMED_TOKEN = "#?#";
	private Transition _transition;
	private static Transition _defaultTransition = Transition.SLIDE;
	protected String tokenStateInfo = CONSUMED_TOKEN;

	@Override
	protected void initWidget(Widget widget) {
		super.initWidget(widget);
		setStyleName("Page");
		// TODO: use permutation instead?
		if (Utils.isAndroid()) {
			addStyleName("Android");
		} else if (Utils.isIOS()) {
			addStyleName("iOS");
		} else {
			addStyleName("Desktop");
		}
	}

	/**
	 * Gives the page an opportunity to load state that was sent as part of the
	 * history token prior to display.
	 * 
	 * @see PageHistory#navigateTo(String, String)
	 * @see BrowserPageHistory#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
	 * 
	 * @param stateInfo
	 */
	protected void initNavigationalState(String stateInfo) {
	}

	@Override
	public void onTransitionEnd() {
		final Page to, from;
		final PageHistory pageHistory = PageHistory.Instance;
		if (pageHistory.from() == null
				|| pageHistory.from() != Page.this) { // goto
			Utils.Console("goto");
			from = pageHistory.current();
			to = this;
			pageHistory.add(to);
			// TODO: change to use scheduler deferred command.
			Timer timer = new Timer() {
				@Override
				public void run() {
					to.onNavigateTo();
					to.initNavigationIfRequired();
				}
			};
			timer.schedule(1);
		} else { // goback
			Utils.Console("goback");
			from = pageHistory.current();
			pageHistory.back();
			to = pageHistory.current();
			Timer timer = new Timer() {
				@Override
				public void run() {
					to.onNavigateBack(from,
							pageHistory.getReturnValue());
					to.initNavigationIfRequired();
				}
			};
			timer.schedule(1);
		}
	}

	protected void initNavigationIfRequired() {
		if (!CONSUMED_TOKEN.equals(tokenStateInfo)) {
			initNavigationalState(tokenStateInfo);
			tokenStateInfo = CONSUMED_TOKEN;
		}
	}

	protected void onNavigateTo() {
	}

	protected void onNavigateBack(Page from, Object object) {
	}

	public void goTo(final Page toPage, final Transition transition) {
		Element focus = Utils.getActiveElement();
		focus.blur();
		final Page fromPage = this;
		toPage.setTransition(transition);
		if (transition != null) {
			transition.start(fromPage, toPage, RootLayoutPanel.get(), false);
		} else {
			Transition.start(fromPage, toPage, RootLayoutPanel.get());
		}
	}

	public void goBack(Object returnValue) {
		PageHistory.Instance.goBack(this, returnValue);
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
		PageHistory.Instance.add(mainPage);
	}

	public static void setDefaultTransition(Transition transition) {
		_defaultTransition = transition;
	}

	public void goTo(final Page toPage) {
		goTo(toPage, _defaultTransition);
	}

	@Override
	public Widget getWidget() { // make getWidget() public
		return super.getWidget();
	}

	private static void setPageResolution() {
		double ratio = getDevicePixelRatio();
		if (ratio == 2) { // iphone 4. screen size on iphone does not change
							// despite the dp ratio.
			Document.get().getDocumentElement().setClassName("HVGA");
		} else if (ratio == 1.5) {
			Document.get().getDocumentElement().setClassName("WVGA");
		} else if (ratio == 0.75) {
			Document.get().getDocumentElement().setClassName("QVGA");
		} else {
			Document.get().getDocumentElement().setClassName("HVGA");
		}
	}

	public static native double getDevicePixelRatio() /*-{
		return $wnd.devicePixelRatio;
	}-*/;

}
