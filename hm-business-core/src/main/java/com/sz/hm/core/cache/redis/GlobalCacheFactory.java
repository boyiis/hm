package com.sz.hm.core.cache.redis;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

/**
 * Global信息缓存对象工厂
 * @author ChiShiJie
 * @createdAt 16/8/10 下午1:41
 */
public class GlobalCacheFactory {

    private static final Logger logger = LogManager.getLogger(GlobalCacheFactory.class);

    private GlobalApi globalApi;

    public GlobalCacheFactory(String masterName, String _sentinels, int database){

        Assert.notNull(masterName, "need property: masterName ");
        Assert.notNull(_sentinels, "need property: sentinels");
        Assert.notNull(database, "need property: database");

        Iterable<String> iterable = Splitter.on(";").split(_sentinels);
        Set<String> sentinels = Sets.newHashSet(iterable);

        //初始化GlobalApi
        Redis redis = new Redis(masterName,sentinels, database);
        GlobalApi globalApi = new GlobalApi(redis);
        this.globalApi = globalApi;

    }

    public void init(){

        //全局事件订阅
        this.globalApi.subscribe(new GlobalCacheEventSubscriber());

        logger.info("init global cache stop");


    }

}
