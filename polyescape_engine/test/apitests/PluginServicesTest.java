package apitests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONArray;
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
    public void testPluginList() {
        final String pluginList = target("plugins/list").request().get(String.class);
        JSONArray pluginListInJA = new JSONArray(pluginList);
        assertTrue("Should be different from 0", pluginListInJA.length() >= 1);
    }

}
