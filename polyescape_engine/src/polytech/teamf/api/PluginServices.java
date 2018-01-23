package polytech.teamf.api;

import polytech.teamf.jarloader.JarLoader;
import polytech.teamf.plugins.MetaPlugin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
     * [
     *   {
     *     "name": "CaesarCipherPlugin",
     *     "serviceDependencies": [
     *     "polytech.teamf.services.CaesarCipherService"
     *     ],
     *     "pluginDependencies": [],
     *     "args": {
     *       "plain_text": "java.lang.String",
     *       "description": "java.lang.String",
     *       "cipher_padding": "java.lang.Integer"
     *     },
     *     "schema": {
     *       "attempt": "java.lang.String"
     *     }
     *   }
     * ]
     */

    /**
     * Get all the classes which inherits the Plugin class and return their full name
     * and the list of args needed to use them as well as others useful information.
     *
     * @return a response object whose entity is a list of {@link MetaPlugin}
     */
    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllStepsType() {
        return Response.ok().entity(new GenericEntity<List<MetaPlugin>>(JarLoader.getInstance().getMetaPlugins()) {
        }).build();
    }

}