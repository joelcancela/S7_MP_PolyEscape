package apitests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import polytech.teamf.api.RunnerServices;

import javax.ws.rs.core.Application;

public class RunnerServicesTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(RunnerServices.class);
    }

    @Ignore
    public void filltest() {

    }

}
