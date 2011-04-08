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

package com.gwtmobile.ui.client.widgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListItem extends FlowPanel{

	public enum ShowArrow { Default, True, False };
	ShowArrow _showArrow = ShowArrow.Default;
	boolean _disabled = false;
	
    public ListItem() { 
    }

	public void setShowArrow(boolean show) {
		_showArrow = show ? ShowArrow.True : ShowArrow.False;
		int last = getWidgetCount() - 1;
		if (last >=0) {
			Widget widget = getWidget(last);
			if (widget.getClass().toString().endsWith(".Chevron")) {
				if (!show) {
					remove(last);
				}
			}
			else {
				if (show) {
					add(new ListPanel.Chevron());
				}
			}
		}		
	}
	
	public void setDisabled(boolean disabled) {
		_disabled = disabled;
		if (_disabled) {
			addStyleName("Disabled");
		}
		else {
			removeStyleName("Disabled");
		}
	}
	
	public boolean getDisabled() {
		return _disabled;
	}
	
	void setShowArrowFromParent(boolean show) {
		// Parent can only override if it has not been set.
		if (_showArrow == ShowArrow.Default) {
			setShowArrow(show);
		}
	}

}
