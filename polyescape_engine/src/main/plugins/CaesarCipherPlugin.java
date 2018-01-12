package main.plugins;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CaesarCipherPlugin extends Plugin {

    /**
     * The ciphered text as returned by the private method toCaesar()
     */
    private String ciphered_text = "";

    /**
     * The original plain text. Is used by the validation process.
     */
    private String plain_text = "";

    /**
     * Initializes the plugin
     *
     * @param plain_text The plain text to discover
     * @param cipher_padding The cipher padding used by the algorithm to shift letters
     */
    CaesarCipherPlugin(String plain_text, int cipher_padding) {
        this.plain_text = plain_text;
        this.ciphered_text = toCaesar(cipher_padding);
    }

    public List<String> listArgs() {

        // TODO : implement in abstract class with javadoc parser

        List<String> args = new LinkedList<String>();
        args.add("plain_text");
        args.add("cipher_padding");

        return args;
    }

    public void readIn(Map<String, String> args) {

    }

    public Map<String, String> checkOut() {
        return null;
    }

    public String toJSONString() {
        return null;
    }

    private String toCaesar(int pad) {
        return "";
    }
}
