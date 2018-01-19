package plugintests;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import polytech.teamf.plugins.CaesarCipherPlugin;
import polytech.teamf.resources.AnswerResource;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CaesarCipherPluginTest {

    private CaesarCipherPlugin plugin;

    @Before
    public void setup() {
        plugin = new CaesarCipherPlugin("a random description", "ANSWER", 1);
    }

    @Test
    public void toStringTests() {
        JSONObject obj = new JSONObject(plugin.toString());

        assertEquals("description", obj.get("description"), "a random description BOTXFS");
        assertEquals("name", obj.getString("name"), "Epreuve code Caesar");
        assertEquals("plain_text", obj.getString("plain_text"), "ANSWER");
        assertEquals("ciphered_text", obj.getString("ciphered_text"), "BOTXFS");
        assertEquals("answer_format", obj.getString("answer_format"), "text");
    }

    @Test
    public void playTest() {
        assertFalse("test false", plugin.play(new AnswerResource("72")).getSuccess());
        assertTrue("test true", plugin.play(new AnswerResource("answer")).getSuccess());
    }

}
