package polytech.teamf.plugins;

import org.json.JSONObject;
import java.util.Arrays;

public class MultipleChoiceQuestionPlugin extends Plugin {

    /**
     * Correct answers of the question
     */
    private String[] answers;

    /**
     * Default constructor
     * Used by the API Reflection Engine
     */
    public MultipleChoiceQuestionPlugin() {
        super();
        // ARGS
        super.getArgs().add("answers_csv");
        // SCHEMA
        this.schema.put("attempt_answers_csv", "The user answers as a CSV string");
    }

    /**
     * Initializes the plugin
     *
     * @param description The plugin description
     * @param answers_csv The list of answers as a CSV string
     */
    MultipleChoiceQuestionPlugin(String description, String answers_csv) {
        super(description);
        this.answers = answers_csv.split(",");
    }

    @Override
    public JSONObject play(JSONObject args) {

        JSONObject ret = new JSONObject();

        try {
            String[] attempt_answers = args.getString("attempt_answers_csv").split(",");

            if (Arrays.equals(this.answers, attempt_answers)) {
                this.isValidatedState = true;
                ret.put(SUCCESS, "true");
            } else {
                ret.put(SUCCESS, "false");
            }
        } catch (Exception e) {
            ret.put(SUCCESS, "false");
        }

        return ret;
    }

    public String toString() {
        return new JSONObject()
                .put("description", this.description).toString();
    }
}
