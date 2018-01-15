package polytech.teamf.api;

import org.json.JSONObject;
import polytech.teamf.plugins.IPlugin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
     * Instantiate a new runner instance
     *
     * @param config The plugins configuration
     * @return HTTP response. 404 if no plugins configuration is given, 201 otherwise.
     */
    @PUT
    @Path("/instantiate")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response instantiateRunner(String config) {
        String uuid = UUID.randomUUID().toString();
        if (config.isEmpty()) {
            return Response.status(400).entity("EmptyConfiguration: Configuration is empty!").build();
        }
        ServiceManager.createNewInstance(uuid, config);
        return Response.ok(new JSONObject().put("id", uuid).toString(), MediaType.APPLICATION_JSON).build();
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
     *     "attempt_text": "Put your answer here"
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
     * @throws Exception See {@link IPlugin#play(org.json.JSONObject)}
     */
    @POST
    @Path("/{id}/answer")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response answerStep(@PathParam("id") String id, String answer) throws Exception {
        if (answer.isEmpty()) {
            ServiceManager.setLastResult(new JSONObject());
            return Response.status(400).entity("EmptyAnswer: Answer is empty!").build();
        }
        ServiceManager.setLastResult(ServiceManager.runnersInstances.get(id).sendGuess_GetResponse(answer));
        return Response.ok(ServiceManager.getLastResult().toString(), MediaType.APPLICATION_JSON).build();
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
    @Produces(MediaType.APPLICATION_JSON)
    public String getLastResult(@PathParam("id") String id) {
        return ServiceManager.runnersInstances.get(id).nextPlugin().toString();
    }

}
