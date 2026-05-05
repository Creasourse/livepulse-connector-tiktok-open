package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.TiktokAd;
import com.cs.service.TiktokAdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TikTok 广告控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/tiktok/ad")
@RequiredArgsConstructor
@Tag(name = "TikTok 广告管理", description = "TikTok 广告数据查询接口")
public class TiktokAdController {

    private final TiktokAdService adService;

    @GetMapping("/{id}")
    @Operation(summary = "获取广告详情", description = "根据 ID 获取广告详情")
    public ResponseEntity<TiktokAd> getAd(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        TiktokAd ad = adService.getById(id);
        if (ad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ad);
    }

    @GetMapping
    @Operation(summary = "分页查询广告", description = "分页查询 TikTok 广告列表")
    public ResponseEntity<Page<TiktokAd>> listAds(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "广告状态") @RequestParam(required = false) String adStatus,
            @Parameter(description = "广告系列 ID") @RequestParam(required = false) String campaignId) {
        Page<TiktokAd> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TiktokAd> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(TiktokAd::getAccountId, accountId);
        }
        if (adStatus != null && !adStatus.isEmpty()) {
            wrapper.eq(TiktokAd::getAdStatus, adStatus);
        }
        if (campaignId != null && !campaignId.isEmpty()) {
            wrapper.eq(TiktokAd::getCampaignId, campaignId);
        }

        wrapper.orderByDesc(TiktokAd::getCreatedTime);
        Page<TiktokAd> result = adService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将广告标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        adService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
