package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.TiktokAccount;

/**
 * TikTok 账户配置服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface TiktokAccountService extends IService<TiktokAccount> {

    /**
     * 根据 TikTok 账户 ID 查询
     *
     * @param accountId TikTok 账户 ID
     * @return 账户配置
     */
    TiktokAccount getByAccountId(String accountId);

    /**
     * 启用账户
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean enableAccount(Long id);

    /**
     * 禁用账户
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean disableAccount(Long id);

    /**
     * 更新同步状态
     *
     * @param id         主键 ID
     * @param syncStatus 同步状态
     * @return 是否成功
     */
    boolean updateSyncStatus(Long id, String syncStatus);

    /**
     * 更新最后同步时间
     *
     * @param id       主键 ID
     * @param syncType 同步类型
     * @return 是否成功
     */
    boolean updateLastSyncTime(Long id, String syncType);
}
