package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.TiktokSyncLog;

/**
 * TikTok 同步日志服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface TiktokSyncLogService extends IService<TiktokSyncLog> {

    /**
     * 创建同步日志
     *
     * @param accountId  账户 ID
     * @param syncType   同步类型
     * @param syncMethod 同步方式
     * @return 同步日志
     */
    TiktokSyncLog createSyncLog(Long accountId, String syncType, String syncMethod);

    /**
     * 更新同步日志为成功
     *
     * @param syncLog     同步日志
     * @param totalCount  总记录数
     * @param successCount 成功数量
     * @param failureCount 失败数量
     * @return 是否成功
     */
    boolean updateSyncLogSuccess(TiktokSyncLog syncLog, int totalCount, int successCount, int failureCount);

    /**
     * 更新同步日志为失败
     *
     * @param syncLog     同步日志
     * @param totalCount  总记录数
     * @param successCount 成功数量
     * @param failureCount 失败数量
     * @param errorMessage 错误信息
     * @return 是否成功
     */
    boolean updateSyncLogFailure(TiktokSyncLog syncLog, int totalCount, int successCount, int failureCount, String errorMessage);
}
