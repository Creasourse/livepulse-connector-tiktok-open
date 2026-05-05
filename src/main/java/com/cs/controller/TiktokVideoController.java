package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.TiktokVideo;
import com.cs.service.TiktokVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TikTok 视频控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/tiktok/video")
@RequiredArgsConstructor
@Tag(name = "TikTok 视频管理", description = "TikTok 视频数据查询接口")
public class TiktokVideoController {

    private final TiktokVideoService videoService;

    @GetMapping("/{id}")
    @Operation(summary = "获取视频详情", description = "根据 ID 获取视频详情")
    public ResponseEntity<TiktokVideo> getVideo(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        TiktokVideo video = videoService.getById(id);
        if (video == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(video);
    }

    @GetMapping
    @Operation(summary = "分页查询视频", description = "分页查询 TikTok 视频列表")
    public ResponseEntity<Page<TiktokVideo>> listVideos(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "视频状态") @RequestParam(required = false) String videoStatus,
            @Parameter(description = "视频标题") @RequestParam(required = false) String videoTitle) {
        Page<TiktokVideo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TiktokVideo> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(TiktokVideo::getAccountId, accountId);
        }
        if (videoStatus != null && !videoStatus.isEmpty()) {
            wrapper.eq(TiktokVideo::getVideoStatus, videoStatus);
        }
        if (videoTitle != null && !videoTitle.isEmpty()) {
            wrapper.like(TiktokVideo::getVideoTitle, videoTitle);
        }

        wrapper.orderByDesc(TiktokVideo::getViewCount);
        Page<TiktokVideo> result = videoService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将视频标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        videoService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
