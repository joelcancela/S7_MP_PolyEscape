package polytech.teamf.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/service")
public class ServiceServices {

    @POST
    @Path("/{service}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPostService(@PathParam("service") String serviceName) { // should be another parameter here for the post
        if (serviceName.isEmpty()) { // rather test if the service name doesn't exist
            return Response.status(400).build();
        } else {

            if (serviceName.equals("EmailSpyService")) {

                
            }

            return Response.ok().build();
        }
    }

    // TODO : Implement GET if required

    /*
    @GET
    @Path("/{service}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getGetService(@PathParam("service") String serviceName) {
        if (serviceName.isEmpty()) { // rather test if the service name doesn't exist
            return Response.status(400).build();
        } else {
            return Response.ok().build();
        }
    }
    */
}
