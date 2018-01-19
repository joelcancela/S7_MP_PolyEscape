package game_enginetests;

import org.junit.Before;
import org.junit.Test;
import polytech.teamf.game_engine.Runner;
import polytech.teamf.resources.AnswerResource;
import polytech.teamf.resources.PluginResource;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RunnerTest {

    private Runner runner;

    @Before
    public void setUp() {
        List<PluginResource> config = new ArrayList<>();
        config.add(new PluginResource().addToArgs("type", "CaesarCipherPlugin").addToArgs("description", "dit 42")
                .addToArgs("plain_text", "COUCOU").addToArgs("cipher_padding", 1));
        runner = new Runner(config);
    }

    @Test
    public void getDescriptionTests() {
        assertEquals("test  description : ", runner.getDescription().getDescription(), "dit 42 DPVDPV");
        assertEquals("test  format : ", runner.getDescription().getDescription(), "text");
    }

    @Test
    public void sendGuess_GetResponseTests() {
        try {
            assertFalse("test wrong answer", runner.sendGuess_GetResponse(new AnswerResource("72")).getSuccess());
            assertTrue("test right answer", runner.sendGuess_GetResponse(new AnswerResource("COUCOU")).getSuccess());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void nextPluginTest() {
        assertEquals("test is finished", runner.nextPlugin().getStatus(), "finish");
        List<PluginResource> config = new ArrayList<>();
        PluginResource plugin = new PluginResource().addToArgs("type", "CaesarCipherPlugin").addToArgs("description", "dit 42")
                .addToArgs("plain_text", "COUCOU").addToArgs("cipher_padding", 1);
        config.add(plugin);
        config.add(plugin);
        runner = new Runner(config);
        assertEquals("test is finished", runner.nextPlugin().getStatus(), "ok");
    }

}





