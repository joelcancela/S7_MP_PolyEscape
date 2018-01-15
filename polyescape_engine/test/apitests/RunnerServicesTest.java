package apitests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import polytech.teamf.api.RunnerServices;
import polytech.teamf.api.ServiceManager;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RunnerServicesTest extends JerseyTest {

    private JSONArray successConfig = new JSONArray();
    private JSONArray successMultipleConfig = new JSONArray();
    private JSONObject successAnswer = new JSONObject();
    private String runnerID;

    @Before
    public void setup() {
        JSONObject successPluginConfig = new JSONObject();
        successPluginConfig.put("type", "CaesarCipherPlugin");
        successPluginConfig.put("description", "Put a description here");
        successPluginConfig.put("plain_text", "HFJXFW");
        successPluginConfig.put("cipher_padding", "5");
        successConfig.put(successPluginConfig);
        successMultipleConfig.put(successPluginConfig);

        successPluginConfig = new JSONObject();
        successPluginConfig.put("type", "MultipleChoiceQuestionPlugin");
        successPluginConfig.put("description", "Put a description here");
        successPluginConfig.put("answers_csv", "answer");
        successPluginConfig.put("correct_answers_csv", "correct answer");
        successMultipleConfig.put(successPluginConfig);

        successAnswer.put("attempt", "HFJXFW");
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

    private void answerRunner(JSONObject answer) {
        Entity<String> answerEntity = Entity.entity(answer.toString(), MediaType.TEXT_PLAIN);
        target("runners/answer").request().post(answerEntity);
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(RunnerServices.class);
    }

    @Test
    public void testRunnerInstantiateRequestSuccess() {
        Entity<String> config = Entity.entity(successConfig.toString(), MediaType.TEXT_PLAIN);
        Response response = target("runners/instantiate").request().put(config); //Here we send PUT request
        assertEquals("Status should be 200", 200, response.getStatus());
        assertEquals("runnerInstances in ServiceManager should have 1 instances", 1, ServiceManager.runnersInstances.size());

        runnerID = response.readEntity(String.class);
        JSONObject runnerIDInJo = new JSONObject(runnerID);
        assertTrue("Should have a key id", runnerIDInJo.has("id"));
    }

    @Test
    public void testRunnerInstantiateRequestFailure() {
        Entity<String> config = Entity.entity("", MediaType.TEXT_PLAIN);
        Response response = target("runners/instantiate").request().put(config);
        assertEquals("Status should be 400", 400, response.getStatus());
        assertEquals("runnerInstances in ServiceManager should have 0 instances", 0, ServiceManager.runnersInstances.size());
    }

    @Test
    public void testRunnerAnswerRequestSuccess() {
        instantiateRunner(successConfig);
        Entity<String> answer = Entity.entity(successAnswer.toString(), MediaType.TEXT_PLAIN);
        Response response = target("runners/" + runnerID + "/answer").request().post(answer);
        String result = response.readEntity(String.class);
        JSONObject resultInJo = new JSONObject(result);

        assertEquals("Status should be 200", 200, response.getStatus());
        assertEquals("MediaType should be JSON", MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertTrue("Should have a key success", resultInJo.has("success"));
        assertEquals("Should be true", "true", resultInJo.getString("success"));
    }

    @Test
    public void testRunnerAnswerRequestFailure() {
        instantiateRunner(successConfig);
        Entity<String> answer = Entity.entity("", MediaType.TEXT_PLAIN);
        Response response = target("runners/" + runnerID + "/answer").request().post(answer);
        assertEquals("Status should be 400", 400, response.getStatus());
    }

    @Test
    public void testRunnerStatusRequestFinishProperty() {
        instantiateRunner(successConfig);
        answerRunner(successAnswer);
        final String response = target("runners/" + runnerID + "/status").request().get(String.class);
        JSONObject responseInJO = new JSONObject(response);
        assertTrue("Should be true", responseInJO.has("status"));
        assertEquals("Status should be finish", "finish", responseInJO.getString("status"));
    }

    @Test
    public void testRunnerStatusRequestOkProperty() {
        instantiateRunner(successMultipleConfig);
        answerRunner(successAnswer);
        final String response = target("runners/" + runnerID + "/status").request().get(String.class);
        JSONObject responseInJO = new JSONObject(response);
        assertTrue("Should be true", responseInJO.has("status"));
        assertEquals("Status should be ok", "ok", responseInJO.getString("status"));
    }

    @Test
    public void testMultipleInstances() {
        instantiateRunner(successConfig);
        instantiateRunner(successConfig);
        assertEquals("Should have 2 runner instances", 2, ServiceManager.runnersInstances.size());
    }

}
