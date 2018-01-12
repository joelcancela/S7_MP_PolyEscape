package polytech.teamf.plugins;

import java.util.Map;

public class PluginFactory {

    public static Plugin create(String className, Map<String, String> args) {

        Plugin plugin = null;

        if (className.equals("CaesarCipherPlugin")) {
            plugin = new CaesarCipherPlugin(args.get("description"), args.get("plain_text"), Integer.parseInt(args.get("cipher_padding")));
        }
        if (className.equals("MultipleChoiceQuestionPlugin")) {
            plugin = new MultipleChoiceQuestionPlugin(args.get("description"), args.get("attempt_answers_csv"));
        }

        return plugin;
    }
}
