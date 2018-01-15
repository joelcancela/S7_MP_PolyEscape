package polytech.teamf.services;

import org.json.JSONObject;

public abstract class Service implements IService {

    Service(String[] args) {
    }

    protected JSONObject execute(Object o) {
        return new JSONObject(o.toString());
    }
}
