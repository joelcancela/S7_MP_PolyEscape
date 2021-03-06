package polytech.teamf.plugins;

import polytech.teamf.events.Event;
import polytech.teamf.events.IEvent;
import polytech.teamf.events.IEventListener;
import polytech.teamf.jarloader.JarLoader;
import polytech.teamf.services.Service;

import java.util.*;

public abstract class Plugin implements IPlugin, IEventListener {

    /**
     * Nested Plugin List
     */
    private List<Plugin> plugins = new ArrayList<>();

    /**
     * Plugin attributes
     */
    private Map<String, Object> attributes = new HashMap<>();

    public Map<String, Object> args = new LinkedHashMap<>();

    /**
     * Shared constructor with inherited plugins
     *
     * @param name        The plugin name
     * @param description The plugin description as a short text
     */
   public Plugin(String name, String description) {
        this.attributes.put("name", name);
        this.attributes.put("description", description);
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
            e.setSrouce(p);
            p.sendEvent(e);
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getDescription() {
        if (!this.attributes.containsKey("description")) {
            return null;
        }
        return this.attributes.get("description").toString();
    }

    @Override
    public String getName() {
        if (!this.attributes.containsKey("name")) {
            return null;
        }
        return this.attributes.get("name").toString();
    }

    /**
     * Update Plugin attribute given a key
     *
     * @param key   Attribute identifier
     * @param value Attribute value
     */
    public void putAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    /**
     * Service Invoker
     *
     * @param className the class name
     * @return the service to invoke
     */
    protected Service invokeService(String className) {

        try {
            Class t = JarLoader.getInstance().getServicesClasses().get(className);
            return (Service) t.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

}
