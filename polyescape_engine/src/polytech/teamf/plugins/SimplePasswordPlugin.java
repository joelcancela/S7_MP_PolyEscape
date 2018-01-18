package polytech.teamf.plugins;

import polytech.teamf.events.*;

import java.util.Map;

public class SimplePasswordPlugin extends Plugin {

    /**
     * The original plain text
     * Is used by the validation process
     */
    private String plain_text = "";

    /**
     * Default constructor
     * Used by the API Reflection Engine
     */
    public SimplePasswordPlugin() {
        this("", "");
    }

    /**
     * Initializes the plugin
     *
     * @param description The plugin description
     * @param plain_text  The plain text to discover
     */
    public SimplePasswordPlugin(String description, String plain_text) {

        super("Epreuve mot de passe simple", description);

        // ARGS
        super.getArgs().put("plain_text", String.class.getSimpleName());

        // SCHEMA
        super.getSchema().put("attempt", String.class.getSimpleName());

        // MODEL
        this.plain_text = plain_text;
    }

    @Override
    public Event execute(Map<String, Object> args) throws Exception {

        String response = (String) args.get("attempt");

        if (this.plain_text.equals(response)) {
            return new GoodResponseEvent();
        }
        return new BadResponseEvent();
    }

    @Override
    public void on(BadResponseEvent e) {

    }

    @Override
    public void on(GoodResponseEvent e) {

    }

    @Override
    public void on(StartEvent e) {

    }

    @Override
    public void on(EndEvent e) {

    }
}



