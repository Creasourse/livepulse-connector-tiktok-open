package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.TiktokVideo;
import com.cs.mapper.TiktokVideoMapper;
import com.cs.service.TiktokVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * TikTok 视频服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class TiktokVideoServiceImpl extends ServiceImpl<TiktokVideoMapper, TiktokVideo> implements TiktokVideoService {

    private final TiktokVideoMapper videoMapper;

    @Override
    public TiktokVideo getByVideoId(Long accountId, String videoId) {
        LambdaQueryWrapper<TiktokVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TiktokVideo::getAccountId, accountId)
                .eq(TiktokVideo::getVideoId, videoId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncVideos(Long accountId, int daysAgo) {
        // TODO: 实现 TikTok API 调用逻辑
        // 1. 调用 TikTok API 获取视频数据
        // 2. 批量插入或更新视频数据
        // 3. 更新同步日志
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        TiktokVideo video = getById(id);
        if (video == null) {
            return false;
        }
        video.setProcessed(true);
        video.setProcessedTime(LocalDateTime.now());
        return updateById(video);
    }
}
