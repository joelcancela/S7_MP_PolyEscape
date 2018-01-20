package polytech.teamf.events;

import polytech.teamf.plugins.Plugin;

public class StartEvent extends Event {

    public StartEvent(Plugin p) {
        super(p);
    }

    @Override
    public void fire() {
        this.source.onStartEvent();
    }

}
