package polytech.teamf.services;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import polytech.teamf.plugins.MultipleChoiceQuestionPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class GoogleSheetsService extends Service {

    /**
     * Google Sheet URL
     */
    private final String gsheets_url;

    public GoogleSheetsService(String[] args) {
        super(args);

        if (args.length != 1) {
            throw new IllegalArgumentException();
        }
        this.gsheets_url = args[0];
    }

    public JSONObject execute() {

        String description = "";
        String answers_csv = "";
        String correct_answers_csv = "";

        // Build plugin given an URL
        try {
            File gsheetFile = File.createTempFile("gsheet", ".tmp");
            FileUtils.copyURLToFile(
                    new URL(gsheets_url),
                    gsheetFile
            );

            // Read as CSV and populate vars
            String line;
            BufferedReader br = new BufferedReader(new FileReader(gsheetFile));

            for (int i = 0; i < 4; i++) {

                line = br.readLine();
                if (line == null) {
                    break;
                }

                String[] parts = line.replace('"', ' ').split(","); // use comma as separator

                StringBuilder argBuilder = new StringBuilder();
                for (int j = 1; j < parts.length; j++) {
                    if (parts[j].trim().isEmpty()) {
                        continue;
                    }
                    argBuilder.append(parts[j].trim());
                    argBuilder.append(",");
                }
                String arg = argBuilder.toString();
                if (parts.length > 1) {
                    arg = arg.substring(0, arg.length() - 1); // Remove last comma
                }

                if (parts[0].trim().equals("Question")) {
                    description = arg;
                }
                if (parts[0].trim().equals("Answers")) {
                    answers_csv = arg;
                }
                if (parts[0].trim().equals("Correction")) {
                    // Correction
                    correct_answers_csv = arg;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.execute(new MultipleChoiceQuestionPlugin(description, answers_csv, correct_answers_csv));
    }




}
