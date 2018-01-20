package polytech.teamf.events;

import polytech.teamf.plugins.Plugin;

public class GoodResponseEvent extends Event {

    public GoodResponseEvent(Plugin p) {
        super(p);
    }

    @Override
    public void fire() {
        this.source.onGoodResponseEvent();
    }

}
