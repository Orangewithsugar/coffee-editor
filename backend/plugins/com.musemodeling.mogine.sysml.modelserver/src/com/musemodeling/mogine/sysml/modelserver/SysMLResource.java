package com.musemodeling.mogine.sysml.modelserver;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emfcloud.coffee.util.CoffeeResourceFactoryImpl;

public interface SysMLResource extends XMIResource {

   Resource.Factory FACTORY = new CoffeeResourceFactoryImpl();

   String FILE_EXTENSION = "coffee";

}
