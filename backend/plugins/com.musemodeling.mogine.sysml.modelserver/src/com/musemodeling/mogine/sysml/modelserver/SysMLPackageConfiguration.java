package com.musemodeling.mogine.sysml.modelserver;

import java.util.Collection;

import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV2;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.omg.sysml.lang.sysml.SysMLPackage;

import com.google.common.collect.Lists;

public class SysMLPackageConfiguration implements EPackageConfiguration {
	@Override
	public String getId() {
		return SysMLPackage.eINSTANCE.getNsURI();
	}

	@Override
	public Collection<String> getFileExtensions() {
		return Lists.newArrayList(SysMLResource.FILE_EXTENSION, ModelServerPathParametersV2.FORMAT_JSON);
	}

	@Override
	public void registerEPackage() {
		SysMLPackage.eINSTANCE.eClass();
	}
}
