package polytech.teamf.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/runners")
public class RunnerServices {

    /**
     * @api {put} /runners/instantiate Instantiate the runner
     * @apiName InstantiateRunner
     * @apiGroup Runners
     * @apiVersion 0.1.0
     *
     * @apiParam {String} config The plugins configuration
     *
     * @apiError EmptyConfiguration The configuration was empty. Minimum of <code>1</code> plugin configuration is required.
     */

    /**
     * Instantiate the runner
     *
     * @param config The plugins configuration
     * @return HTTP response. 404 if no plugins configuration is given, 201 otherwise.
     */
    @PUT
    @Path("/instantiate")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response instantiateRunner(String config) {
        String result = "Runner initialized";
        if (config.isEmpty()) {
            result = "EmptyConfiguration: Configuration is empty!";
            return Response.status(404).entity(result).build();
        }
        ServiceManager.getRunnerInstance(config);
        return Response.status(201).entity(result).build();
    }

}
