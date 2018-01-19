package polytech.teamf.services;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Map;

public class GoogleSheetsService extends Service {

	public static final String SPREADSHEET_ID_KEY = "spreadsheetID";

	public GoogleSheetsService() {
		this.name = "Google Spreadsheets Service";
		this.inputService = false;
		this.serviceHost = "https://docs.google.com/spreadsheets/d/";
	}

	protected String call(Map<String, Object> callArgs) {
		String spreadsheetID = (String) callArgs.get(SPREADSHEET_ID_KEY);
		WebTarget target = client.target(serviceHost + spreadsheetID);
		Invocation.Builder builder = target.request();
		builder.accept(MediaType.TEXT_PLAIN);
		return builder.get(String.class);
	}
}
