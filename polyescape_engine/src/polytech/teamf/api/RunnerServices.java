package polytech.teamf.api;

import polytech.teamf.resources.PluginInstantiationResource;
import polytech.teamf.resources.PluginStatusResource;
import polytech.teamf.resources.RunnerInstanceResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/runners")
public class RunnerServices {

	/**
	 * @api {put} /runners/instantiate Instantiate a new unique runner instance
	 * @apiName InstantiateRunner
	 * @apiGroup Runners
	 * @apiVersion 0.1.0
	 *
	 * @apiParam {String} config The plugins configuration
	 * @apiParamExample {json} Request-Example:
	 * [{
	 *   "name": "CaesarCipherPlugin",
	 *   "args": {
	 *     "plain_text": "a",
	 *     "description": "d",
	 *     "cipher_padding": 7
	 *   }
	 * }]
	 *
	 * @apiError EmptyConfiguration The configuration was empty. Minimum of <code>1</code> plugin configuration is required.
	 *
	 * @apiSuccessExample Example data on success
	 * {
	 *   "id": "872ac411-39ec-4674-922e-3a51b7ba522d",
	 *   "status": null
	 * }
	 */

	/**
	 * Instantiate a new unique runner instance.
	 *
	 * @param config the plugins configuration
	 * @return a response object whose status will be 400 if no plugins configuration is given, 200 otherwise
	 */
	@PUT
	@Path("/instantiate")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response instantiateRunner(List<PluginInstantiationResource> config) {
		String uuid = UUID.randomUUID().toString();

		if (config.isEmpty()) {
			return Response.status(400).entity("EmptyConfiguration: Configuration is empty!").build();
		}

		InstanceHolder.getInstance().createNewInstance(uuid, config);
		RunnerInstanceResource runnerInstanceResource = new RunnerInstanceResource();
		runnerInstanceResource.setId(uuid);

		return Response.ok(runnerInstanceResource).build();
	}

	/**
	 * @api {post} /runners/{id}/answer Give an answer to solve the current step and retrieve the result on the runner with id {id}
	 * @apiName RunnerAnswer
	 * @apiGroup Runners
	 * @apiVersion 0.1.0
	 *
	 * @apiParam {String} answer The player's answer
	 *
	 * @apiParamExample {json} Request-Example:
	 * {
	 *     "attempt": "Put your answer here"
	 * }
	 *
	 * @apiError EmptyAnswer The answer was empty. Exactly <code>1</code> answer has to be given.
	 *
	 * @apiSuccessExample Example data on success
	 * {
	 *     "success": "true"
	 * }
	 */

	/**
	 * Gives the step answer of a player to the runner and retrieve the result to send to the user.
	 *
	 * @param answer the player's answer
	 * @param id     the runner unique id
	 * @return a response object whose status will be 400 if no answer is given, 200 otherwise
	 */
	@POST
	@Path("/{id}/answer")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response answerStep(@PathParam("id") String id, PluginStatusResource answer) throws Exception {
		if (answer.getAttempt() == null) {
			return Response.status(400).entity("EmptyAnswer: Answer is empty!").build();
		}
		boolean status = InstanceHolder.getInstance().getRunnerInstance(id).sendMessage(answer.toMap());
		return Response.ok(new PluginStatusResource(status)).build();
	}

	/**
	 * @api {get} /runners/{id}/status Retrieve the status for the next step of the game on the runner with id {id}
	 * @apiName RunnerStatus
	 * @apiGroup Runners
	 * @apiVersion 0.1.0
	 *
	 * @apiSuccess 200 {String} status The current runner status
	 *
	 * @apiSuccessExample Example data on success: (the status can be ok if a correct answer has been given and there's another step after, finish if a correct answer has been given and there's no more step or forbidden if no correct answer has been given (the player tries to skip the step))
	 *	{
	 *	"id": null,
	 *	"status": "ok/finish/forbidden"
	 *	}
	 */

	/**
	 * Informs the player if there's a next step or if it's end
	 *
	 * @param id the runner unique id
	 * @return a response object containing the status for the next plugins
	 */
	@GET
	@Path("/{id}/status")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getLastResult(@PathParam("id") String id) {
		return Response.ok(InstanceHolder.getInstance().getRunnerInstance(id).nextPlugin()).build();
	}


	/**
	 * @api {get} /runners/{id}/description Get the current played plugin description on the runner with id {id}
	 * @apiName RunnerDescription
	 * @apiGroup Runners
	 * @apiVersion 0.1.0
	 *
	 * @apiSuccess (200) {String} description The current played plugin description
	 *
	 * @apiSuccessExample Example data on success
	 * {
	 *      "attributes": {
	 *           "name": "CaesarCipherPlugin",
	 *           "description": "a Voici le code à décrypter: K"
	 *      },
	 *      "responseFormat": {
	 *          "attempt": "java.lang.String"
	 *      }
	 * }
	 */

	/**
	 * Get the current played plugin description.
	 *
	 * @return a response object containing the current played plugin description and how to answer
	 */
	@GET
	@Path("/{id}/description")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getPluginDescription(@PathParam("id") String id) {
		return Response.ok().entity(InstanceHolder.getInstance().getRunnerInstance(id).getCurrentPluginDescription()).build();
	}

    /**
     * @api {get} /runners/{id}/inputStatus Get the status for the current step on the runner with id {id}
     * @apiName RunnerInputStatus
     * @apiGroup Runners
     * @apiVersion 0.1.0
     *
     * @apiError EmptyAnswer The answer was empty. Exactly <code>1</code> answer has to be given.
     *
     * @apiSuccessExample Example data on success, this request is mostly used when the step is using a service that's calls our API. ("Input" services like mails)
     * {
     *     "success": "true"
     * }
     */

	/**
	 * Get the current played plugin status.
	 *
	 * @param id the runner unique id
	 * @return a response object containing the status of the current played plugin
	 */
	@GET
	@Path("/{id}/inputStatus")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getInputAnswerStatus(@PathParam("id") String id) {
		return Response.ok().entity(new PluginStatusResource(InstanceHolder.getInstance().getRunnerInstance(id).getStatus())).build();
	}

}
