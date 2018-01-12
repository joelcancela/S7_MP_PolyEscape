package main.plugins;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface IPlugin {

    /**
     * Plugin constructor arguments
     */
    List<String> ARGS = new LinkedList<>();

    /**
     * Plugin input schema
     */
    Map<String, String> SCHEMA = new HashMap<>();

    /**
     * Returns the List of String of expected arguments to initialize this plugin
     */
    static List<String> listArgs() {
        return ARGS;
    }

    /**
     * Returns the expected list of input arguments
     *
     * @return Map
     */
    static Map<String, String> getJSONSchema() {
        return SCHEMA;
    }

    /**
     * Reads the input data submitted from an external entity to this plugin
     * Outputs the response, given the plugin initialization and the user input
     *
     * @param args The inputs as an array
     * @return Map
     */
    Map<String, String> play(Map<String, String> args);
}
