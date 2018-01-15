package polytech.teamf.api;

import org.json.JSONObject;
import polytech.teamf.services.GoogleSheetsService;
import polytech.teamf.services.TwitterDMService;

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
     *
     */
    @GET
    @Path("/TwitterDMService")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTwitterDMService(@QueryParam("crc_token") String crc_token) {
        return new TwitterDMService(new String[0]).crc_check(crc_token).toString();
    }

    /**
     *
     */
    @POST
    @Path("/TwitterDMService")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String postTwitterDMService(String json) {
        System.err.print("DBG  ::  " + json);
        return "WIP";
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
