package at.ac.uibk.sepm.pixplorer;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/pixplorer")
public class PixplorerApplication extends ResourceConfig {

	public PixplorerApplication() {
		packages("at.ac.uibk.sepm.pixplorer.rest");
	}
}
