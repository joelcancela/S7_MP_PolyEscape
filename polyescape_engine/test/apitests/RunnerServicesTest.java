package apitests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import polytech.teamf.api.RunnerServices;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class RunnerServicesTest extends JerseyTest {

    private JSONArray successConfig = new JSONArray();

    @Before
    public void setup() {
        JSONObject successPluginConfig = new JSONObject();
        successPluginConfig.put("type", "CaesarCipherPlugin");
        successPluginConfig.put("description", "Put a description here");
        successPluginConfig.put("plain_text", "Put the text to cipher here");
        successPluginConfig.put("cipher_padding", "5");
        successConfig.put(successPluginConfig);
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(RunnerServices.class);
    }

    @Test
    public void testRunnerInstantiateRequestSuccess() {
        Entity<String> config = Entity.entity(successConfig.toString(), MediaType.TEXT_PLAIN);
        Response response = target("runners/instantiate").request().put(config); //Here we send POST request
        assertEquals("Status should be 201", 201, response.getStatus());
    }

    @Test
    public void testRunnerInstantiateRequestFailure() {
        Entity<String> config = Entity.entity("", MediaType.TEXT_PLAIN);
        Response response = target("runners/instantiate").request().put(config); //Here we send POST request
        assertEquals("Status should be 400", 400, response.getStatus());
    }

}
