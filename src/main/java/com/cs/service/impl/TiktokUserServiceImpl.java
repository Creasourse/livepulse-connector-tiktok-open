package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.TiktokUser;
import com.cs.mapper.TiktokUserMapper;
import com.cs.service.TiktokUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * TikTok 用户服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class TiktokUserServiceImpl extends ServiceImpl<TiktokUserMapper, TiktokUser> implements TiktokUserService {

    private final TiktokUserMapper userMapper;

    @Override
    public TiktokUser getByUserId(Long accountId, String userId) {
        LambdaQueryWrapper<TiktokUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TiktokUser::getAccountId, accountId)
                .eq(TiktokUser::getUserId, userId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncUsers(Long accountId, int daysAgo) {
        // TODO: 实现 TikTok API 调用逻辑
        // 1. 调用 TikTok API 获取用户数据
        // 2. 批量插入或更新用户数据
        // 3. 更新同步日志
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        TiktokUser user = getById(id);
        if (user == null) {
            return false;
        }
        user.setProcessed(true);
        user.setProcessedTime(LocalDateTime.now());
        return updateById(user);
    }
}
