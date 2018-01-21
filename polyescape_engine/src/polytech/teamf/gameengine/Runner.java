package polytech.teamf.gameengine;

import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.IEvent;
import polytech.teamf.jarloader.JarLoader;
import polytech.teamf.plugins.MetaPlugin;
import polytech.teamf.plugins.Plugin;
import polytech.teamf.resources.PluginDescriptionResource;
import polytech.teamf.resources.PluginInstantiationResource;
import polytech.teamf.resources.RunnerInstanceResource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Runner {

    /**
     * The list of plugins (ordered).
     */
    private List<Plugin> plugins = new LinkedList<>();

    /**
     * Handle on the current plugin.
     */
    private Plugin currentPlugin;

    /**
     * The current plugin state.
     */
    private boolean currentPluginStatus = false;

    /**
     * The current plugin number in {@link #plugins}.
     */
    private int it = 0;

    /**
     * Runner that parse the json & instantiate plugins.
     *
     * @param config the plugins configuration
     */
    public Runner(List<PluginInstantiationResource> config) {
        for (PluginInstantiationResource plugin : config) {
            instantiatePlugin(plugin);
        }
        currentPlugin = plugins.get(it);
    }

    public PluginDescriptionResource getCurrentPluginDescription() {
        Map<String, Object> attributes = currentPlugin.getAttributes();
        Map<String, Object> schema = new HashMap<>();
        for (MetaPlugin metaPlugin : JarLoader.getInstance().getMetaPlugins()) {
            if (metaPlugin.getName().equals(currentPlugin.getName())) {
                schema = metaPlugin.getSchema();
            }
        }
        return new PluginDescriptionResource(attributes, schema);
    }

    /**
     * Notify the plugin of an incoming message.
     * Notify the plugin plus its nested plugin of the event.
     *
     * @param args plugin arguments
     * @return an event if the plugin executed properly
     */
    public boolean sendMessage(Map<String, Object> args) {
        IEvent e = null;

        try {
            e = this.currentPlugin.execute(args);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (e instanceof GoodResponseEvent) {
            this.currentPluginStatus = true;
        }

        this.sendEvent(e);

        return currentPluginStatus;
    }

    /**
     * Notify the plugin plus its nested plugin of the event.
     *
     * @return
     */
    public void sendEvent(IEvent e) {
        this.currentPlugin.sendEvent(e);
    }

    /**
     * The current plugin becomes the next in the list if there is another plugin to play (next step in the game).
     *
     * @return A JSONObject containing the status of the game. The status will be "finish" in case there are no more
     * plugins to play or "ok" otherwise.
     */
    public RunnerInstanceResource nextPlugin() {
        if (currentPluginStatus) {
            this.currentPluginStatus = false; // Reset plugin state
            it++;

            if (it >= plugins.size()) {
                return new RunnerInstanceResource("finish");
            }

            currentPlugin = plugins.get(it);
            return new RunnerInstanceResource("ok");
        } else {
            return new RunnerInstanceResource("forbidden");
        }
    }

    private void instantiatePlugin(PluginInstantiationResource plugin) {
        String className = plugin.getName();
        JarLoader jarloader = JarLoader.getInstance();
        Class pluginClass = jarloader.getPluginClasses().get(className);
        List<MetaPlugin> pluginMeta = jarloader.getMetaPlugins();
        Class[] types = null;
        for (MetaPlugin metaPlugin : pluginMeta) {
            if (metaPlugin.getName().equals(className)) {
                types = metaPlugin.toClassArray();
            }
        }
        Collection<Object> objects = plugin.getArgs().values();
        Object[] values = objects.toArray(new Object[objects.size()]);
        Object p = null;
        try {
            Constructor ct = pluginClass.getConstructor(types);
            p = ct.newInstance(values);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        plugins.add((Plugin) p);
    }

    /**
     * Get the current played plugin.
     *
     * @return the current played plugin
     */
    public Plugin getPlugin() {
        return this.currentPlugin;
    }

    /**
     * Get the current played plugin status.
     *
     * @return true if the last answer was correct, false otherwise
     */
    public boolean getStatus() {
        return this.currentPluginStatus;
    }

}
