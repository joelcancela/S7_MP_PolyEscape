package main.game_engine;

import main.plugins.IPlugin;
import main.plugins.Plugin;
import main.plugins.PluginFactory;
import java.util.ArrayList;
import java.util.HashMap;

public class Runner {

private ArrayList<Plugin> plugins;
private IPlugin currentPlugin;


    public Runner(String path){

        PluginFactory factory = new PluginFactory(); //initialization of the factory


            Parser p = new Parser(path);

        ArrayList<HashMap<String,String>> l= p.getPlugins();

//        for(HashMap<String,String> h : l) // fill the list of plugins thx to the parser datas
          // plugins.add(factory.create(h.get("name") , h));


    }

    public void run(){

        for(Plugin p : plugins){
            //TODO link this class to REST API done by young Jeremy ;)
       // p.toJSONString(); // todo send this to rest api

            boolean isNotOk = false;
            while(isNotOk) {
                // todo wait the response from RESt api
       //      HashMap<String,String> result =  p.play(); // todo put the given response here

         //       if(result.get("success") == "true"){
                    // todo envoyer bonne réponse a la vue
        //        }
         //       else{
                    // todo envoyer mauvaise réponse a la vue
           //     }


                // todo send response to API rest

            }
        }

    }

}
