package polytech.teamf.plugins;

import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, String> play(Map<String, String> args) throws Exception {
        Map<String, String> ret = new HashMap<>();

        if (!args.containsKey("attempt_answers_csv")) {
            throw new InvalidParameterException();
        }

        String[] attempt_answers = args.get("attempt_answers_csv").split(",");
        if (Arrays.equals(this.answers, attempt_answers)) {
            this.isValidatedState = true;
            ret.put("success", "true");
        }
        else {
            ret.put("success", "false");
        }

        return ret;
    }

    public String toString() {
        return new JSONObject()
                .put("description", this.description).toString();
    }
}
