package main.plugins;

import java.util.List;
import java.util.Map;

public interface IPlugin {

    /**
     * Returns the list of expected arguments as Strings
     */
    List<String> listArgs();

    /**
     * Reads the input data submitted from an external entity to this plugin
     *
     * @param args The inputs as an array
     */
    void readIn(Map<String, String> args);

    /**
     * Outputs the response, given a configuration and the inputs
     *
     * @return Map
     */
    Map<String, String> checkOut();

    /**
     * Returns the plugin representation as a JSON String
     *
     * @return String
     */
    String toJSONString();
}
