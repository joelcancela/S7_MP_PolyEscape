package polytech.teamf.game_engine;

import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class INIParser {

    public ArrayList<HashMap<String,String>> plugins;
    private String path;

    public INIParser(String aPath) throws IOException {

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
