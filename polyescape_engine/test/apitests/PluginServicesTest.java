package apitests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import polytech.teamf.api.PluginServices;
import polytech.teamf.api.RunnerServices;
import polytech.teamf.api.ServiceManager;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PluginServicesTest extends JerseyTest {

    private JSONArray successConfig = new JSONArray();
    private String runnerID;

    @Before
    public void setup() {
        JSONObject successPluginConfig = new JSONObject();
        successPluginConfig.put("type", "CaesarCipherPlugin");
        successPluginConfig.put("description", "Put a description here");
        successPluginConfig.put("plain_text", "CAESAR");
        successPluginConfig.put("cipher_padding", "5");
        successConfig.put(successPluginConfig);
    }

    @After
    public void tearsdown() {
        ServiceManager.runnersInstances.clear();
    }

    private void instantiateRunner(JSONArray pluginConfig) {
        Entity<String> config = Entity.entity(pluginConfig.toString(), MediaType.TEXT_PLAIN);
        Response response = target("runners/instantiate").request().put(config);
        runnerID = response.readEntity(String.class);
        JSONObject runnerIDInJo = new JSONObject(runnerID);
        runnerID = runnerIDInJo.getString("id");
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(PluginServices.class, RunnerServices.class);
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

    @Test
    public void testPluginDescriptionRequest() {
        instantiateRunner(successConfig);
        final String pluginDescription = target("plugins/" + runnerID + "/description").request().get(String.class);
        JSONObject pluginDescriptionInJO = new JSONObject(pluginDescription);
        assertTrue("Should have a key description", pluginDescriptionInJO.has("description"));
        assertEquals("Should have a description", "Put a description here HFJXFW", pluginDescriptionInJO.getString("description"));
    }

}
