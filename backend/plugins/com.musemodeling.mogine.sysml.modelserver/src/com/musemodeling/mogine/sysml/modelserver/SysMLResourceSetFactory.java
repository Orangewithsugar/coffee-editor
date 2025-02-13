/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.musemodeling.mogine.sysml.modelserver;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfcloud.modelserver.emf.common.DefaultResourceSetFactory;
import org.eclipse.emfcloud.modelserver.integration.SemanticFileExtension;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationFileExtension;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationResource;
import org.omg.sysml.delegate.invocation.OperationInvocationDelegateFactory;
import org.omg.sysml.delegate.setting.DerivedPropertySettingDelegateFactory;

import com.google.inject.Inject;

public class SysMLResourceSetFactory extends DefaultResourceSetFactory {

	@Inject
	@SemanticFileExtension
	protected String semanticFileExtension;
	@Inject
	@NotationFileExtension
	protected String notationFileExtension;

	@Override
	public ResourceSet createResourceSet(final URI modelURI) {
		ResourceSet result = super.createResourceSet(modelURI);
		EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.
				put("http://www.omg.org/spec/SysML", new DerivedPropertySettingDelegateFactory());

		EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.
				put("http://www.omg.org/spec/SysML", new OperationInvocationDelegateFactory());
		result.getResourceFactoryRegistry().getExtensionToFactoryMap().put(semanticFileExtension,
				SysMLResource.FACTORY);
		result.getResourceFactoryRegistry().getExtensionToFactoryMap().put(notationFileExtension,
				NotationResource.FACTORY);

		return result;
	}
}
