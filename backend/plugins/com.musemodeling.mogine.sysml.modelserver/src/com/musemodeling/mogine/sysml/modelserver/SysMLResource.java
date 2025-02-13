package com.musemodeling.mogine.sysml.modelserver;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;


public interface SysMLResource extends XMIResource {

   Resource.Factory FACTORY = new SysMLResourceFactoryImpl();

   String FILE_EXTENSION = "sysmlx";

}
