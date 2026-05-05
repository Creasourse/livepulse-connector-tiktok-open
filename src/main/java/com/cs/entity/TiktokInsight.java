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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * TikTok 洞察数据实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tiktok_insight")
@Schema(description = "TikTok 洞察数据")
public class TiktokInsight extends Model<TiktokInsight> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("resource_type")
    @Schema(description = "资源类型")
    private String resourceType;

    @TableField("resource_id")
    @Schema(description = "资源 ID")
    private String resourceId;

    @TableField("insight_date")
    @Schema(description = "数据日期")
    private LocalDate insightDate;

    @TableField("impressions")
    @Schema(description = "展示次数")
    private Long impressions;

    @TableField("clicks")
    @Schema(description = "点击次数")
    private Long clicks;

    @TableField("spend")
    @Schema(description = "花费")
    private BigDecimal spend;

    @TableField("cpc")
    @Schema(description = "平均点击成本")
    private BigDecimal cpc;

    @TableField("ctr")
    @Schema(description = "点击率")
    private BigDecimal ctr;

    @TableField("reach")
    @Schema(description = "触达人数")
    private Long reach;

    @TableField("frequency")
    @Schema(description = "平均频次")
    private BigDecimal frequency;

    @TableField("conversions")
    @Schema(description = "转化次数")
    private Integer conversions;

    @TableField("conversion_values")
    @Schema(description = "转化价值")
    private BigDecimal conversionValues;

    @TableField("cost_per_conversion")
    @Schema(description = "单次转化成本")
    private BigDecimal costPerConversion;

    @TableField("video_views")
    @Schema(description = "视频播放次数")
    private Long videoViews;

    @TableField("video_watched_25")
    @Schema(description = "观看25%完成数")
    private Long videoWatched25;

    @TableField("video_watched_50")
    @Schema(description = "观看50%完成数")
    private Long videoWatched50;

    @TableField("video_watched_75")
    @Schema(description = "观看75%完成数")
    private Long videoWatched75;

    @TableField("video_watched_100")
    @Schema(description = "观看100%完成数")
    private Long videoWatched100;

    @TableField("engagement_rate")
    @Schema(description = "互动率")
    private BigDecimal engagementRate;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
