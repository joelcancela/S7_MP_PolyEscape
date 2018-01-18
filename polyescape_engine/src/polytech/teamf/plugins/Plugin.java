package polytech.teamf.plugins;

import polytech.teamf.events.Event;
import polytech.teamf.services.IService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Plugin implements IPlugin, IPluginEventListener {

    /**
     * Nested Plugin List
     */
    private List<IPlugin> plugins = new ArrayList<>();

    /**
     * Constructor Arguments
     */
    private Map<String, Object> args = new HashMap<>();

    /**
     * Form Schema
     */
    private Map<String, Object> schema = new HashMap<>();

    /**
     * Plugin Name
     */
    private String name = "";

    /**
     * Plugin Description Field
     */
    private String description = "";

    /**
     * Description Getter
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Name Getter
     *
     * @return name
     */
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

        // Shared Attributes
        this.name = name;
        this.description = description;

        // ARGS
        args.put("description", String.class.getSimpleName());
    }

    @Override
    public Map<String, Object> getArgs() {
        return this.args;
    }

    @Override
    public Map<String, Object> getSchema() {
        return this.schema;
    }

    @Override
    public void addPlugin(IPlugin p) {
        this.plugins.add(p);
    }

    @Override
    public void notifyEvent(Event e) {
        // Notify nested plugins
        for (IPlugin p : this.plugins) {
            p.notifyEvent(e);
        }
    }

    @Override
    public List<IPlugin> getPluginDependencies() {
        // TODO : must be implemented
        return null;
    }

    @Override
    public List<IService> getServiceDependencies() {
        // TODO : must be implemented
        return null;
    }
}
