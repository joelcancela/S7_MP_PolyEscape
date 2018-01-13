package polytech.teamf.api;

import org.json.JSONObject;
import polytech.teamf.plugins.IPlugin;

import javax.ws.rs.*;
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
            return Response.status(400).entity(result).build();
        }
        ServiceManager.getRunnerInstance(config);
        return Response.status(201).entity(result).build();
    }

    /**
     * @api {POST} /runners/answer Give an answer to solve the current step
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
     * @apiError EmptyAnswer The answer was empty. <code>1</code> has to be given.
     */

    /**
     * Gives the step answer of a player to the runner and retrieve the result to send to the user.
     *
     * @param answer The player's answer.
     * @return The answer's result.
     * @throws Exception See {@link IPlugin#play(org.json.JSONObject)}
     */
    @POST
    @Path("/answer")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response answerStep(String answer) throws Exception {
        String result = "Answer correctly parsed";
        if (answer.isEmpty()) {
            ServiceManager.setLastResult(new JSONObject());
            result = "EmptyAnswer: Answer is empty!";
            return Response.status(400).entity(result).build();
        }
        ServiceManager.setLastResult(ServiceManager.getRunnerInstance(null).sendGuess_GetResponse(answer));
        return Response.status(200).entity(result).build();
    }

    /**
     * @api {get} /runners/result Retrieve the last player's answer result.
     * @apiName RunnerResult
     * @apiGroup Runners
     * @apiVersion 0.1.0
     *
     * @apiSuccess 200 {String} result The last result
     *
     * @apiSuccessExample Example data on success
     * {
     *     "success": "true"
     * }
     */

    /**
     * Get the last result produced by the last player's answer.
     *
     * @return A JSONObject containing the result of the last answer. "success" is "true" if the correct
     * answer was given, "success" is "false" otherwise.
     */
    @GET
    @Path("/result")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLastResult() {
        return ServiceManager.getLastResult().toString();
    }

}
