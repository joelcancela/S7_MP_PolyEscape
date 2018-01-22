package plugintests;

import org.junit.Before;
import org.junit.Test;
import polytech.teamf.plugins.Plugin;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class PluginEventTests {

    private TestPlugin p1;
    private TestPlugin p2;


    @Before
    public void setup(){
        p2 = new TestPlugin("TestPlugin" ,"the second plugin", "test", null );
        p1 = new TestPlugin("TestPlugin" , "the first plugin" ,"TEST" , p2 );
        }

    @Test
    public void EventTest(){


        try {
            HashMap<String,Object> hm = new HashMap<>();
            hm.put("attempt" , "falsetest");

           p1.sendEvent(p1.execute(hm));
            assertEquals(p1.getEventRes(),"bad response received from father");
            assertEquals(p2.getEventRes(),"bad response received from father");


            hm = new HashMap<>();
            hm.put("attempt" , "TEST");

            p1.sendEvent(p1.execute(hm));
            assertEquals(p1.getEventRes(),"good response received from father");
            assertEquals(p2.getEventRes(),"good response received from father");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
