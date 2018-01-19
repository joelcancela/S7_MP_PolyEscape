package polytech.teamf.game_engine;

import polytech.teamf.plugins.IPlugin;
import polytech.teamf.plugins.Plugin;
import polytech.teamf.plugins.PluginFactory;
import polytech.teamf.resources.AnswerResource;
import polytech.teamf.resources.PluginResource;
import polytech.teamf.resources.RunnerResource;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    /**
     * The list of plugins ordered.
     */
    private List<Plugin> plugins = new ArrayList<>();

    private Plugin currentPlugin;

    /**
     * The current plugin number in {@link #plugins}.
     */
    private int it = 0;

    /**
     * Runner that parse the json & instantiate plugins.
     *
     * @param config the plugins configuration
     */
    public Runner(List<PluginResource> config) {

        for (PluginResource plugin : config) {
            plugins.add(PluginFactory.create(plugin.getType(), plugin.getArgs()));
        }

        currentPlugin = plugins.get(it);
    }

    /**
     * Get the description / context of the plugin.
     *
     * @return an object containing the current plugin description & the format of the waited answer.
     */
    public PluginResource getDescription() {
        return new PluginResource(currentPlugin.getDescription(), currentPlugin.getAns_format());
    }

    public Plugin getPlugin() {
        return this.currentPlugin;
    }

    /**
     * Notify the plugin of an incoming message (sent by a service).
     *
     * @param message plugin arguments
     */
    public void sendMessage(AnswerResource message) {
        try {
            this.currentPlugin.play(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Given an answer, tells if you are right or wrong.
     *
     * @param guess the player's answer to the current step
     * @return the answer's result
     * @throws Exception see {@link IPlugin#play(AnswerResource)}
     */
    public AnswerResource sendGuess_GetResponse(AnswerResource guess) throws Exception {
        return currentPlugin.play(guess);
    }

    /**
     * The current plugin becomes the next in the list if there is another plugin to play (next step in the game).
     *
     * @return a JSONObject containing the status of the game. The status will be "finish" in case there are no more
     * plugins to play or "ok" otherwise.
     */
    public RunnerResource nextPlugin() {
        it++;

        if (it >= plugins.size()) {
            return new RunnerResource("finish");
        }

        currentPlugin = plugins.get(it);
        return new RunnerResource("ok");
    }

    public boolean getStatus() {
        return currentPlugin.getStatus();
    }

}
