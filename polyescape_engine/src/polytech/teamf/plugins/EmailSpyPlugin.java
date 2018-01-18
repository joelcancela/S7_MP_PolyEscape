package polytech.teamf.plugins;

import polytech.teamf.events.IEvent;
import polytech.teamf.services.IService;

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

        super("Epreuve mot de passe simple", description);

        // MODEL
        this.plain_text = plain_text;
    }

    @Override
    public IEvent execute(Map<String, Object> args) throws Exception {
        return null;
    }

    @Override
    public List<IPlugin> getPluginDependencies() {
        return null;
    }

    @Override
    public List<IService> getServiceDependencies() {
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
