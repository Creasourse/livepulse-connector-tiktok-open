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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TikTok 广告实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tiktok_ad")
@Schema(description = "TikTok 广告")
public class TiktokAd extends Model<TiktokAd> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("ad_id")
    @Schema(description = "TikTok 广告 ID")
    private String adId;

    @TableField("ad_name")
    @Schema(description = "广告名称")
    private String adName;

    @TableField("ad_group_id")
    @Schema(description = "广告组 ID")
    private String adGroupId;

    @TableField("campaign_id")
    @Schema(description = "广告系列 ID")
    private String campaignId;

    @TableField("ad_status")
    @Schema(description = "广告状态")
    private String adStatus;

    @TableField("ad_type")
    @Schema(description = "广告类型")
    private String adType;

    @TableField("objective")
    @Schema(description = "广告目标")
    private String objective;

    @TableField("budget_mode")
    @Schema(description = "预算模式")
    private String budgetMode;

    @TableField("budget")
    @Schema(description = "预算")
    private BigDecimal budget;

    @TableField("bid_price")
    @Schema(description = "出价")
    private BigDecimal bidPrice;

    @TableField("optimization_goal")
    @Schema(description = "优化目标")
    private String optimizationGoal;

    @TableField("creative_type")
    @Schema(description = "创意类型")
    private String creativeType;

    @TableField("targeting")
    @Schema(description = "定向条件")
    private String targeting;

    @TableField("created_time")
    @Schema(description = "TikTok 创建时间")
    private LocalDateTime createdTime;

    @TableField("updated_time")
    @Schema(description = "TikTok 更新时间")
    private LocalDateTime updatedTime;

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
