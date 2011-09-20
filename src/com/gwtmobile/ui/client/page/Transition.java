package com.gwtmobile.ui.client.page;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasWidgets;
import com.gwtmobile.ui.client.CSS.CSS;
import com.gwtmobile.ui.client.utils.Utils;
import com.gwtmobile.ui.client.widgets.WidgetBase;

public class Transition implements EventListener {
	
	String _transitionStyleName;
	WidgetBase _from, _to;
	boolean _reverse;
	HasWidgets _parent;
	
	public static Transition SLIDE = new Transition(CSS.T.slide());
	public static Transition SLIDEUP = new Transition(CSS.T.slideup());
	public static Transition SLIDEDOWN = new Transition(CSS.T.slidedown());
	public static Transition FADE = new Transition(CSS.T.fade());
	public static Transition POP = new Transition(CSS.T.pop());
	public static Transition FLIP = new FlipTransition();
	public static Transition SWAP = new SwapTransition();
//	public static Transition CUBE = new CubeTransition();
	
	public Transition(String transitionStyleName) {
		_transitionStyleName = transitionStyleName;
	}
	
	// No transition
	public static void start(final WidgetBase from, final WidgetBase to, final HasWidgets parent) {
		new Timer() {
			@Override
			public void run() {
				parent.remove(from);
				parent.add(to);
				to.onTransitionEnd();
			}
			
		}.schedule(1);
	}
	
	public void start(WidgetBase from, WidgetBase to, HasWidgets parent, boolean reverse) {
		_from = from;
		_to = to;
		_parent = parent;
		_reverse = reverse;
		prepare();
		start();
	}
	
	protected void prepare() {
		_from.addStyleName(_transitionStyleName + " " + CSS.T.out());
		_to.addStyleName(_transitionStyleName + " " + CSS.T.in());
		if (_reverse) {
			_from.addStyleName(CSS.T.reverse());
			_to.addStyleName(CSS.T.reverse());
		}
		_parent.add(_to);
	}

	protected void start() {
		registerTransitionEndEvent();
		new Timer() {
            @Override
            public void run() {
        		_from.addStyleName(CSS.T.start());
        		_to.addStyleName(CSS.T.start());
            }
		}.schedule(20);	//xxms instead of 1ms, to give iOS/Android enough time to set the starting state.

	}
	
	protected void removeTransitionStyles() {
		_from.removeStyleName(_transitionStyleName);
		_from.removeStyleName(CSS.T.start());
		_from.removeStyleName(CSS.T.out());
		_from.removeStyleName(CSS.T.reverse());
		_to.removeStyleName(_transitionStyleName);
		_to.removeStyleName(CSS.T.in());
		_to.removeStyleName(CSS.T.start());
		_to.removeStyleName(CSS.T.reverse());
	}
	
	@Override
	public void onBrowserEvent(Event e) {
		String type = e.getType();
		if (type.equals("webkitTransitionEnd") || type.equals("webkitAnimationEnd")) {
		    onTransitionEnd();
		}		
	}
	
	protected void onTransitionEnd() {
		if (_from != null && _to != null) {
			_parent.remove(_from);
			removeTransitionStyles();
			_to.onTransitionEnd();
			_from = null;
			_to = null;
			_parent = null;
		}
	}

	protected void registerTransitionEndEvent() {
		if (!_reverse) {
			Utils.addEventListenerOnce(_to.getElement(), "webkitTransitionEnd", false, this);
		}
		else {
			Utils.addEventListenerOnce(_from.getElement(), "webkitTransitionEnd", false, this);
		}
	}

	//Flip
	
	private static class FlipTransition extends Transition {

		private int _phase = 0;
		
		FlipTransition() {
			super(CSS.T.flip0());
		}
		
		@Override
		protected void registerTransitionEndEvent() {
			if (_phase == 0) {
				Utils.addEventListenerOnce(_from.getElement(), "webkitTransitionEnd", false, this);
			}
			else {
				Utils.addEventListenerOnce(_to.getElement(), "webkitTransitionEnd", false, this);
			}
		}
		
		@Override
		protected void onTransitionEnd() {
			removeTransitionStyles();
			if (_phase == 0) {
				_parent.remove(_from);
				_parent.add(_to);
				_phase++;
				_transitionStyleName = CSS.T.flip1();
				prepare();
				start();
			}
			else {
				_to.onTransitionEnd();
				_from = null;
				_to = null;
				_phase = 0;
				_transitionStyleName = CSS.T.flip0();
			}
		}
		
		@Override
		protected void prepare() {
			_from.addStyleName(_transitionStyleName + " " + CSS.T.out());
			_to.addStyleName(_transitionStyleName + " " + CSS.T.in());
			if (_reverse) {
				_from.addStyleName(CSS.T.reverse());
				_to.addStyleName(CSS.T.reverse());
			}
		}
		
	}

	// Swap
	
	private static class SwapTransition extends Transition {
		
		private int _phase = 0;

		SwapTransition() {
			super(CSS.T.swap0());
		}

		@Override
		protected void onTransitionEnd() {
			removeTransitionStyles();
			if (_phase == 0) {
				_phase++;
				_transitionStyleName = CSS.T.swap1();
				prepare();
				start();
			}
			else {
				_parent.remove(_from);
				_to.onTransitionEnd();
				_from = null;
				_to = null;
				_phase = 0;
				_transitionStyleName = CSS.T.swap0();
			}
		}

		@Override
		protected void prepare() {
			_from.addStyleName(_transitionStyleName + " " + CSS.T.out());
			_to.addStyleName(_transitionStyleName + " " + CSS.T.in());
			if (_reverse) {
				_from.addStyleName(CSS.T.reverse());
				_to.addStyleName(CSS.T.reverse());
			}
			if (_phase == 0) {
				_parent.add(_to);
			}
		}
	}
	
	// Cube
	
//	private static class CubeTransition extends Transition {
//
//		CubeTransition() {
//			super(CSS.T.cube());
//		}
//		
//		protected void registerTransitionEndEvent() {
//			if (!_reverse) {
//				Utils.addEventListenerOnce(_to.getElement(), "webkitAnimationEnd", false, this);
//			}
//			else {
//				Utils.addEventListenerOnce(_from.getElement(), "webkitAnimationEnd", false, this);
//			}
//		}		
//	}

}
