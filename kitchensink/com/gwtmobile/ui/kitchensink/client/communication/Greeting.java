/*
 * Copyright (c) 2011 Zhihua (Dennis) Jiang
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

package com.gwtmobile.ui.kitchensink.client.communication;

import java.io.Serializable;

public class Greeting implements Serializable {

	private static final long serialVersionUID = 1L;
	private String _from;
	private String _message;
	
	public void setFrom(String from) {
		this._from = from;
	}

	public String getFrom() {
		return _from;
	}

	public void setMessage(String message) {
		this._message = message;
	}

	public String getMessage() {
		return _message;
	}
	
}
