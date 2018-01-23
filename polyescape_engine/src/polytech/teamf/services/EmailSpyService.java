package polytech.teamf.services;

import org.json.JSONArray;
import polytech.teamf.api.InstanceHolder;
import polytech.teamf.gameengine.Runner;

import java.util.HashMap;
import java.util.Map;

public class EmailSpyService extends Service {

    public EmailSpyService() {
        this.name = "Service de Mail Zapier";
        this.inputService = true;
        this.serviceHost = "https://zapier.com/";
    }

    @Override
    public String call(Map<String, Object> callArgs) {

        String message = ((JSONArray)callArgs.get("msg"))
                .getJSONObject(0)
                .getString("body_plain")
                .split("\n")[0]
                .trim();

        String instanceUID = ((JSONArray)callArgs.get("msg"))
                .getJSONObject(0)
                .getString("subject")
                .split("\n")[0]
                .trim();

        // Resolve runner instance
        Runner r = InstanceHolder.getInstance().getRunnerInstance(instanceUID);

        // Send Msg
        Map<String, Object> msg = new HashMap<>();
        msg.put("attempt", message);
        r.sendMessage(msg);

        // End
        return "200 OK";
    }
}
