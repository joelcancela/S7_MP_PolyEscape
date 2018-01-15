package apitests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import polytech.teamf.api.PluginServices;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertTrue;

public class PluginServicesTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(PluginServices.class);
    }

    @Test
    public void testPluginListRequestProperties() {
        final String pluginList = target("plugins/list").request().get(String.class);
        JSONArray pluginListInJA = new JSONArray(pluginList);
        assertTrue("Should be different from 0", pluginListInJA.length() >= 1);
        assertTrue("Should have a key type", ((JSONObject) pluginListInJA.get(0)).getString("type") != null);
        assertTrue("Should have a key name", ((JSONObject) pluginListInJA.get(0)).getString("name") != null);
        assertTrue("Should have a key args", ((JSONObject) pluginListInJA.get(0)).getJSONArray("args") != null);
        assertTrue("Should have a key schema", ((JSONObject) pluginListInJA.get(0)).getJSONObject("schema") != null);
    }

}
