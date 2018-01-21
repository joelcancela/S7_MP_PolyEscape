//package plugintests;
//
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//import SimplePasswordPlugin;
//import polytech.teamf.resources.AnswerResource;
//
//import static junit.framework.Assert.assertEquals;
//
//public class SimplePasswordPluginTest {
//
//    private SimplePasswordPlugin plugin;
//
//    @Before
//    public void setup() {
//        plugin = new SimplePasswordPlugin("a random description", "ANSWER");
//    }
//
//    @Test
//    public void toStringTests() {
//        JSONObject obj = new JSONObject(plugin.toString());
//
//        assertEquals("description", obj.get("description"), "a random description");
//        assertEquals("name", obj.getString("name"), "Epreuve mot de passe simple");
//        assertEquals("plain_text", obj.getString("plain_text"), "ANSWER");
//        assertEquals("answer_format", obj.getString("answer_format"), "text");
//    }
//
//    @Test
//    public void playTest() {
//<<<<<<< HEAD
//        //assertEquals("test false", plugin.play(new JSONObject("{attempt : 72 }")).getString("success"), "false");
//        //assertEquals("test true", plugin.play(new JSONObject("{attempt : ANSWER }")).getString("success"), "true");
//=======
//        assertEquals("test false", plugin.play(new AnswerResource("72")).getSuccess(), "false");
//        assertEquals("test true", plugin.play(new AnswerResource("answer")).getSuccess(), "true");
//>>>>>>> feature/jsontoresource
//    }
//
//}
