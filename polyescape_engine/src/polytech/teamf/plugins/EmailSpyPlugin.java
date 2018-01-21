package polytech.teamf.plugins;

import polytech.teamf.events.BadResponseEvent;
import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.IEvent;

import java.util.Map;

public class EmailSpyPlugin extends Plugin {

	/**
	 * The original plain text.
	 * Is used by the validation process.
	 */
	private String plain_text = "";

	/**
	 * Initializes the plugin.
	 *
	 * @param description the plugin description
	 * @param plain_text  the plain text to discover
	 */
	public EmailSpyPlugin(String description, String plain_text) {

		super("EmailSpyPlugin", description + "<br>Envoyez votre réponse à cet email : <a href=\"mailto:polyescape.olw5ew@zapiermail.com\">polyescape.olw5ew@zapiermail.com</a>"
		);

		// MODEL
		this.plain_text = plain_text;
	}

	public EmailSpyPlugin() {
		super("EmailSpyPlugin", "");
		args.put("description", "String");
		args.put("plain_text", "String");
	}

	@Override
	public IEvent execute(Map<String, Object> args) throws Exception {

		if (!args.containsKey("attempt")) {
			throw new IllegalArgumentException("Bad response format");
		}

		String response = (String) args.get("attempt");

		if (this.plain_text.equals(response)) {
			return new GoodResponseEvent(this);
		}

		return new BadResponseEvent(this);
	}

	@Override
	public void onBadResponseEvent() {

	}

	@Override
	public void onGoodResponseEvent() {

	}

	@Override
	public void onStartEvent() {

	}

	@Override
	public void onEndEvent() {

	}

}
