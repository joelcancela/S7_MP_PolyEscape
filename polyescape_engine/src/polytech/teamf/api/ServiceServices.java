package polytech.teamf.api;

import org.json.JSONArray;
import polytech.teamf.services.EmailSpyService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/service")
public class ServiceServices {

    @POST
    @Path("/{service}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPostService(@PathParam("service") String serviceName, String json) {
        if (serviceName.isEmpty()) { // rather test if the service name doesn't exist
            return Response.status(400).build();
        } else {

            /* STACK INPUT SERVICES HERE */

            if (serviceName.equals("EmailSpyService")) {
                Map<String, Object> callArgs = new HashMap<>();
                callArgs.put("msg", new JSONArray(json));
                new EmailSpyService().call(callArgs);
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
