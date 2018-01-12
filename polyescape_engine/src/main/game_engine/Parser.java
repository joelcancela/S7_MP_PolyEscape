package main.game_engine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {


    private ArrayList<HashMap<String,String>> plugins;
    private String path;


    /**
     *parse a json file into plugins
     *  @param json the json object ready to be parsed
     *
     * @throws IOException
     */
    public Parser(String json) throws IOException {

        plugins = new ArrayList();
        JSONArray ja = new JSONArray(json);


        for(int i=0;i<ja.length();i++){
            JSONObject jo = ja.getJSONObject(i);
            plugins.add(new HashMap());
            for(String s : jo.keySet()){
                plugins.get(plugins.size()-1).put(s,jo.getString(s));
            }
        }
    }




    public ArrayList<HashMap<String, String>> getPlugins() {
        return plugins;
    }


}
