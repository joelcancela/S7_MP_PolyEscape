package polytech.teamf.plugins;

import polytech.teamf.events.IEvent;
import polytech.teamf.events.IEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class Plugin implements IPlugin, IEventListener {

    /**
     * Nested Plugin List
     */
    private List<Plugin> plugins = new ArrayList<>();

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

    @Override
    public final void addPlugin(Plugin p) {
        this.plugins.add(p);
    }

    @Override
    public final void sendEvent(IEvent e) {

        // Fire event
        e.fire();

        // Notify nested plugins
        for (Plugin p : this.plugins) {
            p.sendEvent(e);
        }
    }
}
