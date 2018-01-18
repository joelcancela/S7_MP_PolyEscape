package polytech.teamf.events;

import java.util.EventListener;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 */
public interface PluginEventListener extends EventListener{
	public void onBadResponseGiven();
	public void onGoodResponseGiven();
	public void onStartingStep();
	public void onEndingStep();

}
