package com.musemodeling.mogine.sysml.modelserver;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfcloud.modelserver.emf.common.RecordingModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.common.watchers.ModelWatchersManager;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.emf.configuration.ServerConfiguration;
import org.eclipse.emfcloud.modelserver.emf.util.JsonPatchHelper;
import org.eclipse.emfcloud.modelserver.integration.SemanticFileExtension;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationFileExtension;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SysMLModelResourceManager extends RecordingModelResourceManager {

	@Inject
	@SemanticFileExtension
	protected String semanticFileExtension;
	@Inject
	@NotationFileExtension
	protected String notationFileExtension;

	@Inject
	public SysMLModelResourceManager(final Set<EPackageConfiguration> configurations,
			final AdapterFactory adapterFactory, final ServerConfiguration serverConfiguration,
			final ModelWatchersManager watchersManager, final Provider<JsonPatchHelper> jsonPatchHelper) {

		super(configurations, adapterFactory, serverConfiguration, watchersManager, jsonPatchHelper);
	}

	@Override
	protected void loadSourceResources(final String directoryPath) {
		if (directoryPath == null || directoryPath.isEmpty()) {
			return;
		}
		File directory = new File(directoryPath);
		File[] list = directory.listFiles();
		Arrays.sort(list);
		for (File file : list) {
			if (isSourceDirectory(file)) {
				loadSourceResources(file.getAbsolutePath());
			} else if (file.isFile()) {
				URI absolutePath = URI.createFileURI(file.getAbsolutePath());
				if (SysMLResource.FILE_EXTENSION.equals(absolutePath.fileExtension())) {
					getSysmlResourceSet(absolutePath);
				}
				loadResource(absolutePath.toString());
			}
		}
	}

	/**
	 * Get the resource set that manages the given coffee semantic model resource,
	 * creating it if necessary.
	 *
	 * @param modelURI a coffee semantic model resource URI
	 * @return its resource set
	 */
	protected ResourceSet getSysmlResourceSet(final URI modelURI) {
		ResourceSet result = resourceSets.get(modelURI);
		if (result == null) {
			result = resourceSetFactory.createResourceSet(modelURI);
			resourceSets.put(modelURI, result);
		}
		return result;
	}

	@Override
	public ResourceSet getResourceSet(final String modeluri) {
		URI resourceURI = createURI(modeluri);
		if (notationFileExtension.equals(resourceURI.fileExtension())) {
			URI semanticUri = resourceURI.trimFileExtension().appendFileExtension(semanticFileExtension);
			return getSysmlResourceSet(semanticUri);
		}
		return resourceSets.get(resourceURI);
	}

	@Override
	public boolean save(final String modeluri) {
		boolean result = false;
		for (Resource resource : getResourceSet(modeluri).getResources()) {
			result = saveResource(resource) || result;
		}
		if (result) {
			getEditingDomain(getResourceSet(modeluri)).saveIsDone();
		}
		return result;
	}
}
