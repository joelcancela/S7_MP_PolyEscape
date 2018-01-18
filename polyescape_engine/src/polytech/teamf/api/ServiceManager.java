package polytech.teamf.api;

import polytech.teamf.game_engine.Runner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Service handles everything that every others services might need.
 */
public class ServiceManager {

    private static final String CAESAR_CIPHER_SERVICE_URI = "https://www.joelcancela.fr/services/fun/caesar_cipher.php";

    public static Map<String, Runner> runnersInstances = new HashMap<>();

    /**
     * Create a new unique runner instance.
     *
     * @param config The plugins configurations.
     */
    public static void createNewInstance(String uuid, String config) {
        Runner runner = new Runner(config);
        runnersInstances.put(uuid, runner);
    }

    /**
     * Call the Caesar Cihper service which allow to
     *
     * @param plain_text     The text to cipher
     * @param cipher_padding The cipher
     * @return
     */
    public static String callCaesarCipherPlugin(String plain_text, int cipher_padding) {
        Client client = ClientBuilder.newBuilder().newClient();
        WebTarget target = client.target(CAESAR_CIPHER_SERVICE_URI);
        target = target.queryParam("message", plain_text).queryParam("padding", cipher_padding);
        Invocation.Builder builder = target.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        return builder.get(String.class);
    }

}
