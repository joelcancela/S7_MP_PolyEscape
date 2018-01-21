package apitests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import polytech.teamf.api.PluginServices;
import polytech.teamf.jarloader.JarLoader;
import polytech.teamf.plugins.MetaPlugin;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PluginServicesTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(PluginServices.class);
    }

    @Before
    public void before() {
        //JarLoader.getInstance().loadServices("./resources/services/");
        JarLoader.getInstance().loadPlugins("./resources/plugins/");
    }

    @Test
    public void testPluginListRequestProperties() {
        List<MetaPlugin> pluginList = target("plugins/list").request().get(new GenericType<List<MetaPlugin>>() {
        });
        assertTrue("Should be different from 0", pluginList.size() >= 1);
        assertFalse("Name shouldn't be empty", pluginList.get(0).getName().isEmpty());
        assertFalse("Constructor args shouldn't be empty", pluginList.get(0).getConstructorArgs().isEmpty());
        assertFalse("Schema shouldn't be empty", pluginList.get(0).getSchema().isEmpty());
    }

}
