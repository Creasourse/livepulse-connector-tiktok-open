package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.TiktokAd;

/**
 * TikTok 广告服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface TiktokAdService extends IService<TiktokAd> {

    /**
     * 根据 TikTok 广告 ID 查询
     *
     * @param accountId 账户 ID
     * @param adId      广告 ID
     * @return 广告
     */
    TiktokAd getByAdId(Long accountId, String adId);

    /**
     * 同步广告数据
     *
     * @param accountId 账户 ID
     * @param daysAgo   同步最近多少天的数据
     * @return 同步的记录数
     */
    int syncAds(Long accountId, int daysAgo);

    /**
     * 标记为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);
}
