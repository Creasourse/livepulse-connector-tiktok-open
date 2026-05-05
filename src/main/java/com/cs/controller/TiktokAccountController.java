package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.TiktokAccount;
import com.cs.service.TiktokAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TikTok 账户配置控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/tiktok/account")
@RequiredArgsConstructor
@Tag(name = "TikTok 账户管理", description = "TikTok 账户配置管理接口")
public class TiktokAccountController {

    private final TiktokAccountService accountService;

    @PostMapping
    @Operation(summary = "添加账户配置", description = "添加新的 TikTok 账户配置")
    public ResponseEntity<TiktokAccount> addAccount(@RequestBody TiktokAccount account) {
        account.setCreateTime(java.time.LocalDateTime.now());
        account.setUpdateTime(java.time.LocalDateTime.now());
        account.setEnabled(true);
        account.setSyncStatus("pending");
        accountService.save(account);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新账户配置", description = "更新 TikTok 账户配置")
    public ResponseEntity<TiktokAccount> updateAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @RequestBody TiktokAccount account) {
        account.setId(id);
        account.setUpdateTime(java.time.LocalDateTime.now());
        accountService.updateById(account);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除账户配置", description = "删除 TikTok 账户配置")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        accountService.removeById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取账户配置", description = "根据 ID 获取账户配置")
    public ResponseEntity<TiktokAccount> getAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        TiktokAccount account = accountService.getById(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping
    @Operation(summary = "分页查询账户配置", description = "分页查询 TikTok 账户配置列表")
    public ResponseEntity<Page<TiktokAccount>> listAccounts(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户名称") @RequestParam(required = false) String accountName) {
        Page<TiktokAccount> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TiktokAccount> wrapper = new LambdaQueryWrapper<>();
        if (accountName != null && !accountName.isEmpty()) {
            wrapper.like(TiktokAccount::getAccountName, accountName);
        }
        wrapper.orderByDesc(TiktokAccount::getCreateTime);
        Page<TiktokAccount> result = accountService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/enable")
    @Operation(summary = "启用账户", description = "启用指定的账户")
    public ResponseEntity<Void> enableAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        accountService.enableAccount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/disable")
    @Operation(summary = "禁用账户", description = "禁用指定的账户")
    public ResponseEntity<Void> disableAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        accountService.disableAccount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sync/ads")
    @Operation(summary = "手动同步广告", description = "手动触发广告数据同步")
    public ResponseEntity<String> syncAds(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @Parameter(description = "同步最近多少天") @RequestParam(defaultValue = "30") Integer daysAgo) {
        // TODO: 实现广告同步逻辑
        return ResponseEntity.ok("广告同步任务已提交");
    }

    @PostMapping("/{id}/sync/videos")
    @Operation(summary = "手动同步视频", description = "手动触发视频数据同步")
    public ResponseEntity<String> syncVideos(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @Parameter(description = "同步最近多少天") @RequestParam(defaultValue = "30") Integer daysAgo) {
        // TODO: 实现视频同步逻辑
        return ResponseEntity.ok("视频同步任务已提交");
    }

    @PostMapping("/{id}/sync/users")
    @Operation(summary = "手动同步用户", description = "手动触发用户数据同步")
    public ResponseEntity<String> syncUsers(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @Parameter(description = "同步最近多少天") @RequestParam(defaultValue = "30") Integer daysAgo) {
        // TODO: 实现用户同步逻辑
        return ResponseEntity.ok("用户同步任务已提交");
    }
}
