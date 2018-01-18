package polytech.teamf.plugins;

import polytech.teamf.events.Event;
import polytech.teamf.services.IService;

import java.util.List;
import java.util.Map;

public interface IPlugin {

    /**
     * Plugin constructor arguments
     */
    Map<String, Object> getArgs();

    /**
     * Plugin input schema
     */
    Map<String, Object> getSchema();

    /**
     * Reads the input data submitted from an external entity to this plugin
     * Outputs the response, given the plugin initialization and the user input
     *
     */
    Event execute(Map<String, Object> args) throws Exception;

    /**
     * Add a new nested plugin
     */
    void addPlugin(IPlugin p);

    /**
     * Notify this plugin, as well as the nested plugins.
     */
    void notifyEvent(Event e);

    /**
     * Returns the list of required plugins
     */
    List<IPlugin> getPluginDependencies();

    /**
     * Returns the list of required services
     */
    List<IService> getServiceDependencies();
}
