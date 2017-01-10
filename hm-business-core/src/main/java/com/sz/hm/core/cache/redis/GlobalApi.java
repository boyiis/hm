package com.sz.hm.core.cache.redis;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 全局redis读写api
 */
public class GlobalApi {

	private static final Logger logger = LogManager.getLogger();

	public GlobalApi(Redis redis){
        this.globalCache = redis;
    }

    private Redis globalCache;
	
	private static SubScribeManager scribeManager;

    /**
     * 全局事件订阅
     * @param subcriber
     */
	public void subscribe(GlobalEventSubcriber subcriber){

		scribeManager = new SubScribeManager();
		scribeManager.init(globalCache, subcriber);
		
	}

    /**获取所有coop_base中的配置*/
	public Map<String, String> getAllCoopBase(){
		return globalCache.hgetAll(GlobalRedisKey.KEY_GATE_CB.getKey());
	}

	/** 获取指定coopname 的 coop_base 配置 */
	public String getCoopBase (String field){
		return globalCache.hget(GlobalRedisKey.KEY_GATE_CB.getKey(), field);
	}
	
	/** 获取指定coopname 的 coop_chenmi 配置 */
	public String getCoopChenmi (String field){
		return globalCache.hget(GlobalRedisKey.KEY_GATE_OPEN_CHENMI.getKey(), field);
	}

	/**获取所有客户端地址的配置*/
	public Map<String, String> getAllClientUrl(){
		return globalCache.hgetAll(GlobalRedisKey.KEY_CLIENT_URL.getKey());
	}
	/** 删除客户端地址的配置 */
	public void deleteClientUrl(String sid){
		globalCache.hdel(GlobalRedisKey.KEY_CLIENT_URL.getKey(), sid);
		globalCache.publish(GlobalRedisKey.CHANNEL_CLIENT_URL_DEL.getKey(), sid);
	}
	
	/**获取测试服的地址 */
	public String getClientUrlBySid(String sid){
		
		String url = globalCache.hget(GlobalRedisKey.KEY_CLIENT_URL.getKey(), sid);
		if( url == null ){
			url = globalCache.hget(GlobalRedisKey.KEY_CLIENT_URL.getKey(), sid);
		}
		return url;
	}
	
	/** 设置测试服的客户端地址 */
	public void updateClientUrl(String sid, String url) {
		

		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(sid, url);
		globalCache.hset(GlobalRedisKey.KEY_CLIENT_URL.getKey(), sid, url);
		globalCache.publish(GlobalRedisKey.CHANNEL_CLIENT_URL.getKey(), jsonObj.toString());
	}
	
	/**
	 * 页面上存储coopbase
	 */
	public void setCoopBase(String coopname,String jsonString){
		//更新redis
		globalCache.hset(GlobalRedisKey.KEY_GATE_CB.getKey(), coopname, jsonString);
		//发布redis事件
		globalCache.publish(GlobalRedisKey.CHANNEL_GATE_CB.getKey(), jsonString);
	}
	
	
	/**获取所有coop_chenmi中的配置*/
	public Map<String, String> getAllCoopChenmi(){
		return globalCache.hgetAll(GlobalRedisKey.KEY_GATE_OPEN_CHENMI.getKey());
	}
	/**
	 * 页面上存储chenmi
	 */
	public void updateChenmi(String pf, boolean open){
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(pf, open);
		
		//更新redis
		globalCache.hset(GlobalRedisKey.KEY_GATE_OPEN_CHENMI.getKey(), pf, String.valueOf(open));
		//发布redis事件
		globalCache.publish(GlobalRedisKey.CHANNEL_GATE_CHENMI.getKey(), jsonObj.toString());
	}
	/** 删除指定平台的沉迷配置 */
	public void delChenmi(String pf){
		//更新redis
		globalCache.hdel(GlobalRedisKey.KEY_GATE_OPEN_CHENMI.getKey(), pf);
		//发布redis事件
		globalCache.publish(GlobalRedisKey.CHANNEL_GATE_CHENMI_DEL.getKey(), pf);
	}

	
	/** 停服的时候向redis服务器注册，更新服务器状态 */
	public void stopServer(){
		globalCache.destory();
	}
	
	public void shutdown(){
		if( globalCache != null ){
			globalCache.shutdown();
		}
		if( scribeManager != null ){
			scribeManager.shutdown();
		}
	}

}
