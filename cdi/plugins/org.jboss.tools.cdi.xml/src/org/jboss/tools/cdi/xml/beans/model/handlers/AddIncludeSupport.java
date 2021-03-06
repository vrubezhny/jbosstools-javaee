/*******************************************************************************
 * Copyright (c) 2012 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.cdi.xml.beans.model.handlers;

import java.util.Properties;

import org.jboss.tools.common.meta.XChild;
import org.jboss.tools.common.meta.action.impl.DefaultWizardDataValidator;
import org.jboss.tools.common.meta.action.impl.SpecialWizardSupport;
import org.jboss.tools.common.meta.action.impl.WizardDataValidator;
import org.jboss.tools.common.meta.action.impl.handlers.DefaultCreateHandler;
import org.jboss.tools.common.model.XModelException;
import org.jboss.tools.common.model.XModelObject;
import org.jboss.tools.common.model.util.FindObjectHelper;
import org.jboss.tools.common.model.util.XModelObjectLoaderUtil;

public class AddIncludeSupport extends SpecialWizardSupport {

    @Override
	public void action(String name) throws XModelException {
		if(FINISH.equals(name)) {
			execute();
			setFinished(true);
		} else if(CANCEL.equals(name)) {
			setFinished(true);
		} else if(HELP.equals(name)) {
			help();
		}
	}
	
	protected void execute() throws XModelException {
		Properties p0 = extractStepData(0);
		boolean include = "include".equals(p0.getProperty("kind"));
		String entity = getObjectEntity(include);
		
		XModelObject object = XModelObjectLoaderUtil.createValidObject(getTarget().getModel(), entity, p0);
	
		DefaultCreateHandler.addCreatedObject(getTarget(), object, FindObjectHelper.EVERY_WHERE);
	}

	String getObjectEntity(boolean include) {
		XChild[] cs = getTarget().getModelEntity().getChildren();
		String search = include ? "Include" : "Exclude";
		for (XChild c: cs) {
			String name = c.getName();
			if(name.indexOf(search) >= 0) {
				return name;
			}
		}
		return null;
	}

//	protected DefaultWizardDataValidator validator = new Validator();
//    
//	public WizardDataValidator getValidator(int step) {
//		validator.setSupport(this, step);
//		return validator;    	
//	}
//
//	class Validator extends DefaultWizardDataValidator {
//		public void validate(Properties data) {
//			boolean isRegEx = "true".equals(data.getProperty("is regular expression"));
//			String nameValue = data.getProperty("name/pattern");
//			String nameAttr = isRegEx ? "pattern" : "name";
//			data.setProperty(nameAttr, nameValue);
//			super.validate(data);			
//		}
//		
//	}
}
