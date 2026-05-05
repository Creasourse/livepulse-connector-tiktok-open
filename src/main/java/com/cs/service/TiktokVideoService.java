package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.TiktokVideo;

/**
 * TikTok 视频服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface TiktokVideoService extends IService<TiktokVideo> {

    /**
     * 根据 TikTok 视频 ID 查询
     *
     * @param accountId 账户 ID
     * @param videoId   视频 ID
     * @return 视频
     */
    TiktokVideo getByVideoId(Long accountId, String videoId);

    /**
     * 同步视频数据
     *
     * @param accountId 账户 ID
     * @param daysAgo   同步最近多少天的数据
     * @return 同步的记录数
     */
    int syncVideos(Long accountId, int daysAgo);

    /**
     * 标记为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);
}
