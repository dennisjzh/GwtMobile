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

package com.gwtmobile.ui.client.event;

import com.google.gwt.core.client.JavaScriptObject;

public class Touch extends JavaScriptObject {

    protected Touch() {
    }

    public final native int getClientX() /*-{
        return this.clientX;
    }-*/;

    public final native int getClientY() /*-{
        return this.clientY;
    }-*/;

    public final native int pageX() /*-{
        return this.pageX;
    }-*/;

    public final native int pageY() /*-{
        return this.pageY;
    }-*/;

    public final native int screenX() /*-{
        return this.screenX;
    }-*/;

    public final native int screenY() /*-{
        return this.screenY;
    }-*/;
}
