package com.musemodeling.mogine.sysml.modelserver.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.AutomaticTask;
import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.ManualTask;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.edit.command.SetCommandContribution;
import org.eclipse.emfcloud.modelserver.edit.util.CommandUtil;
import org.omg.sysml.lang.sysml.SysMLPackage;
import org.omg.sysml.util.SysMLUtil;

import com.musemodeling.mogine.sysml.modelserver.SysMLResource;

public class SemanticCommandUtil {
	// Hide constructor for utility class
	private SemanticCommandUtil() {
	}

	// Expect a given EObject with an ID attribute
	public static String getSemanticElementId(final EObject element) {
		return EcoreUtil.getID(element);
	}

	public static String getCoffeeFileExtension() {
		return SysMLResource.FILE_EXTENSION;
	}

	public static Workflow getModel(final URI modelUri, final EditingDomain domain) {
		Resource semanticResource = domain.getResourceSet()
				.getResource(modelUri.trimFileExtension().appendFileExtension(getCoffeeFileExtension()), false);
		EObject semanticRoot = semanticResource.getContents().get(0);
		if (!(semanticRoot instanceof Machine)) {
			return null;
		}
		Machine machine = (Machine) semanticRoot;
		if (machine.getWorkflows().size() < 1) {
			return null;
		}
		// TODO We might want to hand in the index of the used workflow
		return machine.getWorkflows().get(0);
	}

	public static EObject getElement(final Workflow semanticModel, final String semanticElementId) {
		return semanticModel.eResource().getEObject(semanticElementId);
	}

	public static <C> C getElement(final Workflow semanticModel, final String semanticElementId,
			final java.lang.Class<C> clazz) {
		EObject element = getElement(semanticModel, semanticElementId);
		return clazz.cast(element);
	}

//	public static CCommand createSetTaskNameCommand(final Node taskToRename, final String ownerRefUri,
//			final String newName) {
//		return SetCommandContribution.clientCommand(CommandUtil.createProxy(getEClass(taskToRename), ownerRefUri),
//				SysMLPackage.Literals.TASK__NAME, newName);
//	}
//
//	public static CCommand createSetTaskDurationCommand(final Node task, final String ownerRefUri,
//			final int newDuration) {
//		return SetCommandContribution.clientCommand(CommandUtil.createProxy(getEClass(task), ownerRefUri),
//				SysMLPackage.Literals.TASK__DURATION, newDuration);
//	}
//
//	protected static EClass getEClass(final EObject element) {
//
//	}
}
