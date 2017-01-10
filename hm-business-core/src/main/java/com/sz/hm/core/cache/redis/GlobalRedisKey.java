package com.sz.hm.core.cache.redis;

/**
 * 全局 redis key
 */
public enum GlobalRedisKey {
	
	Key_GAME("game"),
	Key_CROSS("cross"),
	Key_GC("game-cross"),
	
	
	CHANNEL_CROSS("channel-cross"),
	CHANNEL_GAME("channel-game"),
	/** 游戏连接跨服的channel */
	CHANNEL_GAME_CROSS("channel-game-cross"),
	
	/** coopbase信息的key */
	KEY_GATE_CB("gate-coopbase"),
	CHANNEL_GATE_CB("channel-gate-coop-base"),
	
	/**开启防沉迷*/
	KEY_GATE_OPEN_CHENMI("gate-open-chenmi"),
	/** 防沉迷变化channel */
	CHANNEL_GATE_CHENMI("channel-gate-chenmi"),
	/** 防沉迷删除channel */
	CHANNEL_GATE_CHENMI_DEL("channel-gate-chenmi-del"),
	
	
	/**客户端地址的key */
	KEY_CLIENT_URL("gate-client-url"),
	/** 客户端地址的channel */
	CHANNEL_CLIENT_URL("channel-gate-client-url"),
	CHANNEL_CLIENT_URL_DEL("channel-gate-client-url-del"),
	
	/** 转化率开启 */
	KEY_CONVERSION("gate-conversion"),
	CHANNEL_CONVERSION("channel-gate-conversion")
	;
	
	public static GlobalRedisKey convert(String key){
		
		for(GlobalRedisKey redisKey : values()){
			if( redisKey.sign.equals(key) ){
				return redisKey;
			}
		}
		return Key_GAME;
	}
	
	private String sign;
	GlobalRedisKey(String sign){
		this.sign = sign;
	}
	
	public String getKey(){
		return sign;
	}
}