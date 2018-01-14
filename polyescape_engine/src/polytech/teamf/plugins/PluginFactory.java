package polytech.teamf.plugins;

import org.json.JSONObject;

public class PluginFactory {

    public static Plugin create(String className, JSONObject args) {

        Plugin plugin = null;

        if (className.equals("CaesarCipherPlugin")) {
            plugin = new CaesarCipherPlugin(
                    args.getString("description"),
                    args.getString("plain_text"),
                    args.getInt("cipher_padding")
            );
        }
        if (className.equals("MultipleChoiceQuestionPlugin")) {
            plugin = new MultipleChoiceQuestionPlugin(
                    args.getString("description"),
                    args.getString("attempt_answers_csv")
            );
        }

        return plugin;
    }
}
