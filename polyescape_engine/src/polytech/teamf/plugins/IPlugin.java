package polytech.teamf.plugins;

import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.util.*;

public interface IPlugin {

    /**
     * Plugin constructor arguments
     */
    List<String> ARGS = new ArrayList<>();

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
    static Map<String, String> getSchema() {
        return SCHEMA;
    }

    /**
     * Reads the input data submitted from an external entity to this plugin
     * Outputs the response, given the plugin initialization and the user input
     *
     * @param args The inputs a JSObject
     * @return JSObject
     * @throws Exception Error if anything wrong happens
     */
    JSONObject play(JSONObject args) throws Exception;
}
