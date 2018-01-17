package polytech.teamf.plugins;

import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class CaesarCipherPlugin extends Plugin {

    /**
     * The ciphered text as returned by the private method toCaesar()
     */
    private String ciphered_text = "";

    /**
     * The original plain text. Is used by the validation process.
     */
    private String plain_text = "";

    /**
     * Default constructor
     * Used by the API Reflection Engine
     */
    public CaesarCipherPlugin() {
        this("", "", 0);
    }

    /**
     * Initializes the plugin
     *
     * @param description The plugin description
     * @param plain_text The plain text to discover
     * @param cipher_padding The cipher padding used by the algorithm to shift letters
     */
    public CaesarCipherPlugin(String description, String plain_text, int cipher_padding) {

        super(description, "Epreuve code Caesar");

        // ARGS
        super.getArgs().add("plain_text");
        super.getArgs().add("cipher_padding");
        // SCHEMA
        this.schema.put("attempt_text", "The user attempt");

        // FORM
        this.plain_text = plain_text.toUpperCase();
        this.ans_format = "text";

        this.ciphered_text = toCaesarFromService(plain_text, cipher_padding);
    }

    @Override
    public JSONObject play(JSONObject args) {

        JSONObject ret = new JSONObject();

        try {
            if (this.plain_text.equals(args.getString("attempt").toUpperCase())) {
                this.isValidatedState = true;
                ret.put(SUCCESS, "true");
                isSuccess = "true";

            }
            else {
                ret.put(SUCCESS, "false");
                isSuccess="false";
            }
        }
        catch (Exception e) {
            ret.put(SUCCESS, "false");
            isSuccess="false";

        }

        return ret;
    }

    public String toString() {
        return new JSONObject()
                .put("name", this.getName())
                .put("description", this.getDescription())
                .put("plain_text", this.plain_text)
                .put("ciphered_text", this.ciphered_text)
                .put("answer_format",this.getAns_format())
                .put("use_remote_service", false).toString();
    }

    private String toCaesarFromService(String plain_text, int cipher_padding) {
        Client client = ClientBuilder.newBuilder().newClient();
        WebTarget target = client.target("https://www.joelcancela.fr/services/fun/caesar_cipher.php");
        target = target.queryParam("message", plain_text).queryParam("padding", cipher_padding);
        Invocation.Builder builder = target.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        JSONObject response = new JSONObject(builder.get(String.class));
        return response.get("result").toString();
    }

    @Override
    public String getDescription(){
        return  super.getDescription() + " " + this.ciphered_text;
    }
}
