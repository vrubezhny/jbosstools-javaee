/******************************************************************************* 
 * Copyright (c) 2012 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.tools.seam.internal.core.project.facet;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 * @author Alexey Kazakov
 */
public class Seam23ProjectCreator extends Seam2ProjectCreator {

	public Seam23ProjectCreator(IDataModel model, IProject seamWebProject) {
		super(model, seamWebProject);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.tools.seam.internal.core.project.facet.SeamProjectCreator#execute(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		super.execute(monitor);
		if(ISeamFacetDataModelProperties.DEPLOY_AS_EAR.equals(model.getProperty(ISeamFacetDataModelProperties.JBOSS_AS_DEPLOY_AS))) {
			IProject earProject = seamWebProject.getWorkspace().getRoot().getProject(earProjectName);
			File destFolder = new File(earProjectFolder, "resources");
			Seam23FacetInstallDelegate.copyDBDriverToProject(earProject, model, destFolder);
		}
	}
}