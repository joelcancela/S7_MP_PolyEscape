package polytech.teamf.gameengine;

import org.json.JSONObject;
import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.IEvent;
import polytech.teamf.plugins.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Runner {

    /**
     * The list of plugins (ordered)
     */
    private List<Plugin> plugins = new ArrayList<>();

    /**
     * Handle on the current plugin
     */
    private Plugin currentPlugin;

    /**
     * The current plugin state
     */
    private boolean currentPluginStatus = false;

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

    public Plugin getPlugin() {
        return this.currentPlugin;
    }

    /**
     * Notify the plugin of an incoming message
     * Notify the plugin plus its nested plugin of the event
     *
     * @param jsonObject Plugin arguments
     * @return
     */
    public IEvent sendMessage(JSONObject jsonObject) {

        try {
            IEvent e = this.currentPlugin.execute(null);

            if (e instanceof GoodResponseEvent) {
                this.currentPluginStatus = true;
            }

            this.sendEvent(e);
            return e;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Notify the plugin plus its nested plugin of the event
     * @return
     */
    public void sendEvent(IEvent e) {
        this.currentPlugin.sendEvent(e);
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

    public Boolean getStatus(){
        return this.currentPluginStatus;
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