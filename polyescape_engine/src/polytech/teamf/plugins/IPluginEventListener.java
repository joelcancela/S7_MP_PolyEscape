package polytech.teamf.plugins;

import polytech.teamf.events.BadResponseEvent;
import polytech.teamf.events.EndEvent;
import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.StartEvent;

import java.util.EventListener;

public interface IPluginEventListener extends EventListener{
	void on(BadResponseEvent e);
	void on(GoodResponseEvent e);
	void on(StartEvent e);
	void on(EndEvent e);
}
