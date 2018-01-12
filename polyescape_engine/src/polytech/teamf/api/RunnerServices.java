package polytech.teamf.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/runners")
public class RunnerServices {

    @PUT
    @Path("/instantiate")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response instantiateRunner(String config) {
        ServiceManager.getRunnerInstance(config);
        String result = "Runner initialized";
        return Response.status(201).entity(result).build();
    }

}
