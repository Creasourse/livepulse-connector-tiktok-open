package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.TiktokAd;
import com.cs.mapper.TiktokAdMapper;
import com.cs.service.TiktokAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * TikTok 广告服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class TiktokAdServiceImpl extends ServiceImpl<TiktokAdMapper, TiktokAd> implements TiktokAdService {

    private final TiktokAdMapper adMapper;

    @Override
    public TiktokAd getByAdId(Long accountId, String adId) {
        LambdaQueryWrapper<TiktokAd> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TiktokAd::getAccountId, accountId)
                .eq(TiktokAd::getAdId, adId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncAds(Long accountId, int daysAgo) {
        // TODO: 实现 TikTok API 调用逻辑
        // 1. 调用 TikTok API 获取广告数据
        // 2. 批量插入或更新广告数据
        // 3. 更新同步日志
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        TiktokAd ad = getById(id);
        if (ad == null) {
            return false;
        }
        ad.setProcessed(true);
        ad.setProcessedTime(LocalDateTime.now());
        return updateById(ad);
    }
}
