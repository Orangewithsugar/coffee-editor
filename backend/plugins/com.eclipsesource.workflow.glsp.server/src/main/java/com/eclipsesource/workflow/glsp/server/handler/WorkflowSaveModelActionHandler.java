/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package com.eclipsesource.workflow.glsp.server.handler;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.SaveModelAction;
import org.eclipse.glsp.api.action.kind.SetDirtyStateAction;
import org.eclipse.glsp.api.jsonrpc.GLSPServerException;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.server.actionhandler.BasicActionHandler;

import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelState;

public class WorkflowSaveModelActionHandler extends BasicActionHandler<SaveModelAction> {

	@Override
	protected List<Action> executeAction(SaveModelAction action, GraphicalModelState modelState) {
		try {
			if (action != null) {
				WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);
				String modelURI = modelAccess.getWorkflowFacade().getSemanticResource().getURI().toString();
				if (!modelAccess.getModelServerClient().save(modelURI).thenApply(res -> res.body()).get()) {
					throw new GLSPServerException("Could not execute save action: " + action.toString());
				}

				modelAccess.save();
			}
		} catch (ExecutionException | InterruptedException e) {
			throw new GLSPServerException("Could not execute save action: " + action.toString(), e);
		}
		return listOf(new SetDirtyStateAction(modelState.isDirty()));
	}

}
