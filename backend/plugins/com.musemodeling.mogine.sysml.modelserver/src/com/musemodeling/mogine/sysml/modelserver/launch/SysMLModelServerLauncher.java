package com.musemodeling.mogine.sysml.modelserver.launch;

import java.io.IOException;

import org.eclipse.emfcloud.modelserver.emf.launch.CLIBasedModelServerLauncher;
import org.eclipse.emfcloud.modelserver.emf.launch.CLIParser;
import org.eclipse.emfcloud.modelserver.emf.launch.ModelServerLauncher;

import com.musemodeling.mogine.sysml.modelserver.SysMLModelServerModule;

@SuppressWarnings("UncommentedMain")
public class SysMLModelServerLauncher {
	private SysMLModelServerLauncher() {
	}

//	private static String EXECUTABLE_NAME = "org.eclipse.emfcloud.coffee.modelserver-0.1.0-standalone.jar";
	private static String EXECUTABLE_NAME = "com.musemodeling.mogine.sysml.modelserver-0.1.0-standalone.jar";

	/**
	 * Launch Coffee Model Server.
	 *
	 * @param args arguments
	 */
	public static void main(final String[] args) throws IOException {
		final ModelServerLauncher launcher = new CLIBasedModelServerLauncher(createCLIParser(args),
				new SysMLModelServerModule());
		launcher.run();
	}

	protected static CLIParser createCLIParser(final String[] args) {
		CLIParser parser = new CLIParser(args, CLIParser.getDefaultCLIOptions(), EXECUTABLE_NAME, 8081);
		return parser;
	}

}
