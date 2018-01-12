package main.plugins;

import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.*;

public class CaesarCipherPlugin extends Plugin {

    /**
     * Initialization arguments
     */
    static {
        // Constructor arguments
        CaesarCipherPlugin.ARGS.add("plain_text");
        CaesarCipherPlugin.ARGS.add("cipher_padding");

        // Schema arguments
        CaesarCipherPlugin.JSON_SCHEMA.put("attempt_text", "the user attempt");
    }

    /**
     * The ciphered text as returned by the private method toCaesar()
     */
    private String ciphered_text = "";

    /**
     * The original plain text. Is used by the validation process.
     */
    private String plain_text = "";

    /**
     * Caesar cipher table
     */
    private List<Character> alpha_beta_array = new ArrayList<>();

    /**
     * Initializes the plugin
     *
     * @param plain_text The plain text to discover
     * @param cipher_padding The cipher padding used by the algorithm to shift letters
     */
    CaesarCipherPlugin(String plain_text, int cipher_padding) {
        this.plain_text = plain_text;

        this.alpha_beta_array.add('A');
        this.alpha_beta_array.add('B');
        this.alpha_beta_array.add('C');
        this.alpha_beta_array.add('D');
        this.alpha_beta_array.add('E');
        this.alpha_beta_array.add('F');
        this.alpha_beta_array.add('G');
        this.alpha_beta_array.add('H');
        this.alpha_beta_array.add('I');
        this.alpha_beta_array.add('J');
        this.alpha_beta_array.add('K');
        this.alpha_beta_array.add('L');
        this.alpha_beta_array.add('M');
        this.alpha_beta_array.add('N');
        this.alpha_beta_array.add('O');
        this.alpha_beta_array.add('P');
        this.alpha_beta_array.add('Q');
        this.alpha_beta_array.add('R');
        this.alpha_beta_array.add('S');
        this.alpha_beta_array.add('T');
        this.alpha_beta_array.add('U');
        this.alpha_beta_array.add('V');
        this.alpha_beta_array.add('W');
        this.alpha_beta_array.add('X');
        this.alpha_beta_array.add('Y');
        this.alpha_beta_array.add('Z');

        this.ciphered_text = toCaesar(plain_text, cipher_padding);
    }

    public void readIn(Map<String, String> args) {

        if (!args.containsKey("attempt_text")) {
            throw new InvalidParameterException();
        }

        if(this.plain_text.equals(args.get("attempt_text"))) {
            this.isValidatedState = true;
        }
    }

    public Map<String, String> checkOut() {

        Map<String, String> ret = new HashMap<String, String>();

        if (this.isValidatedState) {
            ret.put("success", "true");
        }
        else {
            ret.put("success", "false");
        }

        return ret;
    }

    public String toString() {
        return new JSONObject()
                .put("ciphered_text", this.ciphered_text)
                .put("plain_text", this.plain_text).toString();
    }

    private String toCaesar(String plain_text, int cipher_padding) {

        cipher_padding = Math.abs(cipher_padding) % 26;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < plain_text.length(); i++) {
            int index = this.alpha_beta_array.indexOf(plain_text.charAt(i)) + cipher_padding;
            builder.append(this.alpha_beta_array.get(index));
        }

        return builder.toString();
    }
}
