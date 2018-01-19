package polytech.teamf.plugins;

import java.util.Map;

public class PluginFactory {

    private static final String DESCRIPTION = "description";
    private static final String PLAIN_TEXT = "plain_text";

    public static Plugin create(String className, Map<String, Object> args) {

        Plugin plugin = null;

        if (className.equals("CaesarCipherPlugin")) {

            plugin = new CaesarCipherPlugin(
                    (String) args.get(DESCRIPTION),
                    (String) args.get(PLAIN_TEXT),
                    (int) args.get("cipher_padding")
            );
        }

        if (className.equals("EmailSpyPlugin")) {

            plugin = new EmailSpyPlugin(
                    (String) args.get(DESCRIPTION),
                    (String) args.get(PLAIN_TEXT)
            );
        }

        if (className.equals("SimplePasswordPlugin")) {

            plugin = new SimplePasswordPlugin(
                    (String) args.get(DESCRIPTION),
                    (String) args.get(PLAIN_TEXT));
        }

        if (className.equals("MultipleChoiceQuestionPlugin")) {

            plugin = new MultipleChoiceQuestionPlugin(
                    (String) args.get(DESCRIPTION),
                    (String) args.get("answers_csv"),
                    (String) args.get("correct_answers_csv")
            );
        }

        return plugin;
    }

}
