/*
 * Copyright (c) 2011 Zhihua (Dennis) Jiang
 * Copyright (c) 2011 Giuseppe Verduci
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

package com.gwtmobile.phonegap.client.plugins;

public class BarcodeScanner
{

    public interface Callback
    {
        void onError(String error);

        void onSuccess(String result);
    }

    public static native void scan(com.gwtmobile.phonegap.client.plugins.BarcodeScanner.Callback callback)
    /*-{
        $wnd.plugins.barcodeScanner.scan( null, function(result) {
            callback.@com.gwtmobile.phonegap.client.plugins.BarcodeScanner.Callback::onSuccess(Ljava/lang/String;)(result);
        }, function(error) {
            callback.@com.gwtmobile.phonegap.client.plugins.BarcodeScanner.Callback::onError(Ljava/lang/String;)(error);
        }, {yesString: "Install"});
     }-*/;

}