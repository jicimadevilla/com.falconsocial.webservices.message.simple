package com.falconsocial.redis.subscribers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.falconsocial.redis.util.DataProvider;
import com.falconsocial.redis.util.RedisConfigProvider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class JSONSubscriber extends JedisPubSub implements ServletContextListener, Runnable {
	JedisPoolConfig poolConfig = null;
	JedisPool jedisPool = null;

	public JSONSubscriber() {
	}

	@Override
	public void onMessage(String channel, String jsonMessage) {
		String jsonMessageKey = RedisConfigProvider.getJsonMessageKey() + ":" + DataProvider.getId();
		Jedis jedis = jedisPool.getResource();
		jedis.set(jsonMessageKey, jsonMessage);
		jedisPool.returnResource(jedis);
		System.out.println("JSONSubscriber Message set. Key:" + jsonMessageKey + " - Message:" + jsonMessage);
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println("Subscribing to channel: " + channel);
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println("Unsubscribing from channel: " + channel);
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		new Thread(this).start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		this.unsubscribe();
		jedisPool.destroy();
	}

	@Override
	public void run() {
		poolConfig = new JedisPoolConfig();
		jedisPool = new JedisPool(poolConfig, RedisConfigProvider.getHostName(), RedisConfigProvider.getPortNumber(), 0);
		Jedis jedis = jedisPool.getResource();
		jedis.subscribe(this, RedisConfigProvider.getJsonChannelName());
		jedisPool.returnResource(jedis);
	}

}
