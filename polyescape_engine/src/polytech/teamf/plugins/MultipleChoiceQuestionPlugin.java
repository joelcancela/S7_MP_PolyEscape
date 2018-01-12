package polytech.teamf.plugins;

import org.json.JSONObject;

import java.util.Arrays;

public class MultipleChoiceQuestionPlugin extends Plugin {

    static {
        // Init
        MultipleChoiceQuestionPlugin.ARGS.add("answers_csv");

        // Schema
        MultipleChoiceQuestionPlugin.SCHEMA.put("attempt_answers_csv", "The user answers as a CSV string");
    }

    private String[] answers;

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
                ret.put("success", "true");
            }
            else {
                ret.put("success", "false");
            }
        }
        catch (Exception e) {
            ret.put("success", "false");
        }

        return ret;
    }

    public String toString() {
        return new JSONObject()
                .put("description", this.description).toString();
    }
}
