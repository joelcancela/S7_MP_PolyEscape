package polytech.teamf.api;

import polytech.teamf.game_engine.Runner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Service handles everything that every others services might need.
 */
public class ServiceManager {

    private static final String CAESAR_CIPHER_SERVICE_URI = "https://www.joelcancela.fr/services/fun/caesar_cipher.php";

    private static Runner runner = null;

    /**
     * Get the unique runner instance.
     *
     * @param config The plugins configurations.
     * @return The unique runner instance.
     */
    public static Runner getRunnerInstance(String config) {
        if (runner == null) {
            runner = new Runner(config);
        }
        return runner;
    }

    /**
     * Reset the runner to null
     */
    public static void resetRunner() {
        runner = null;
    }

    /**
     * Call the Caesar Cihper service which allow to
     *
     * @param plain_text The text to cipher
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
