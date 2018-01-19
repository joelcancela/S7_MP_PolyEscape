package polytech.teamf.plugins;

import polytech.teamf.events.BadResponseEvent;
import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.IEvent;
import polytech.teamf.services.Service;

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

        //TODO
	    return null;
    }

    @Override
    public List<IPlugin> getPluginDependencies() {
        return null;
    }

    @Override
    public List<Service> getServiceDependencies() {
        return null;
    }

    @Override
    public void onBadResponseEvent() {

    }

    @Override
    public void onGoodResponseEvent() {

    }


    @Override
    public void onStartEvent() {

    }

    @Override
    public void onEndEvent() {

    }

}
