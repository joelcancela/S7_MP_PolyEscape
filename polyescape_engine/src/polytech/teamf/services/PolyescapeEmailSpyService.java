package polytech.teamf.services;

import org.json.JSONObject;
import polytech.teamf.api.ServiceManager;
import polytech.teamf.game_engine.Runner;

public class PolyescapeEmailSpyService extends Service {

    private JSONObject message = new JSONObject();

    public PolyescapeEmailSpyService(String[] args) {

        super(args);

        if (args.length != 1) {
            throw new IllegalArgumentException();
        }
        JSONObject complete_message = new JSONObject(args[0]);

        System.err.println(complete_message.toString());

        // Push to message after format
    }

    @Override
    public JSONObject execute() {
        Runner r = ServiceManager.getRunnerInstance(null);
        r.sendMessage(this.message);
        return new JSONObject(); // Useless return value
    }
}
