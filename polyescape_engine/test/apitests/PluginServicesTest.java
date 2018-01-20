//package apitests;
//
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import polytech.teamf.api.PluginServices;
//import polytech.teamf.api.RunnerServices;
//import polytech.teamf.api.InstanceHolder;
//
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class PluginServicesTest extends JerseyTest {
//
//    private List<PluginResource> successConfig = new ArrayList<>();
//    private String runnerID;
//
//    @Before
//    public void setup() {
//        PluginResource pluginResource = new PluginResource();
//        pluginResource.setType("CaesarCipherPlugin");
//        pluginResource.addToArgs("description", "Put a description here")
//                .addToArgs("plain_text", "CAESAR")
//                .addToArgs("cipher_padding", "5");
//        successConfig.add(pluginResource);
//    }
//
//    @After
//    public void tearsdown() {
//        InstanceHolder.runnersInstances.clear();
//    }
//
//    private void instantiateRunner(List<PluginResource> pluginConfig) {
//        Entity<List<PluginResource>> config = Entity.entity(pluginConfig, MediaType.APPLICATION_JSON_TYPE);
//        Response response = target("runners/instantiate").request().put(config);
//        runnerID = response.readEntity(String.class);
//        JSONObject runnerIDInJo = new JSONObject(runnerID);
//        runnerID = runnerIDInJo.getString("id");
//    }
//
//    @Override
//    protected Application configure() {
//        return new ResourceConfig(PluginServices.class, RunnerServices.class);
//    }
//
//    @Test
//    public void testPluginListRequestProperties() {
//        final String pluginList = target("plugins/list").request().accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
//        JSONArray pluginListInJA = new JSONArray(pluginList);
//        assertTrue("Should be different from 0", pluginListInJA.length() >= 1);
//        assertTrue("Should have a key type", ((JSONObject) pluginListInJA.get(0)).getString("type") != null);
//        assertTrue("Should have a key name", ((JSONObject) pluginListInJA.get(0)).getString("name") != null);
//        assertTrue("Should have a key args", ((JSONObject) pluginListInJA.get(0)).getJSONArray("args") != null);
//        assertTrue("Should have a key schema", ((JSONObject) pluginListInJA.get(0)).getJSONObject("schema") != null);
//    }
//
//    @Test
//    public void testPluginDescriptionRequest() {
//        instantiateRunner(successConfig);
//        final String pluginDescription = target("plugins/" + runnerID + "/description").request().get(String.class);
//        JSONObject pluginDescriptionInJO = new JSONObject(pluginDescription);
//        assertTrue("Should have a key description", pluginDescriptionInJO.has("description"));
//        assertEquals("Should have a description", "Put a description here HFJXFW", pluginDescriptionInJO.getString("description"));
//    }
//
//}
