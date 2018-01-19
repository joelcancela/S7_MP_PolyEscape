package polytech.teamf.gameengine;

import org.json.JSONObject;
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

        JSONParser JSONParser = new JSONParser(json); // parse the json

        List<HashMap<String, String>> list = JSONParser.getPlugins(); // get the plugins list

        for (HashMap<String, String> map : list) { // fill the list of plugins thx to the JSONParser data
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
        // TODO : fix this method stub
        return new JSONObject().put("description", currentPlugin.getDescription()).put("answer_format" , "");
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
            this.currentPlugin.sendEvent(this.currentPlugin.execute(null));


            //this.currentPlugin.play(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject sendGuess_GetResponse(String guess) throws Exception {
        return null;
        //return currentPlugin.play(new JSONObject(guess));
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
        return null;
    }





    /**
     * make real objects from classes and instantiation datas
     * @param datas the list of PluginInit, used to instanciate the plugin objects
     * @return the instanciated plugin
     * @throws Exception
     */

    public ArrayList<Plugin> getPluginsFromJar(List<MetaPlugin> datas, List<Class> pluginClasses) throws Exception
    {
        ArrayList<Plugin> plugins = new ArrayList<>();

        for(MetaPlugin pi : datas) {
            for(Class c : pluginClasses){
                if(c.getName().equals(pi.getName())) {
                    Object p = c.getClass().getDeclaredConstructor(pi.getTypes()).newInstance(pi.getValues());
                    plugins.add((Plugin)p);
                }
            }
        }
        return plugins;
    }

}
