package polytech.teamf.services;

import org.json.JSONArray;
import org.json.JSONObject;
import polytech.teamf.api.ServiceManager;
import polytech.teamf.game_engine.Runner;

public class PolyescapeEmailSpyService extends Service {

    private String message;

    public PolyescapeEmailSpyService(String[] args) {

        super(args);

        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        this.message = new JSONArray(args[0])
                .getJSONObject(0)
                .getString("body_plain")
                .split("\n")[0]
                .trim();
    }

    @Override
    public JSONObject execute() {
        Runner r = ServiceManager.getRunnerInstance(null);
        r.sendMessage(new JSONObject().put("attempt", this.message));
        return new JSONObject(); // Useless return value
    }
}
