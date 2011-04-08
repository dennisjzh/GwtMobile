/*
 * Copyright 2010 Zhihua (Dennis) Jiang
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

package com.gwtmobile.persistence.rebind;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JMethod;

public class GWTPersistenceGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			final String typeName) throws UnableToCompleteException {

		final GenUtils utils = new GenUtils(logger, context);
		final String requestedClassName = utils.getClassName(typeName);
		final String generatedClassName = requestedClassName + "EntityImpl";
		final List<JMethod> getters = new ArrayList<JMethod>();
		final List<JMethod> hasManyRels = new ArrayList<JMethod>();
		final List<JMethod> revHasManyRels = new ArrayList<JMethod>();
		
		utils.inspectType(typeName, getters, hasManyRels, revHasManyRels);
		
		return utils.generateClass(typeName, "EntityImpl",
				new EntityGenerator(utils, requestedClassName, generatedClassName, getters, hasManyRels, revHasManyRels));
	}
}
