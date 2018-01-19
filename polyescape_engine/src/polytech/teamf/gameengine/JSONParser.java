package polytech.teamf.gameengine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONParser {

    private List<HashMap<String, String>> plugins;

    /**
     * parse a json file into plugins
     *
     * @param json the json object ready to be parsed
     */
    public JSONParser(String json) {

        plugins = new ArrayList<>();
        JSONArray ja = new JSONArray(json);

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            plugins.add(new HashMap<>());
            for (String s : jo.keySet()) {
                plugins.get(plugins.size() - 1).put(s, jo.getString(s));
            }
        }
    }

    public List<HashMap<String, String>> getPlugins() {
        return plugins;
    }

}