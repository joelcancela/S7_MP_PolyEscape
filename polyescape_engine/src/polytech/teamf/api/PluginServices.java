package polytech.teamf.api;

import polytech.teamf.jarloader.JarLoader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Path("/plugins")
public class PluginServices {

	/**
	 * @api {get} /plugins/list The list of available plugins
	 * @apiName PluginsList
	 * @apiGroup Plugins
	 * @apiVersion 0.1.0
	 *
	 * @apiSuccess (200) {String} plugins The plugins list
	 *
	 * @apiSuccessExample Example data on success
	 * [{
	 *     "type": "polytech.teamf.plugins.CaesarCipherPlugin",
	 *     "args":
	 *     [
	 *     "description",
	 *     "plain_text",
	 *     "cipher_padding"
	 *     ]
	 * }]
	 */

	/**
	 * Get all the classes which inherits the Plugin class and return their full name
	 * and the list of args needed to use them.
	 *
	 * @return A string obtained from a JSONArray filled with the full class names.
	 */
	@GET
	@Path("/list")
	public String getAllStepsType() {
        JarLoader.getInstance();
		return null;
	}

}