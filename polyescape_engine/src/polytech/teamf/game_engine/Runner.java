package polytech.teamf.game_engine;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import polytech.teamf.plugins.IPlugin;
import polytech.teamf.plugins.Plugin;
import polytech.teamf.plugins.PluginFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class Runner {

    private ArrayList<Plugin> plugins; // list of plugins ordered
    private Plugin currentPlugin;
    int it = 0; // where are we in the plugins list

    /**
     * runner that parse the json & instance plugins
     * @param json
     */
    public Runner(String json) {

        PluginFactory factory = new PluginFactory(); //initialization of the factory


        Parser parser = new Parser(json); // parse the json

        ArrayList<HashMap<String, String>> list = parser.getPlugins(); // we get all the

        for(HashMap<String,String> map : list) { // fill the list of plugins thx to the parser datas
            JSONObject toBuild = new JSONObject();
            for (String str : map.keySet()) {
                toBuild.append(str,map.get(str));

            }
            plugins.add(factory.create(map.get("type") , toBuild));

        }

        currentPlugin = plugins.get(it);
    }

    /**
     * get the description / contexte  of the plugin
     * @return
     */
    public JSONObject getDescription(){
        return new JSONObject().put("description",currentPlugin.getDescription());
    }

    /**
     * you give a answer and it say if you are right or wrong
     * @param guess
     * @return
     * @throws Exception
     */
    public JSONObject sendGuess_GetResponse(String guess) throws Exception {
        return currentPlugin.play(new JSONObject(guess));

    }


    /**
     * the current plugin is the next on the list
     */
    public JSONObject nextPlugin(){

        it++;

        if(plugins.size() == it)
            return new JSONObject().put("status", "finish");

        currentPlugin = plugins.get(it);
            return new JSONObject().put("status", "ok");
    }

}
