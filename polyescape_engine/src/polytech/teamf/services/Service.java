package polytech.teamf.services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Map;

public abstract class Service {

    protected String name = "A service made by a someone";
    protected boolean inputService = false;
    protected String serviceHost = "localhost:8080";
    protected Client client = ClientBuilder.newBuilder().newClient();

    public String call(Map<String, Object> callArgs) {
        return null;
    }

}
