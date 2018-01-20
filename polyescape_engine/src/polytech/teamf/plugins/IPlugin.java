package polytech.teamf.plugins;

import polytech.teamf.events.IEvent;

import java.util.Map;

public interface IPlugin {

    /**
     * Plugin Attributes.
     */
    Map<String, Object> getAttributes();

    /**
     * Plugin Description Field.
     */
    String getDescription();

    /**
     * Plugin Name Field.
     */
    String getName();

    /**
     * Reads the input data submitted from an external entity to this plugin.
     * Outputs the response, given the plugin initialization and the user input.
     */
    IEvent execute(Map<String, Object> args) throws Exception;

    /**
     * Add a new nested plugin.
     */
    void addPlugin(Plugin p);

    /**
     * Notify the plugin plus the nested plugins of the event.
     */
    void sendEvent(IEvent e);

}
