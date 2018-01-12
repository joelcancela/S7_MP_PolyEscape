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
    Map<String, String> JSON_SCHEMA = new HashMap<>();

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
        return JSON_SCHEMA;
    }

    /**
     * Reads the input data submitted from an external entity to this plugin
     *
     * @param args The inputs as an array
     */
    void readIn(Map<String, String> args);

    /**
     * Outputs the response, given the plugin initialization and the user input
     *
     * @return Map
     */
    Map<String, String> checkOut();
}
