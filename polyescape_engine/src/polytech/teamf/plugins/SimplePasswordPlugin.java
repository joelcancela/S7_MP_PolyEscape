package polytech.teamf.plugins;

import org.json.JSONObject;
import polytech.teamf.resources.AnswerResource;

public class SimplePasswordPlugin extends Plugin {

    /**
     * The original plain text. Is used by the validation process.
     */
    private String plain_text = "";

    /**
     * Default constructor
     * Used by the API Reflection Engine
     */
    public SimplePasswordPlugin() {
        this("", "");
    }

    /**
     * Initializes the plugin
     *
     * @param description The plugin description
     * @param plain_text  The plain text to discover
     */
    public SimplePasswordPlugin(String description, String plain_text) {

        super(description, "Epreuve mot de passe simple");

        // ARGS
        super.getArgs().add("plain_text");
        // SCHEMA
        this.schema.put("attempt", "The user attempt");

        // FORM
        this.plain_text = plain_text;
        this.ans_format = "text";
    }

    @Override
    public AnswerResource play(AnswerResource guess) {
        guess.setSuccess(false);
        isSuccess = false;

        try {
            if (this.plain_text.equals(guess.getAttempt())) {
                this.isValidatedState = true;
                isSuccess = true;
                guess.setSuccess(true);
                return guess;
            }
        } catch (Exception ignored) {

        }

        return guess;
    }

    public String toString() {
        return new JSONObject()
                .put("name", this.getName())
                .put("description", this.getDescription())
                .put("plain_text", this.plain_text)
                .put("answer_format", this.getAns_format())
                .put("use_remote_service", false).toString();
    }
}



