package com.sz.hm.core.cache.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 全局信息本地缓存
 */
public class GlobalCache {

	// 混服
	private static boolean isHf;
	
	

	/** 平台的防沉迷开关 */
	private static Map<String, Boolean> coopChenmiMap = new HashMap<>();
	
	/** 服务器ID对应的客户端地址 */
	private static Map<String, String> clientUrlMap = new HashMap<>();
	
	/** 是否开启转化率统计 */
	private static boolean conversionOpen = false;
	
	//账户Id对应的ServerId
	private static Cache<String, Integer> roleCache =CacheBuilder.newBuilder().maximumSize(1000000).expireAfterAccess(24, TimeUnit.HOURS).build();
	
	
	/** 程序启动，缓存所有服务器 */
	public static void cacheAll(Map<String, String> serverMap){
		if( serverMap == null || serverMap.isEmpty() )return;
		
		for( Entry<String, String> entry : serverMap.entrySet() ){
			updateGameCache(entry.getValue());
		}
	}
	
	/** 缓存单个服务器 */
	public static void updateGameCache(String serverInfo){
		
	}
	
	/**
	 * 缓存所有的coopbase
	 */
	public static void cacheCoopBase(Map<String,String> coopBaseMap){
		if( coopBaseMap == null || coopBaseMap.isEmpty() )return;
		for (Entry<String, String> entry : coopBaseMap.entrySet()) {
			updateCoopBase(entry.getValue());
		}
	}
	
	/**
	 * 根据平台名缓存
	 */
	public static void updateCoopBase(String coopbaseInfo){
		
	}

	
	/**
	 * 缓存所有的coopbase
	 */
	public static void cacheCoopChenmi(Map<String,String> chenmiMap){
		if( chenmiMap == null || chenmiMap.isEmpty() )return;
		for (Entry<String, String> entry : chenmiMap.entrySet()) {
			updateChenmi(entry.getKey(), Boolean.valueOf(entry.getValue()));
		}
	}
	/** 根据平台名缓存 */
	public static void updateChenmi(String platform, boolean chenmi){
		coopChenmiMap.put(platform, chenmi);
	}
	/** 根据平台名删除 */
	public static void delChenmi(String platform){
		coopChenmiMap.remove(platform);
	}
	/** 获取所有的防沉迷信息 */
	public static Map<String, Boolean> getAllChenmiInfo(){
		return new HashMap<>(coopChenmiMap);
	}
	/** 获取指定平台的防沉迷信息 */
	public static Boolean getChenmiByPlatform(String platform){
		Boolean result = coopChenmiMap.get(platform);
		
		return result == null ? true : result.booleanValue();
	}
	
	
	

	/** 生成平台名+平台Sid的Key */
	private static String generatePSidKey(String platform, Object serverId){
		if(isHf) {
			return serverId.toString();
		}
		return platform+"-"+serverId;
	}
	/** 判断平台+服务器是否存在 */
	public static boolean exist(String platform, Object serverId){
		return true;
	}

	

	
	/**
	 * 检查用户是否存在
	 */
	public static boolean checkRoleIsExit(String userId,Integer serverId){
		if(roleCache.getIfPresent(userId) == null){
			return false;
		}
		if(roleCache.getIfPresent(userId).intValue()!=serverId.intValue()){
			return false;
		}
		return true;
	}
	
	/**缓存用户**/
	public static void cacheRole(String userId,Integer serverId){
		roleCache.put(userId, serverId);
	}
	
	/** 缓存客户端地址 */
	public static void cacheAllClientUrl(Map<String, String> urlMap){
		if( urlMap != null && !urlMap.isEmpty() ){
			clientUrlMap.putAll(urlMap);
		}
	}
	
	/** 更新客户端地址 */
	public static void updateClientUrl(String sid, String url){
		clientUrlMap.put(sid, url);
	}
	/** 删除客户端地址 */
	public static void delClientUrl(String sid){
		clientUrlMap.remove(sid);
	}
	/** 根据sid获取客户端地址 */
	public static String getClientUrl(String sid){
		String result = clientUrlMap.get(sid);
	
		return result;
	}
	/** 获取所有客户端地址 */
	public static Map<String, String> getAllClientUrl(){
		return new HashMap<>(clientUrlMap);
	}
	
	/** 缓存转化率开启状态 */
	public static void cacheConversionOpen(boolean open){
		conversionOpen = open;
	}
	/** 缓存转化率开启状态 */
	public static void cacheConversionOpen(String msg){
		conversionOpen = !Strings.isNullOrEmpty(msg) && Integer.valueOf(msg) > 0;
	}
	/** 获取转化率是否开启 */
	public static boolean getConversionOpen(){
		return conversionOpen;
	}

	public static void setIsHf(boolean isHf) {
		GlobalCache.isHf = isHf;
	}
}
