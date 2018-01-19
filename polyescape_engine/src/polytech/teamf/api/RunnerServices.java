package polytech.teamf.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

@Path("/runners")
public class RunnerServices {

    /**
     * @api {put} /runners/instantiate Instantiate a new runner instance
     * @apiName InstantiateRunner
     * @apiGroup Runners
     * @apiVersion 0.1.0
     *
     * @apiParam {String} config The plugins configuration
     * @apiParamExample {json} Request-Example:
     * [{
     *     "type": "CaesarCipherPlugin",
     *     "description": "Put a description here",
     *     "plain_text": "Put the text to cipher here",
     *     "cipher_padding": "5"
     * }]
     *
     * @apiError EmptyConfiguration The configuration was empty. Minimum of <code>1</code> plugin configuration is required.
     */

    /**
     * Instantiate a new runner instance.
     *
     * @param  config the plugins configuration
     * @return HTTP response. 404 if no plugins configuration is given, 201 otherwise
     */
    @PUT
    @Path("/instantiate")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response instantiateRunner(Map<String,Object> config) {
        String uuid = UUID.randomUUID().toString();
        if (config.isEmpty()) {
            return Response.status(400).entity("EmptyConfiguration: Configuration is empty!").build();
        }
        InstanceHolder.createNewInstance(uuid, config);
        return Response.ok().entity(uuid).build();

    }

    /**
     * @api {post} /runners/{id}/answer Give an answer to solve the current step and retrieve the result on the runner with id {id}
     * @apiName RunnerAnswer
     * @apiGroup Runners
     * @apiVersion 0.1.0
     *
     * @apiParam {String} answer The player's answer
     *
     * @apiParamExample {json} Request-Example (Be careful, a request is plugin specific):
     * {
     *     "attempt": "Put your answer here"
     * }
     *
     * @apiError EmptyAnswer The answer was empty. <code>1</code> answer has to be given.
     *
     * @apiSuccessExample Example data on success
     * {
     *     "success": "true"
     * }
     */

    /**
     * Gives the step answer of a player to the runner and retrieve the result to send to the user.
     *
     * @param answer The player's answer.
     * @param id     The runner unique id.
     * @return The answer's result.
     */
    @POST
    @Path("/{id}/answer")
    public Response answerStep(@PathParam("id") String id, Map<String,Object> answer) throws Exception {
        if (answer.isEmpty()) {
            return Response.status(400).entity("EmptyAnswer: Answer is empty!").build();
        }
        return Response.ok(InstanceHolder.getInstance().getRunnerInstance(id).sendMessage(answer)).build();
    }

    /**
     * @api {get} /runners/{id}/status Retrieve the status for the next step of the game on the runner with id {id}
     * @apiName RunnerHasNext
     * @apiGroup Runners
     * @apiVersion 0.1.0
     *
     * @apiSuccess 200 {String} status The current runner status
     *
     * @apiSuccessExample Example data on success
     * {
     *     "status": "ok"
     * }
     */

    /**
     * Get the last result produced by the last player's answer.
     *
     * @param id The runner unique id.
     * @return A JSONObject containing the result of the last answer. "success" is "true" if the correct
     * answer was given, "success" is "false" otherwise.
     */
    @GET
    @Path("/{id}/status")
    public String getLastResult(@PathParam("id") String id) {
        return InstanceHolder.runnersInstances.get(id).nextPlugin().toString();
    }


    /**
     * @api {get} /plugins/{id}/description The current played plugin description on the runner with id {id}
     * @apiName PluginsDescription
     * @apiGroup Plugins
     * @apiVersion 0.1.0
     *
     * @apiSuccess (200) {String} description The plugin description
     *
     * @apiSuccessExample Example data on success
     * {
     *     "description": "Plugin description"
     * }
     */

    /**
     * Get the current played plugin description.
     *
     * @return A string obtained from a JSONObject filled with the current played plugin description.
     */
    @GET
    @Path("/{id}/description")
    public String getPluginDescription(@PathParam("id") String id) {
        //TODO
        return null;
    }

}
