package com.falconsocial.websockets.message;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/messageListener")
public class Message {
	private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session peer) {
		System.out.println("onOpen");
		sessions.add(peer);
	}

	@OnClose
	public void onClose(Session peer) {
		System.out.println("onClose");
		sessions.remove(peer);
	}

	public static void pushJSONMessageToAllSessions(String jsonMessage) {
		for (Session session : sessions) {
			if (session.isOpen()) {
				try {
					System.out.println("Sending message: " + jsonMessage + " to client with ID:" + session.getId());
					session.getBasicRemote().sendText(jsonMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}