package main.plugins;

import java.util.HashMap;

public interface IPlugin {

    /**
     * Initializes the plugin with an associative array
     * Acts like a constructor
     *
     * @param args The construction arguments
     * @return IPlugin
     */
    IPlugin initialize(HashMap<String, String> args);

    /**
     * Reads the input data submitted from an external entity to this plugin
     *
     * @param args The inputs as an array
     */
    void readIn(HashMap<String, String> args);

    /**
     * Outputs the response, given a configuration and the inputs
     *
     * @return HashMap
     */
    HashMap<String, String> checkOut();

    /**
     * Returns the plugin representation as a JSON String
     *
     * @return String
     */
    String toJSONString();
}
