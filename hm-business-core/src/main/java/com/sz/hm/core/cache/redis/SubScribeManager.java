package com.sz.hm.core.cache.redis;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
import com.sz.hm.core.exception.ServiceException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * redis订阅管理
 */
public class SubScribeManager extends JedisPubSub implements Runnable {
	private static final Logger logger = LogManager.getLogger(SubScribeManager.class);

	private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("subscribe-redis-thread").build());
	private int times;
	private Redis redis;
	//全局事件订阅器
	private GlobalEventSubcriber gLobalEventSubcriber;

	public void init(Redis redis, GlobalEventSubcriber gLobalEventSubcriber){
		this.redis = redis;
		this.gLobalEventSubcriber = gLobalEventSubcriber;

		service.execute(this);
	}
	
	
	@Override
	public void run() {
		logger.info("SubScribeManager 进行subscribe已经重试了 {} 次",times);
		try {
			redis.subscribe(this, 
								GlobalRedisKey.CHANNEL_GAME.getKey(),
								GlobalRedisKey.CHANNEL_GATE_CB.getKey(),
								GlobalRedisKey.CHANNEL_GATE_CHENMI.getKey(),
								GlobalRedisKey.CHANNEL_GATE_CHENMI_DEL.getKey(),
								GlobalRedisKey.CHANNEL_CLIENT_URL.getKey(),
								GlobalRedisKey.CHANNEL_CLIENT_URL_DEL.getKey(),
								GlobalRedisKey.CHANNEL_CONVERSION.getKey()
								
					);
		} catch (JedisConnectionException|ServiceException e) {
			logger.error("redis连接失败，订阅线程重新订阅", e);
			service.schedule(this, 10+10*times, TimeUnit.SECONDS);
			times ++;
		} catch (Exception e) {
			logger.error("订阅线程异常退出", e);
		}
	}

	/**
	 * 取得订阅的消息后的处理
	 */
	public void onMessage(String channel, String message) {
		logger.info("取得订阅事件消息,channel={},message={}", channel, message);
		
		//监听游戏服务器启动、停止的事件，监听coopbase变化的事件
		try{
			gameSubScribeHandle(channel, message);
		}catch (Exception e) {
			logger.error("订阅到服务器状态变化事件处理出错", e);
		}
	}
	/** 游戏服务器接收到订阅事件后的处理 */
	private void gameSubScribeHandle(String channel, String message){
		switch (GlobalRedisKey.convert(channel)) {
		case CHANNEL_CROSS:
//			GameContext.getHarborClient().handleCrossEvent(message);
			logger.warn("Gate服务器还没对这个事件做处理,channel={}, message={}", channel, message);
			break;
		case CHANNEL_GAME:
			gLobalEventSubcriber.serverChanged(message);
			break;
		case CHANNEL_GAME_CROSS:
			logger.warn("Gate服务器还没对这个事件做处理,channel={}, message={}", channel, message);
			break;
		case CHANNEL_GATE_CB:
			gLobalEventSubcriber.platformChanged(message);
			break;
		case CHANNEL_GATE_CHENMI:
			gLobalEventSubcriber.cmFlagChanged(message);
			break;
		case CHANNEL_GATE_CHENMI_DEL:
			gLobalEventSubcriber.cmFlagRemoved(message);
			break;
		case CHANNEL_CLIENT_URL:
			gLobalEventSubcriber.clientUrlChanged(message);
			break;
		case CHANNEL_CLIENT_URL_DEL:
			gLobalEventSubcriber.clientUrlRemoved(message);
			break;
		case CHANNEL_CONVERSION:
			break;
		default:
			logger.warn("什么时候来了个新通道,channel={}, message={}", channel, message);
			break;
		}
	}

	/**
	 * 取得按表达式的方式订阅的消息后的处理
	 */
	public void onPMessage(String pattern, String channel, String message) {
	}
	/**
	 * 初始化按表达式的方式订阅时候的处理
	 * @param pattern 订阅的key模式
	 * @param subscribedChannels 订阅的频道序号
	 */
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
	}
	
	/**
	 * 取消按表达式的方式订阅时候的处理
	 */
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
	}
	/** 初始化订阅
	 * @param channel 订阅的频道
	 * @param channelIndex 订阅的频道序号
	 */
	@Override
	public void onSubscribe(String channel, int channelIndex) {
		logger.info("订阅的频道:channel={}, index={}", channel, channelIndex);
	}

	/**
	 * 取消订阅
	 */
	@Override
	public void onUnsubscribe(String channel, int channelIndex) {
		logger.error("取消的频道的订阅:channel={}, index={}", channel, channelIndex);
	}


	public void shutdown() {
		logger.error("关闭redis订阅线程");
		System.out.println("关闭redis订阅线程");
		this.unsubscribe();
		service.shutdownNow();
	}

}
