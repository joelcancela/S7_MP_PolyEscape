package polytech.teamf.events;

import polytech.teamf.plugins.Plugin;

public class BadResponseEvent extends Event {

    public BadResponseEvent(Plugin p) {
        super(p);
    }

    @Override
    public void fire() {
        this.source.onBadResponseEvent();
    }

}
