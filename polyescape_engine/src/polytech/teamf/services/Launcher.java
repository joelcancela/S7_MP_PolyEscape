package polytech.teamf.services;

import org.json.JSONObject;

public class Launcher {

    public static void main(String[] args)
    {
        GoogleSheetsService s = new GoogleSheetsService(args);

        JSONObject res = s.execute();

        System.out.println(res.toString());
    }
}
