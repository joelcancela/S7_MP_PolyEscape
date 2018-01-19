package polytech.teamf.services;

import org.json.JSONArray;
import org.json.JSONObject;
import polytech.teamf.api.ServiceManager;
import polytech.teamf.game_engine.Runner;
import polytech.teamf.resources.AnswerResource;

public class PolyescapeEmailSpyService extends Service {

    private String runnerInstance;

    private String message;

    public PolyescapeEmailSpyService(String[] args) {

        super(args);

        if (args.length > 2) {
            throw new IllegalArgumentException();
        }

        this.message = new JSONArray(args[0])
                .getJSONObject(0)
                .getString("body_plain")
                .split("\n")[0]
                .trim();

        runnerInstance = args[1];
    }

    @Override
    public JSONObject execute() {
        Runner r = ServiceManager.runnersInstances.get(runnerInstance);
        r.sendMessage(new AnswerResource(this.message));
        return new JSONObject(); // Useless return value
    }
}
