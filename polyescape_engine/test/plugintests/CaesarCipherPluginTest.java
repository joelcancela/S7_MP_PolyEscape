package plugintests;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import polytech.teamf.plugins.CaesarCipherPlugin;
import polytech.teamf.plugins.Plugin;


import static junit.framework.Assert.assertEquals;


public class CaesarCipherPluginTest {


    private CaesarCipherPlugin plugin;

    @Before
    public void setup(){
        plugin = new CaesarCipherPlugin("a random description", "ANSWER" , 1);
    }

    @Test
    public void toStringTests(){
        JSONObject obj = new JSONObject(plugin.toString());



        assertEquals("description" , obj.get("description"), "a random description BOTXFS");
        assertEquals("name",obj.getString("name") , "Epreuve code Caesar" );
        assertEquals("plain_text" , obj.getString("plain_text") , "ANSWER");
        assertEquals("ciphered_text", obj.getString("ciphered_text") , "BOTXFS");

    }


    @Test
    public void playTest(){

        assertEquals("test false" , plugin.play(new JSONObject("{attempt_text : 72 }")).getString("success"),"false" );
        assertEquals("test true" , plugin.play(new JSONObject("{attempt_text : answer }")).getString("success"),"true" );

    }
}
