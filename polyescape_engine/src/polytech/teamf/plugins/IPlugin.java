package polytech.teamf.plugins;

import org.json.JSONObject;
import java.util.List;
import java.util.Map;

public interface IPlugin {

    /**
     * Plugin constructor arguments
     * @return List
     */
    List<String> getArgs();

    /**
     * Plugin input schema
     * @return Map
     */
    Map<String, String> getSchema();

    /**
     * Reads the input data submitted from an external entity to this plugin
     * Outputs the response, given the plugin initialization and the user input
     *
     * @param args The inputs a JSObject
     * @return JSObject
     * @throws Exception Error if anything wrong happens
     */
    JSONObject play(JSONObject args) throws Exception;


    /**
     * return the status of the plugin : is the answer was right : true or false
     */
    String getStatus();
}
