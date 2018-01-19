package polytech.teamf.plugins;

import polytech.teamf.events.BadResponseEvent;
import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.IEvent;
import polytech.teamf.services.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CaesarCipherPlugin extends Plugin {

    /**
     * The original plain text. Is used by the validation process.
     */
    private String plain_text = "";

    /**
     * Caesar Service Handle
     */
    private Service caesar;

    /**
     * Initializes the plugin
     *
     * @param description    The plugin description
     * @param plain_text     The plain text to discover
     * @param cipher_padding The cipher padding used by the algorithm to shift letters
     */
    public CaesarCipherPlugin(String description, String plain_text, int cipher_padding) {

        super(description, "Epreuve code Caesar");

        // SERVICES
        this.caesar = this.invokeService("CaesarCipherService");

        // INNER MODEL
        this.plain_text = plain_text.toUpperCase();

        // SHARED MODEL
        this.putAttribute("cyphertext ", toCaesar(plain_text.toUpperCase(), cipher_padding));
    }

    @Override
    public IEvent execute(Map<String, Object> args) throws Exception {

        if (!args.containsKey("attempt")) {
            throw new IllegalArgumentException("Bad response format");
        }

        String response = (String) args.get("attempt");
        response = response.toUpperCase();

        if (this.plain_text.equals(response)) {
            return new GoodResponseEvent(this);
        }
        return new BadResponseEvent(this);
    }

    private String toCaesar(String plain_text, int cipher_padding) {

        // TODO : implement this method stub
        return "";
        // JSONObject response = new JSONObject(ServiceManager.callCaesarCipherPlugin(plain_text, cipher_padding));
        // return response.get("result").toString();
    }

    @Override
    public void onBadResponseEvent() {

    }

    @Override
    public void onGoodResponseEvent() {

    }

//    public String CaesarCipher(Object[] args) {
//        WebTarget target = client.target(CAESAR_CIPHER_SERVICE_URI);
//        target = target.queryParam("message", (String) args[0]).queryParam("padding", (int) args[1]);
//        Invocation.Builder builder = target.request();
//        builder.accept(MediaType.APPLICATION_JSON_TYPE);
//        return builder.get(String.class);
//    }

    @Override
    public void onStartEvent() {

    }

    @Override
    public void onEndEvent() {

    }
}
