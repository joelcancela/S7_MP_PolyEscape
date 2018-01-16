package polytech.teamf.api;

import polytech.teamf.services.GoogleSheetsService;
import polytech.teamf.services.PolyescapeEmailSpyService;

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
     *
     * http://localhost:8080/services/GoogleSheetsService?gsheet=%22https://docs.google.com/spreadsheets/d/17d4SfrjbdyPq9x8HEjumkfYN8b_aBPwaIHVFJnNqbG0/gviz/tq?tqx=out:csv%22
     *
     * @apiSuccessExample Example data on success
     * {
     *     "example": "example"
     * }
     */

    /**
     * polyescape.olw5ew@zapiermail.com
     */
    @POST
    @Path("/PolyescapeEmailSpy")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postTwitterDMService(String json) {
        String[] args = new String[1];
        args[0] = json;
        new PolyescapeEmailSpyService(args).execute();
    }

    /**
     *
     * @param gsheet
     * @return
     */
    @GET
    @Path("/GoogleSheetsService")
    @Produces(MediaType.APPLICATION_JSON)
    public String getService(@QueryParam("gsheet") String gsheet) {
        String[] args = new String[1];
        args[0] = gsheet.replace("\"", "");
        return new GoogleSheetsService(args).execute().toString();
    }
}
