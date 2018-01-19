package polytech.teamf.plugins;

import polytech.teamf.resources.AnswerResource;

import java.util.List;
import java.util.Map;

public interface IPlugin {

    /**
     * Plugin constructor arguments.
     *
     * @return list
     */
    List<String> getArgs();

    /**
     * Plugin input schema.
     *
     * @return map
     */
    Map<String, String> getSchema();

    /**
     * Reads the input data submitted from an external entity to this plugin.
     * Outputs the response, given the plugin initialization and the user input.
     *
     * @param guess the player's guess
     * @return the guess' result
     * @throws Exception error if anything wrong happens
     */
    AnswerResource play(AnswerResource guess) throws Exception;

    /**
     * Plugin status getter.
     *
     * @return true if the plugin is resolved, false otherwise
     */
    boolean getStatus();

}
