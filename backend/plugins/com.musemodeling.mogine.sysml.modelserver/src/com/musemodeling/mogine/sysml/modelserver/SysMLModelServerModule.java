package com.musemodeling.mogine.sysml.modelserver;

import org.eclipse.emfcloud.modelserver.common.utils.MapBinding;
import org.eclipse.emfcloud.modelserver.common.utils.MultiBinding;
import org.eclipse.emfcloud.modelserver.edit.CommandContribution;
import org.eclipse.emfcloud.modelserver.emf.common.ModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.common.ResourceSetFactory;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.notation.integration.EMSNotationModelServerModule;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationResource;

public class SysMLModelServerModule extends EMSNotationModelServerModule {
	@Override
	   protected Class<? extends ModelResourceManager> bindModelResourceManager() {
	      return SysMLModelResourceManager.class;
	   }

	   @Override
	   protected void configureEPackages(final MultiBinding<EPackageConfiguration> binding) {
	      super.configureEPackages(binding);
	      binding.add(SysMLPackageConfiguration.class);
	   }

	   @Override
	   protected Class<? extends ResourceSetFactory> bindResourceSetFactory() {
	      return SysMLResourceSetFactory.class;
	   }

	   @Override
	   protected void configureCommandCodecs(final MapBinding<String, CommandContribution> binding) {
	      super.configureCommandCodecs(binding);

//	      // Nodes
//	      binding.put(AddManualTaskCommandContribution.TYPE, AddManualTaskCommandContribution.class);
//	      binding.put(AddAutomatedTaskCommandContribution.TYPE, AddAutomatedTaskCommandContribution.class);
//	      binding.put(AddDecisionNodeCommandContribution.TYPE, AddDecisionNodeCommandContribution.class);
//	      binding.put(AddMergeNodeCommandContribution.TYPE, AddMergeNodeCommandContribution.class);
//	      binding.put(RemoveNodeCommandContribution.TYPE, RemoveNodeCommandContribution.class);
//
//	      // Flows (Edges)
//	      binding.put(AddFlowCommandContribution.TYPE, AddFlowCommandContribution.class);
//	      binding.put(AddWeightedFlowCommandContribution.TYPE, AddWeightedFlowCommandContribution.class);
//	      binding.put(RemoveFlowCommandContribution.TYPE, RemoveFlowCommandContribution.class);
//	      binding.put(SetFlowSourceCommandContribution.TYPE, SetFlowSourceCommandContribution.class);
//	      binding.put(SetFlowTargetCommandContribution.TYPE, SetFlowTargetCommandContribution.class);
	   }

	   @Override
	   protected String getSemanticFileExtension() { return SysMLResource.FILE_EXTENSION; }

	   @Override
	   protected String getNotationFileExtension() { return NotationResource.FILE_EXTENSION; }

}
