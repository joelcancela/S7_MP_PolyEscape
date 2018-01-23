package game_enginetests;

import org.junit.Before;
import org.junit.Test;

import polytech.teamf.gameengine.Runner;
import polytech.teamf.gameengine.Runner;
import polytech.teamf.jarloader.JarLoader;
import polytech.teamf.resources.PluginDescriptionResource;
import polytech.teamf.resources.PluginInstantiationResource;
import polytech.teamf.resources.RunnerInstanceResource;
import polytech.teamf.servletconfig.StartupListener;


import javax.servlet.ServletContextEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RunnerTest {

    private Runner runner;

    @Before
    public void setUp() {


        JarLoader.getInstance().loadServices("./resources/services/");
        JarLoader.getInstance().loadPlugins("./resources/plugins/");

        ArrayList<PluginInstantiationResource> list = new ArrayList<>();
        PluginInstantiationResource p1 = new PluginInstantiationResource();
        p1.setName("CaesarCipherPlugin");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("description", "a new description of cipher plugin");
        hm.put("plain_text" , "TEST");
        hm.put("cipher_padding" , new Integer(1));
        p1.setArgs(hm);
        list.add(p1);
        list.add(p1);

        runner = new Runner(list);

        }

        @Test
        public void getCurrentPluginDescriptionTests () {

            PluginDescriptionResource desc = runner.getCurrentPluginDescription();
            assertEquals("{name=CaesarCipherPlugin, description=a new description of cipher plugin Voici le code à décrypter: UFTU}" ,
                    desc.getAttributes().toString());
            assertEquals("{attempt=class java.lang.String}" , desc.getResponseFormat().toString());
        }

        @Test
        public void sendMessage () {
            HashMap<String,Object> hm = new HashMap<>();
            hm.put("attempt","AZER");

            assertEquals(runner.sendMessage(hm) , false);

            hm = new HashMap<>();
            hm.put("attempt","TEST");

            assertEquals(runner.sendMessage(hm) , true);
        }

        @Test
        public void nextPluginTest () {
            RunnerInstanceResource r = runner.nextPlugin();
            assertEquals(r.getStatus(),"forbidden");

            HashMap<String,Object> hm = new HashMap<>();

            hm = new HashMap<>();
            hm.put("attempt","TEST");

            assertEquals(runner.sendMessage(hm) , true);
             r = runner.nextPlugin();
            assertEquals(r.getStatus(),"ok");

            hm = new HashMap<>();
            hm.put("attempt","TEST");

            assertEquals(runner.sendMessage(hm) , true);
            r = runner.nextPlugin();
            assertEquals(r.getStatus(),"finish");


        }

    }






