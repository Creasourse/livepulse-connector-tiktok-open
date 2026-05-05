package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.TiktokAccount;
import com.cs.mapper.TiktokAccountMapper;
import com.cs.service.TiktokAdService;
import com.cs.service.TiktokAccountService;
import com.cs.service.TiktokVideoService;
import com.cs.service.TiktokUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * TikTok 账户配置服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class TiktokAccountServiceImpl extends ServiceImpl<TiktokAccountMapper, TiktokAccount> implements TiktokAccountService {

    private final TiktokAccountMapper accountMapper;

    @Lazy
    @Autowired
    private TiktokAdService adService;

    @Lazy
    @Autowired
    private TiktokVideoService videoService;

    @Lazy
    @Autowired
    private TiktokUserService userService;

    @Override
    public TiktokAccount getByAccountId(String accountId) {
        LambdaQueryWrapper<TiktokAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TiktokAccount::getAccountId, accountId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enableAccount(Long id) {
        TiktokAccount account = getById(id);
        if (account == null) {
            return false;
        }
        account.setEnabled(true);
        return updateById(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableAccount(Long id) {
        TiktokAccount account = getById(id);
        if (account == null) {
            return false;
        }
        account.setEnabled(false);
        return updateById(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSyncStatus(Long id, String syncStatus) {
        TiktokAccount account = getById(id);
        if (account == null) {
            return false;
        }
        account.setSyncStatus(syncStatus);
        return updateById(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLastSyncTime(Long id, String syncType) {
        TiktokAccount account = getById(id);
        if (account == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        switch (syncType.toLowerCase()) {
            case "ad":
                account.setLastAdSyncTime(now);
                break;
            case "video":
                account.setLastVideoSyncTime(now);
                break;
            case "user":
                account.setLastUserSyncTime(now);
                break;
            case "insight":
                account.setLastInsightSyncTime(now);
                break;
            default:
                return false;
        }

        return updateById(account);
    }
}
