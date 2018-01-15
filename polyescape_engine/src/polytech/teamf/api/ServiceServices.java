package polytech.teamf.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/services")
public class ServiceServices {

    /**
     * @api {get} /services/{service} Doc here
     * @apiName Service
     * @apiGroup Services
     * @apiVersion 0.1.0
     *
     * @apiSuccess (200) {String} service Doc here
     *
     * @apiSuccessExample Example data on success
     * {
     *     "example": "example"
     * }
     *
     */

    /**
     *
     * @param service
     * @return
     */
    @GET
    @Path("{service}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getService(@PathParam("service") String service) {
        return "todo";
    }

}
