package com.cs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * TikTok 用户实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tiktok_user")
@Schema(description = "TikTok 用户")
public class TiktokUser extends Model<TiktokUser> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("user_id")
    @Schema(description = "TikTok 用户 ID")
    private String userId;

    @TableField("username")
    @Schema(description = "用户名")
    private String username;

    @TableField("display_name")
    @Schema(description = "显示名称")
    private String displayName;

    @TableField("avatar_url")
    @Schema(description = "头像 URL")
    private String avatarUrl;

    @TableField("bio")
    @Schema(description = "个人简介")
    private String bio;

    @TableField("verified")
    @Schema(description = "是否认证")
    private Boolean verified;

    @TableField("follower_count")
    @Schema(description = "粉丝数")
    private Long followerCount;

    @TableField("following_count")
    @Schema(description = "关注数")
    private Long followingCount;

    @TableField("video_count")
    @Schema(description = "视频数")
    private Long videoCount;

    @TableField("heart_count")
    @Schema(description = "获赞数")
    private Long heartCount;

    @TableField("user_status")
    @Schema(description = "用户状态")
    private String userStatus;

    @TableField("create_time_tiktok")
    @Schema(description = "TikTok 创建时间")
    private LocalDateTime createTimeTiktok;

    @TableField("update_time_tiktok")
    @Schema(description = "TikTok 更新时间")
    private LocalDateTime updateTimeTiktok;

    @TableField("processed")
    @Schema(description = "是否已处理")
    private Boolean processed;

    @TableField("processed_time")
    @Schema(description = "处理时间")
    private LocalDateTime processedTime;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
