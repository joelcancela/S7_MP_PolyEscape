package polytech.teamf.plugins;

import polytech.teamf.events.IEvent;

import java.util.Map;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 */
public class GoogleSheetsPlugin extends Plugin{


    public GoogleSheetsPlugin() {
        super("GoogleSheetsPlugin", "");
        args.put("url", "String");
    }

    @Override
    public IEvent execute(Map<String, Object> args) throws Exception {
        //        String description = "";
//        String answers_csv = "";
//        String correct_answers_csv = "";
//
//
//            // CALL THE SERVICE INSTEAD OF DOING TMP FILES
//            // Read as CSV and populate vars
//            String line;
//            BufferedReader br = new BufferedReader(new FileReader(gsheetFile));
//
//            for (int i = 0; i < 4; i++) {
//
//                line = br.readLine();
//                if (line == null) {
//                    break;
//                }
//
//                String[] parts = line.replace('"', ' ').split(","); // use comma as separator
//
//                StringBuilder argBuilder = new StringBuilder();
//                for (int j = 1; j < parts.length; j++) {
//                    if (parts[j].trim().isEmpty()) {
//                        continue;
//                    }
//                    argBuilder.append(parts[j].trim());
//                    argBuilder.append(",");
//                }
//                String arg = argBuilder.toString();
//                if (parts.length > 1) {
//                    arg = arg.substring(0, arg.length() - 1); // Remove last comma
//                }
//
//                if (parts[0].trim().equals("Question")) {
//                    description = arg;
//                }
//                if (parts[0].trim().equals("Answers")) {
//                    answers_csv = arg;
//                }
//                if (parts[0].trim().equals("Correction")) {
//                    // Correction
//                    correct_answers_csv = arg;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return super.execute(new MultipleChoiceQuestionPlugin(description, answers_csv, correct_answers_csv));
        return null;
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
