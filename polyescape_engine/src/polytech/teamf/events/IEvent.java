package polytech.teamf.events;

import polytech.teamf.plugins.Plugin;

public interface IEvent {

    /**
     * Triggers the plugin method as a Command Pattern.
     */
    void fire();
    void setSrouce(Plugin aSource);

}