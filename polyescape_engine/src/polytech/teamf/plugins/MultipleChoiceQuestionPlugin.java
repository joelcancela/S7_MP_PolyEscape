package polytech.teamf.plugins;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class MultipleChoiceQuestionPlugin extends Plugin {

    /**
     * ALl possible answers of the question
     */
    private String[] answers;

    /**
     * Correct correct_answers of the question
     */
    private String[] correct_answers;

    /**
     * Default constructor
     * Used by the API Reflection Engine
     */
    public MultipleChoiceQuestionPlugin() {
        this("", "", "");
    }

    /**
     * Initializes the plugin
     *
     * @param description         The plugin description
     * @param answers_csv         The list of answers as a CSV string
     * @param correct_answers_csv The list of correct answers in answers as a CSV string
     */
    public MultipleChoiceQuestionPlugin(String description, String answers_csv, String correct_answers_csv) {
        super(description, "Epreuve QCM");

        // ARGS
        super.getArgs().add("answers_csv");
        super.getArgs().add("correct_answers_csv");
        // SCHEMA
        this.schema.put("attempt_answers_csv", "The user answers as a CSV string");

        // FORM
        this.answers = answers_csv.split(",");
        this.correct_answers = correct_answers_csv.split(",");
        this.ans_format = "QCM";
    }

    @Override
    public JSONObject play(JSONObject args) {

        JSONObject ret = new JSONObject();

        try {
            String[] attempt_answers = args.getString("attempt").split(",");

            if (Arrays.equals(this.correct_answers, attempt_answers)) {
                this.isValidatedState = true;
                ret.put(SUCCESS, "true");
                isSuccess = "true";

            } else {
                ret.put(SUCCESS, "false");
                isSuccess = "false";

            }
        } catch (Exception e) {
            ret.put(SUCCESS, "false");
            isSuccess = "false";

        }

        return ret;
    }

    public String toString() {
        JSONArray ans = new JSONArray();
        for (String s : this.answers) {
            ans.put(s);
        }
        JSONArray corr = new JSONArray();
        for (String s : this.correct_answers) {
            corr.put(s);
        }
        return new JSONObject()
                .put("name", this.getName())
                .put("description", this.getDescription())
                .put("answers", ans)
                .put("correct_answers", corr)
                .put("answer_format", this.getAns_format()).toString();
    }
}
