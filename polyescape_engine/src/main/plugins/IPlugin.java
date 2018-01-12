package main.plugins;

import java.util.List;
import java.util.Map;

public interface IPlugin {

    /**
     * Returns the list of expected arguments to initialize this plugin as Strings
     */
    List<String> listArgs();

    /**
     * Returns the expected list of input arguments as a JSON formatted string
     *
     * @return String
     */
    String getJSONSchema();

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
