package polytech.teamf.plugins;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

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
     * Caesar cipher table
     */
    private List<Character> alpha_beta_array = new ArrayList<>();

    /**
     * Default constructor
     * Used by the API Reflection Engine
     */
    public CaesarCipherPlugin() {
        this("", "", 0);
    }

    /**
     * Initializes the plugin
     *
     * @param description The plugin description
     * @param plain_text The plain text to discover
     * @param cipher_padding The cipher padding used by the algorithm to shift letters
     */
    public CaesarCipherPlugin(String description, String plain_text, int cipher_padding) {

        super(description, "Epreuve code Caesar");

        // ARGS
        super.getArgs().add("plain_text");
        super.getArgs().add("cipher_padding");
        // SCHEMA
        this.schema.put("attempt_text", "The user attempt");

        // FORM
        this.plain_text = plain_text;
        this.ans_format = "text";


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

        this.ciphered_text = toCaesar(plain_text.toUpperCase(), cipher_padding);
    }

    @Override
    public JSONObject play(JSONObject args) {

        JSONObject ret = new JSONObject();

        try {
            if (this.plain_text.equals(args.getString("attempt").toUpperCase())) {
                this.isValidatedState = true;
                ret.put(SUCCESS, "true");
            }
            else {
                ret.put(SUCCESS, "false");
            }
        }
        catch (Exception e) {
            ret.put(SUCCESS, "false");
        }

        return ret;
    }

    public String toString() {
        return new JSONObject()
                .put("name", this.getName())
                .put("description", this.getDescription() + " " + this.ciphered_text)
                .put("plain_text", this.plain_text)
                .put("ciphered_text", this.ciphered_text)
                .put("answer_format",this.getAns_format() ).toString();
    }

    private String toCaesar(String plain_text, int cipher_padding) {
        cipher_padding = Math.abs(cipher_padding) % 26;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < plain_text.length(); i++) {
            char c = plain_text.charAt(i);
            if (this.alpha_beta_array.indexOf(c) == -1) {
                continue;
            }
            int index = this.alpha_beta_array.indexOf(c) + cipher_padding;
            index = index % 26;
            builder.append(this.alpha_beta_array.get(index));
        }
        return builder.toString();
    }

    @Override
    public String getDescription(){
        return  super.getDescription() + " " + this.ciphered_text;
    }
}
