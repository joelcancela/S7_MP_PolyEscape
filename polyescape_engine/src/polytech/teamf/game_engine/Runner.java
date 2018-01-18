package polytech.teamf.game_engine;

import org.json.JSONObject;
import polytech.teamf.plugins.IPlugin;
import polytech.teamf.plugins.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Runner {

    /**
     * The list of plugins ordered
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
     * @param json The plugins configuration.
     */
    public Runner(String json) {

        Parser parser = new Parser(json); // parse the json

        List<HashMap<String, String>> list = parser.getPlugins(); // get the plugins list

        for (HashMap<String, String> map : list) { // fill the list of plugins thx to the parser data
            JSONObject toBuild = new JSONObject();
            for (String str : map.keySet()) {
                toBuild.put(str, map.get(str));
            }
            //plugins.add(PluginFactory.create(map.get("type"), toBuild));
            // TODO : implement correct behavior here without a Plugin Factory
        }

        currentPlugin = plugins.get(it);
    }

    /**
     * Get the description / context of the plugin.
     *
     * @return A JSONObject containing the current plugin description & the format of the waited answer.
     */
    public JSONObject getDescription() {
        return new JSONObject().put("description", currentPlugin.getDescription()).put("answer_format" , currentPlugin.getAns_format());
    }

    public Plugin getPlugin() {
        return this.currentPlugin;
    }

    /**
     * Notify the plugin of an incoming message (sent by a service)
     *
     * @param jsonObject Plugin arguments
     */
    public void sendMessage(JSONObject jsonObject) {
        try {
            this.currentPlugin.play(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Given an answer, tells if you are right or wrong
     *
     * @param guess The player's answer to the current step.
     * @return The answer's result.
     * @throws Exception See {@link IPlugin#play(org.json.JSONObject)}
     */
    public JSONObject sendGuess_GetResponse(String guess) throws Exception {
        return currentPlugin.play(new JSONObject(guess));
    }

    /**
     * The current plugin becomes the next in the list if there is another plugin to play (next step in the game).
     *
     * @return A JSONObject containing the status of the game. The status will be "finish" in case there are no more
     * plugins to play or "ok" otherwise.
     */
    public JSONObject nextPlugin() {

        it++;

        if (it >= plugins.size())
            return new JSONObject().put("status", "finish");

        currentPlugin = plugins.get(it);
        return new JSONObject().put("status", "ok");
    }

    public String getStatus(){
        return currentPlugin.getStatus();
    }

}
