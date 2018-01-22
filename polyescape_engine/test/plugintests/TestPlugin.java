package plugintests;

import polytech.teamf.events.BadResponseEvent;
import polytech.teamf.events.Event;
import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.IEvent;
import polytech.teamf.plugins.Plugin;

import java.util.Map;

public class TestPlugin extends Plugin {


    private String plain_text;
    private Plugin subTest;
    private String eventRes;
    /**
     * Shared constructor with inherited plugins
     *
     * @param name        The plugin name
     * @param description The plugin description as a short text
     */
    public TestPlugin(String name, String description, String plain_text , Plugin subTest) {
        super(name, description);
        this.plain_text= plain_text;

        if(subTest != null)
        this.addPlugin(subTest);
    }

    @Override
    public void onBadResponseEvent() {
        this.eventRes = "bad response received from father";
    }

    @Override
    public void onGoodResponseEvent() {
        this.eventRes = "good response received from father";
    }

    @Override
    public void onStartEvent() {

    }

    @Override
    public void onEndEvent() {

    }

    @Override
    public Event execute(Map<String, Object> args) throws Exception {

        if (!args.containsKey("attempt")) {
            throw new IllegalArgumentException("Bad response format");
        }

        String response = (String) args.get("attempt");
        response = response.toUpperCase();

        if (this.plain_text.equalsIgnoreCase(response)) {
            return new GoodResponseEvent(this);
        }
        return new BadResponseEvent(this);
    }


    public String getEventRes() {
        return eventRes;
    }

}
