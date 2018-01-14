package polytech.teamf.plugins;

import org.json.JSONObject;
import polytech.teamf.services.GoogleSheetsService;

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

            // TODO : Maybe the service must be called BEFORE this factory
            // TODO : add new abstraction layer the call to THIS method

            if (args.has("gsheets_url")) {
                plugin = new GoogleSheetsService().buildMultipleChoiceQuestionPlugin(
                        args.getString("gsheets_url")
                );
            } else {
                plugin = new MultipleChoiceQuestionPlugin(
                        args.getString("description"),
                        args.getString("answers_csv"),
                        args.getString("correct_answers_csv")
                );
            }
        }

        return plugin;
    }
}
