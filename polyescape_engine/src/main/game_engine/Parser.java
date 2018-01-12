package main.game_engine;

import main.plugins.Plugin;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Parser {

    public ArrayList<HashMap<String,String>> plugins;
    private String path;

    public Parser(String aPath) throws IOException {

        plugins = new ArrayList();
        Wini ini = new Wini(new File(aPath));


        //get all section --> our plugin
        Collection<Profile.Section> list = ini.values();
        for(Profile.Section section : list){
                plugins.add(new HashMap());


            //output keys of all options of one section
            for(String key : section.keySet()){
                plugins.get(plugins.size()-1).put(key,section.fetch(key));
                }
        }


    }




}
