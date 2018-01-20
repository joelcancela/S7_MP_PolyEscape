package polytech.teamf.events;

import polytech.teamf.plugins.Plugin;

public abstract class Event implements IEvent {

    protected Plugin source;

    Event(Plugin p) {
        this.source = p;
    }

}
