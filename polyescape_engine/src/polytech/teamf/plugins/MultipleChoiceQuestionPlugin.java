package polytech.teamf.plugins;

import polytech.teamf.events.BadResponseEvent;
import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.IEvent;
import polytech.teamf.services.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MultipleChoiceQuestionPlugin extends Plugin {

    /**
     * All answers of the question
     */
    private String[] answers;

    /**
     * Correct correct_answers of the question
     */
    private String[] correct_answers;

    /**
     * Initializes the plugin
     *
     * @param description         The plugin description
     * @param answers_csv         The list of answers as a CSV string
     * @param correct_answers_csv The list of correct answers in answers as a CSV string
     */
    public MultipleChoiceQuestionPlugin(String description, String answers_csv, String correct_answers_csv) {
        super(description, "Epreuve QCM");

        // INNER MODEL
        this.answers = answers_csv.split(",");
        this.correct_answers = correct_answers_csv.split(",");

        // SHARED MODEL
        this.putAttribute("answers", this.answers);
    }

    @Override
    public IEvent execute(Map<String, Object> args) throws Exception {

        if (!args.containsKey("attempt_csv")) {
            throw new IllegalArgumentException("Bad response format");
        }

        String[] attempt_answers = args.get("attempt_csv").toString().split(",");
        if (Arrays.equals(this.correct_answers, attempt_answers)) {
            return new GoodResponseEvent(this);
        }
        return new BadResponseEvent(this);
    }

    @Override
    public void onBadResponseEvent() {

    }

    @Override
    public void onGoodResponseEvent() {

    }

    @Override
    public void onStartEvent() {

    }

    @Override
    public void onEndEvent() {

    }
}
