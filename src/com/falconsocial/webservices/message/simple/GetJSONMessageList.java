package com.falconsocial.webservices.message.simple;

import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import redis.clients.jedis.Jedis;

import com.falconsocial.redis.util.RedisConfigProvider;

@Path("/jsonmessagelist")
public class GetJSONMessageList {
	Jedis jedis;

	@GET
	@Path("/asHTML")
	@Produces(MediaType.TEXT_HTML)
	public String getJSONmessagelistAsHTML(@DefaultValue("localhost") @QueryParam("host") String host, @DefaultValue("6379") @QueryParam("port") int port) {
		init(host, port);
		StringBuilder htmlJSONMessageListPage = new StringBuilder();
		htmlJSONMessageListPage.append("<html><title>Falcon Social - JSON Message List</title>");
		htmlJSONMessageListPage.append("<body><h1>JSON Message List</h1>");
		htmlJSONMessageListPage.append("<p>Host: " + host + ":" + port + "</p>");
		htmlJSONMessageListPage.append("<table border=1><tr><th>Key</th><th>JSON Message</th></tr>");
		Set<String> jsonMessageKeys = jedis.keys(RedisConfigProvider.getJsonMessageKey() + ":*");
		for (String jsonMessageKey : jsonMessageKeys) {
			htmlJSONMessageListPage.append("<tr><td>" + jsonMessageKey + "</td>");
			String jsonMessage = jedis.get(jsonMessageKey);
			htmlJSONMessageListPage.append("<td>" + jsonMessage + "</td></tr>");
		}
		htmlJSONMessageListPage.append("</table>");
		htmlJSONMessageListPage.append("</body></html>");

		return htmlJSONMessageListPage.toString();
	}

	@GET
	@Path("/asJSON")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSONMessageList(@DefaultValue("localhost") @QueryParam("host") String host, @DefaultValue("6379") @QueryParam("port") int port)
			throws JSONException {
		init(host, port);
		StringBuilder jsonMessageList = new StringBuilder().append("[");
		Set<String> jsonMessageKeys = jedis.keys(RedisConfigProvider.getJsonMessageKey() + ":*");
		for (String jsonMessageKey : jsonMessageKeys) {
			if (jsonMessageList.length() != 1) {
				jsonMessageList.append(",");
			}
			jsonMessageList.append(jedis.get(jsonMessageKey));
		}
		jsonMessageList.append("]");

		return jsonMessageList.toString();
	}

	void init(String host, int port) {
		this.jedis = new Jedis(host, port);
	}

}