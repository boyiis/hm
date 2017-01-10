package com.sz.hm.core.cache.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 缓存订阅器 <br/>
 * 监听全局事件,并及时更新缓存
 * @author ChiShiJie
 * @createdAt 16/8/10 下午2:49
 */
public class GlobalCacheEventSubscriber implements GlobalEventSubcriber {

    private static final Logger logger = LogManager.getLogger(GlobalCacheEventSubscriber.class);

    @Override
    public void serverChanged(String server) {
        GlobalCache.updateGameCache(server);
    }

    @Override
    public void platformChanged(String platform) {
        GlobalCache.updateCoopBase(platform);

    }

    @Override
    public void cmFlagChanged(String cmFlagMap) {

        JSONObject chenmiJson = JSON.parseObject(cmFlagMap);
        for( String key : chenmiJson.keySet() ){
            GlobalCache.updateChenmi(key, chenmiJson.getBooleanValue(key));
        }

    }

    @Override
    public void cmFlagRemoved(String pid) {
        GlobalCache.delChenmi(pid);
    }

    @Override
    public void clientUrlChanged(String clientUrlMap) {

        JSONObject jsonObj = JSON.parseObject(clientUrlMap);
        for( String key : jsonObj.keySet() ){
            GlobalCache.updateClientUrl(key, jsonObj.getString(key));
        }
    }

    @Override
    public void clientUrlRemoved(String sid) {
        GlobalCache.delClientUrl(sid);
    }

    @Override
    public void conversionFlagChanged(String flag) {
        GlobalCache.cacheConversionOpen(flag);
    }

    @Override
    public void extra(String msg) {
        logger.info("[globalEventSubcriber] recieve msg: {}", msg);
    }
}
