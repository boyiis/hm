package com.sz.hm.core.cache.redis;

/**
 * 全局事件订阅器接口
 * @author ChiShiJie
 * @createdAt 16/8/10 下午2:26
 */
public interface GlobalEventSubcriber {

    /**
     * 游戏服务器信息更新
     */
    public void serverChanged(String server);

    /**
     * 平台信息变化
     * @param platform
     */
    public void platformChanged(String platform);

    /**
     * 防沉迷标签更新
     * @param cmFlagMap
     */
    public void cmFlagChanged(String cmFlagMap);

    /**
     * 防沉迷标签删除
     * @param pid
     */
    public void cmFlagRemoved(String pid);

    /**
     * 客户端地址更新
     * @param clientUrlMap key: sid
     * */
    public void clientUrlChanged(String clientUrlMap);

    /**
     *
     * @param sid
     */
    public void clientUrlRemoved(String sid);

    /**
     * 缓存转化率开关
     */
    public void conversionFlagChanged(String flag);

    /**
     * 额外信息
     */
    public void extra(String msg);
}
