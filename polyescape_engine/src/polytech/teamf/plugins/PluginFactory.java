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
<<<<<<< HEAD
        if (className.equals("EmailSpyPlugin")) {

            plugin = new EmailSpyPlugin(
                    args.getString("description"),
                    args.getString("plain_text")
            );
        }
=======

>>>>>>> 72bea348f12283d05e434aefaf545ddbac678be5
        if (className.equals("SimplePasswordPlugin")) {

            plugin = new SimplePasswordPlugin(
                    args.getString("description"),
                    args.getString("plain_text"));
        }

        if (className.equals("MultipleChoiceQuestionPlugin")) {

            plugin = new MultipleChoiceQuestionPlugin(
                    args.getString("description"),
                    args.getString("answers_csv"),
                    args.getString("correct_answers_csv")
            );
        }

        return plugin;
    }

}
