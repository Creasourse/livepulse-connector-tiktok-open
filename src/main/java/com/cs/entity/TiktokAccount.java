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
 * TikTok 账户配置实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tiktok_account")
@Schema(description = "TikTok 账户配置")
public class TiktokAccount extends Model<TiktokAccount> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "TikTok 账户 ID")
    private String accountId;

    @TableField("account_name")
    @Schema(description = "账户名称")
    private String accountName;

    @TableField("display_name")
    @Schema(description = "显示名称")
    private String displayName;

    @TableField("access_token")
    @Schema(description = "访问令牌")
    private String accessToken;

    @TableField("app_id")
    @Schema(description = "应用 ID")
    private String appId;

    @TableField("app_secret")
    @Schema(description = "应用密钥")
    private String appSecret;

    @TableField("advertiser_id")
    @Schema(description = "广告主 ID")
    private String advertiserId;

    @TableField("region")
    @Schema(description = "地区")
    private String region;

    @TableField("currency")
    @Schema(description = "货币代码")
    private String currency;

    @TableField("timezone")
    @Schema(description = "时区")
    private String timezone;

    @TableField("enabled")
    @Schema(description = "是否启用")
    private Boolean enabled;

    @TableField("sync_status")
    @Schema(description = "同步状态")
    private String syncStatus;

    @TableField("last_ad_sync_time")
    @Schema(description = "最后广告同步时间")
    private LocalDateTime lastAdSyncTime;

    @TableField("last_video_sync_time")
    @Schema(description = "最后视频同步时间")
    private LocalDateTime lastVideoSyncTime;

    @TableField("last_user_sync_time")
    @Schema(description = "最后用户同步时间")
    private LocalDateTime lastUserSyncTime;

    @TableField("last_insight_sync_time")
    @Schema(description = "最后洞察数据同步时间")
    private LocalDateTime lastInsightSyncTime;

    @TableField("webhook_enabled")
    @Schema(description = "是否启用 Webhook")
    private Boolean webhookEnabled;

    @TableField("webhook_url")
    @Schema(description = "Webhook 回调 URL")
    private String webhookUrl;

    @TableField("last_error_message")
    @Schema(description = "最后错误信息")
    private String lastErrorMessage;

    @TableField("retry_count")
    @Schema(description = "重试次数")
    private Integer retryCount;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @TableField("create_by")
    @Schema(description = "创建人")
    private String createBy;

    @TableField("update_by")
    @Schema(description = "更新人")
    private String updateBy;
}
