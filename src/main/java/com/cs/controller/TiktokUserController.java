package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.TiktokUser;
import com.cs.service.TiktokUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TikTok 用户控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/tiktok/user")
@RequiredArgsConstructor
@Tag(name = "TikTok 用户管理", description = "TikTok 用户数据查询接口")
public class TiktokUserController {

    private final TiktokUserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情", description = "根据 ID 获取用户详情")
    public ResponseEntity<TiktokUser> getUser(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        TiktokUser user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(summary = "分页查询用户", description = "分页查询 TikTok 用户列表")
    public ResponseEntity<Page<TiktokUser>> listUsers(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "用户名") @RequestParam(required = false) String username,
            @Parameter(description = "用户状态") @RequestParam(required = false) String userStatus) {
        Page<TiktokUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TiktokUser> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(TiktokUser::getAccountId, accountId);
        }
        if (username != null && !username.isEmpty()) {
            wrapper.like(TiktokUser::getUsername, username);
        }
        if (userStatus != null && !userStatus.isEmpty()) {
            wrapper.eq(TiktokUser::getUserStatus, userStatus);
        }

        wrapper.orderByDesc(TiktokUser::getFollowerCount);
        Page<TiktokUser> result = userService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将用户标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        userService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
