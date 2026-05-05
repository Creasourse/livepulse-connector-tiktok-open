package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.TiktokUser;

/**
 * TikTok 用户服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface TiktokUserService extends IService<TiktokUser> {

    /**
     * 根据 TikTok 用户 ID 查询
     *
     * @param accountId 账户 ID
     * @param userId    用户 ID
     * @return 用户
     */
    TiktokUser getByUserId(Long accountId, String userId);

    /**
     * 同步用户数据
     *
     * @param accountId 账户 ID
     * @param daysAgo   同步最近多少天的数据
     * @return 同步的记录数
     */
    int syncUsers(Long accountId, int daysAgo);

    /**
     * 标记为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);
}
