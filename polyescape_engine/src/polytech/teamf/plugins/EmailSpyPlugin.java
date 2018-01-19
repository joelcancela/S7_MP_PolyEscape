package polytech.teamf.plugins;

import polytech.teamf.events.BadResponseEvent;
import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.IEvent;
import polytech.teamf.services.Service;

import java.util.List;
import java.util.Map;

public class EmailSpyPlugin extends Plugin {

    /**
     * The original plain text
     * Is used by the validation process
     */
    private String plain_text = "";

    /**
     * Initializes the plugin
     *
     * @param description The plugin description
     * @param plain_text  The plain text to discover
     */
    public EmailSpyPlugin(String description, String plain_text) {

        super("Epreuve mot de passe envoyé sur un email distant",
                description + "<br>Envoyez votre réponse à cet email : <a href=\"mailto:polyescape.olw5ew@zapiermail.com\">polyescape.olw5ew@zapiermail.com</a>"
        );

        // MODEL
        this.plain_text = plain_text;
    }

    @Override
    public IEvent execute(Map<String, Object> args) throws Exception {

        if (!args.containsKey("attempt")) {
            throw new IllegalArgumentException("Bad response format");
        }

        String response = (String) args.get("attempt");

        if (this.plain_text.equals(response)) {
            return new GoodResponseEvent(this);
        }
        return new BadResponseEvent(this);
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
