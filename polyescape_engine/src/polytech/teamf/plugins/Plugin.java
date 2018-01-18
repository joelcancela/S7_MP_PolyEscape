package polytech.teamf.plugins;

import polytech.teamf.events.IEvent;
import polytech.teamf.events.IEventListener;
import polytech.teamf.services.IService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Plugin implements IPlugin, IEventListener {

    /**
     * Nested Plugin List
     */
    private List<IPlugin> plugins = new ArrayList<>();

    /**
     * Plugin Name
     */
    private String name = "";

    /**
     * Plugin Description Field
     */
    private String description = "";

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Shared constructor with inherited plugins
     *
     * @param name        The plugin name
     * @param description The plugin description as a short text
     */
    Plugin(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Add new nested plugin
     */
    public void addPlugin(Plugin p) {
        this.plugins.add(p);
    }

    @Override
    public void sendEvent(IEvent e) {

        // Fire event
        e.fire();

        // Notify nested plugins
        for (IPlugin p : this.plugins) {
            p.sendEvent(e);
        }
    }
}
