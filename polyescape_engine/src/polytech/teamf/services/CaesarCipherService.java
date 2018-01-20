package polytech.teamf.services;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Class CaesarCipherService
 *
 * @author Joël CANCELA VAZ
 */
public class CaesarCipherService extends Service {

    public static final String MESSAGE_KEY = "message";
    public static final String PADDING_KEY = "padding";

    public CaesarCipherService() {
        this.name = "Service de cryptage en Code César";
        this.inputService = false;
        this.serviceHost = "https://www.joelcancela.fr/services/fun/caesar_cipher.php";
    }

    @Override
    public String call(Map<String, Object> callArgs) {
        WebTarget target = client.target(serviceHost);
        target = target.queryParam(MESSAGE_KEY, callArgs.get(MESSAGE_KEY))
                .queryParam(PADDING_KEY, (int) callArgs.get(PADDING_KEY));
        Invocation.Builder builder = target.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        return builder.get(String.class);
    }

}
