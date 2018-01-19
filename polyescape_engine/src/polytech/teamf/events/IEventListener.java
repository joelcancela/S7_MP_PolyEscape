package polytech.teamf.events;

import java.util.EventListener;

public interface IEventListener extends EventListener{

	void onBadResponseEvent();
	void onGoodResponseEvent();
	void onStartEvent();
	void onEndEvent();
}
