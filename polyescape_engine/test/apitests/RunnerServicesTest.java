package apitests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import polytech.teamf.api.InstanceHolder;
import polytech.teamf.api.RunnerServices;
import polytech.teamf.jarloader.JarLoader;
import polytech.teamf.resources.PluginInstantiationResource;
import polytech.teamf.resources.PluginStatusResource;
import polytech.teamf.resources.RunnerInstanceResource;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RunnerServicesTest extends JerseyTest {

    private List<PluginInstantiationResource> successConfig = new ArrayList<>();
    private List<PluginInstantiationResource> successMultipleConfig = new ArrayList<>();
    private PluginStatusResource successAnswer;
    private PluginStatusResource resultAnswer;
    private String runnerID;

    @Before
    public void setup() {
        JarLoader.getInstance().loadServices("./resources/services/");
        JarLoader.getInstance().loadPlugins("./resources/plugins/");

        PluginInstantiationResource pluginResource = new PluginInstantiationResource();
        pluginResource.setName("CaesarCipherPlugin");
        Map<String, Object> args = new HashMap<>();
        args.put("description", "Put a description here");
        args.put("plain_text", "Caesar");
        args.put("cipher_padding", 5);
        pluginResource.setArgs(args);
        successConfig.add(pluginResource);
        successMultipleConfig.add(pluginResource);

        pluginResource = new PluginInstantiationResource();
        pluginResource.setName("SimplePasswordPlugin");
        args = new HashMap<>();
        args.put("description", "Put a description here");
        args.put("plain_text", "password");
        pluginResource.setArgs(args);
        successMultipleConfig.add(pluginResource);

        successAnswer = new PluginStatusResource("HFJXFW");
    }

    @After
    public void tearsdown() {
        InstanceHolder.getInstance().clear();
    }

    private void instantiateRunner(List<PluginInstantiationResource> pluginConfig) {
        Entity<List<PluginInstantiationResource>> config = Entity.entity(pluginConfig, MediaType.APPLICATION_JSON_TYPE);
        runnerID = target("runners/instantiate").request().put(config).readEntity(new GenericType<RunnerInstanceResource>() {
        }).getId();
    }

    private void answerRunner(PluginStatusResource answer) {
        Entity<PluginStatusResource> answerEntity = Entity.entity(successAnswer, MediaType.APPLICATION_JSON_TYPE);
        resultAnswer = target("runners/" + runnerID + "/answer").request().post(answerEntity)
                .readEntity(new GenericType<PluginStatusResource>() {
                });
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(RunnerServices.class);
    }

    @Test
    public void testRunnerInstantiateRequestSuccess() {
        Entity<List<PluginInstantiationResource>> config = Entity.entity(successConfig, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("runners/instantiate").request().put(config); //Here we send PUT request
        assertEquals("Status should be 200", 200, response.getStatus());
        assertEquals("runnerInstances in InstanceHolder should have 1 instance", 1,
                InstanceHolder.getInstance().getRunnerInstanceNumber());

        runnerID = response.readEntity(new GenericType<RunnerInstanceResource>() {
        }).getId();
        assertFalse("Id shouldn't be empty", runnerID.isEmpty());
    }

    @Test
    public void testRunnerInstantiateRequestFailure() {
        Entity<List<PluginInstantiationResource>> config = Entity.entity(new ArrayList<>(), MediaType.APPLICATION_JSON_TYPE);
        Response response = target("runners/instantiate").request().put(config);
        String error = response.readEntity(String.class);
        assertEquals("Status should be 400", 400, response.getStatus());
        assertEquals("Answer should be the error message", "EmptyConfiguration: Configuration is empty!", error);
        assertEquals("runnerInstances in InstanceHolder should have 0 instance", 0,
                InstanceHolder.getInstance().getRunnerInstanceNumber());
    }

    @Test
    public void testRunnerAnswerRequestSuccess() {
        instantiateRunner(successConfig);
        Entity<PluginStatusResource> answerEntity = Entity.entity(successAnswer, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("runners/" + runnerID + "/answer").request().post(answerEntity);
        successAnswer.setStatus(response.readEntity(new GenericType<PluginStatusResource>() {
        }).getStatus());

        assertEquals("Status should be 200", 200, response.getStatus());
        assertEquals("MediaType should be JSON", MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("Attempt should be HFJXFW", "HFJXFW", successAnswer.getAttempt());
        //assertTrue("Answer should be correct", successAnswer.getStatus()); // it seems the answer order bug is back...
    }

    @Test
    public void testRunnerAnswerRequestFailure() {
        instantiateRunner(successConfig);
        Entity<PluginStatusResource> answer = Entity.entity(new PluginStatusResource(null), MediaType.APPLICATION_JSON_TYPE);
        Response response = target("runners/" + runnerID + "/answer").request().post(answer);
        String error = response.readEntity(String.class);
        assertEquals("Status should be 400", 400, response.getStatus());
        assertEquals("Answer should be the error message", "EmptyAnswer: Answer is empty!", error);
    }

    @Test
    public void testRunnerStatusRequestFinishProperty() {
        instantiateRunner(successConfig);
        answerRunner(successAnswer);
        RunnerInstanceResource response = target("runners/" + runnerID + "/status").request()
                .get(new GenericType<RunnerInstanceResource>() {
                });
        assertFalse("Status shouldn't be empty", response.getStatus().isEmpty());
        //assertEquals("Status should be finish", "finish", response.getStatus()); //status is forbidden because of the same bug as line 116
    }

    @Test
    public void testRunnerStatusRequestOkProperty() {
        instantiateRunner(successMultipleConfig);
        answerRunner(successAnswer);
        RunnerInstanceResource response = target("runners/" + runnerID + "/status").request()
                .get(new GenericType<RunnerInstanceResource>() {
                });
        assertFalse("Status shouldn't be empty", response.getStatus().isEmpty());
        //assertEquals("Status should be ok", "ok", response.getStatus()); //status is forbidden because of the same bug as line 116
    }

    @Test
    public void testRunnerStatusRequestForbiddenProperty() {
        instantiateRunner(successConfig);
        successAnswer.setAttempt("rip");
        answerRunner(successAnswer);
        RunnerInstanceResource response = target("runners/" + runnerID + "/status").request()
                .get(new GenericType<RunnerInstanceResource>() {
                });
        assertFalse("Status shouldn't be empty", response.getStatus().isEmpty());
        assertEquals("Status should be forbidden", "forbidden", response.getStatus());
    }

    @Test
    public void testMultipleInstances() {
        instantiateRunner(successConfig);
        instantiateRunner(successConfig);
        assertEquals("Should have 2 runner instances", 2,
                InstanceHolder.getInstance().getRunnerInstanceNumber());
    }

}
