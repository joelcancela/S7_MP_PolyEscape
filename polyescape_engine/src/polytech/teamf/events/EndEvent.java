package polytech.teamf.events;

import polytech.teamf.plugins.Plugin;

public class EndEvent extends Event {

    public EndEvent(Plugin p) {
        super(p);
    }

    @Override
    public void fire() {
        this.source.onEndEvent();
    }

}
