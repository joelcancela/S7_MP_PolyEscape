package polytech.teamf.services;

import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TwitterDMService extends Service {

    /**
     * Consumer Key (API Key)	AcbrOmPhO6D0dOsMjOzR5GM5g
     * Consumer Secret (API Secret)	5t6dKBMOt1V5hPHgnfiW77AtWiB43MmUDiTTmQyjKTusNALBIL
     *
     * @param args
     */
    public TwitterDMService(String[] args) {
        super(args);
    }

    public JSONObject crc_check(String crc_token) {
        String hash_digest = TwitterDMService.hmacDigest("AcbrOmPhO6D0dOsMjOzR5GM5g", crc_token, "HmacSHA256");
        return new JSONObject().put("response_token", "sha256=" + new String(Base64.getEncoder().encode(hash_digest.getBytes())));
    }

    @Override
    public JSONObject execute() {
        return null;
    }

    public static String hmacDigest(String keyString, String msg, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException e) {
        }
        return digest;
    }
}
