package polytech.teamf.game_engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Main {


    public static void main (String[] args) throws IOException {

        Parser p = new Parser("[ { \"type\" : \"codeceasar\", \"non\" : \"yolo\", \"zsdfas\" : \"eeee\" },{\"type2\" : \"codeceassdfdsfsdfar\", \"non2\" : \"yzsdfolo\", \"zsdfas2\" : \"eeqsdfee\" } ]");


        for(HashMap<String,String> h : p.getPlugins()){
            Set cles = h.keySet();
            Iterator<String> it = cles.iterator();
            System.out.println("************************");

            while (it.hasNext()){
                String cle = it.next();
                System.out.println(cle + " : " + h.get(cle)); // tu peux typer plus finement ici
        }


        }

    }
}
