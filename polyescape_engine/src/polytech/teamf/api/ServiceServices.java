package polytech.teamf.api;

import polytech.teamf.services.GoogleSheetsService;
import polytech.teamf.services.PolyescapeEmailSpyService;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Path("/services")
public class ServiceServices {

    private static final String CAESAR_CIPHER_SERVICE_URI = "https://www.joelcancela.fr/services/fun/caesar_cipher.php";

    private Client client = ClientBuilder.newBuilder().newClient();

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
    @Path("/{id}/PolyescapeEmailSpy")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postTwitterDMService(String json, @PathParam("id") String id) {
        String[] args = new String[2];
        args[0] = json;
        args[1] = id;
        new PolyescapeEmailSpyService(args).execute();
    }

    /**
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

    /**
     * Cipher a text given a padding.
     *
     * @param args The text to cipher and the padding.
     * @return The service response.
     */
    public String CaesarCipher(Object[] args) {
        WebTarget target = client.target(CAESAR_CIPHER_SERVICE_URI);
        target = target.queryParam("message", (String) args[0]).queryParam("padding", args[1]);
        Invocation.Builder builder = target.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        return builder.get(String.class);
    }

}
