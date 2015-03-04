package com.falconsocial.webservices.message.simple;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;

import com.falconsocial.redis.util.RedisConfigProvider;
import com.falconsocial.websockets.message.Message;

@Path("/postJSONmessage")
public class PostJSONMessage {
	Jedis jedis;
	
	public PostJSONMessage() {
		this.jedis = new Jedis(RedisConfigProvider.getHostName());
	}
	
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  public String postMessage(String jsonMessage) throws JSONException {
  	System.out.println("/postJSONmessage Message received: " + jsonMessage);
  	try {
  		createJSONObject(jsonMessage);
		} catch (JSONException jsonException) {
			jsonException.printStackTrace();
			return "ERROR: " + jsonException.getMessage();
		}
  	publishJSONMessage(jsonMessage);
  	Message.pushJSONMessageToAllSessions(jsonMessage);
  	return "Message posted";
  }

	private void publishJSONMessage(String jsonMessage) {
		try {
			jedis.publish(RedisConfigProvider.getJsonChannelName(), jsonMessage);
	  	System.out.println("/postJSONmessage Message published: " + jsonMessage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

  private void createJSONObject(String jsonMessage) throws JSONException {
		new JSONObject(jsonMessage);
  }

} 