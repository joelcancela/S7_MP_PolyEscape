package polytech.teamf.plugins;

import polytech.teamf.events.IEvent;
import polytech.teamf.services.IService;

import java.util.List;
import java.util.Map;

public interface IPlugin {

    /**
     * Plugin Complete Name
     */
    String getName();

    /**
     * Plugin Description Field
     */
    String getDescription();

    /**
     * Reads the input data submitted from an external entity to this plugin
     * Outputs the response, given the plugin initialization and the user input
     */
    IEvent execute(Map<String, Object> args) throws Exception;

    /**
     * Notify the nested plugins
     */
    void sendEvent(IEvent e);

    /**
     * Returns the list of required plugins
     */
    List<IPlugin> getPluginDependencies();

    /**
     * Returns the list of required services
     */
    List<IService> getServiceDependencies();
}
