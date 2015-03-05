package com.falconsocial.clients;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class CreateJSONMessage {
	static String endpointURL = "http://localhost:8080/falconsocialMessage/rest/postJSONmessage";

	static String jsonMessage = "{\"message\": \"This is a dummy JSON message\"}";

	public static void main(String[] args) throws IOException {
		System.out.println("Starting CreateJSONMessage. Execute with parameter -? for help.");
		if (init(args)) {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(getBaseURI());
			String response = target.request().accept(MediaType.TEXT_PLAIN).post(Entity.json(jsonMessage), String.class);
			System.out.println(response);
		}

	}

	static boolean init(String[] args) {
		if (args.length > 0 && "-?".equals(args[0])) {
			System.out.println("Sends a JSON message to a REST endpoint.");
			System.out.println("Parameter [1] is the JSON message. Current default JSON message:");
			System.out.println(jsonMessage);
			System.out.println("Parameter [2] is the REST endpoint URL. Current default REST endpoint URL:");
			System.out.println(endpointURL);
			return false;
		}
		if (args.length > 0 && args[0] != null) {
			jsonMessage = args[0];
		}
		if (args.length > 0 && args[1] != null) {
			endpointURL = args[1];
		}
		return true;
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(endpointURL).build();
	}
}
