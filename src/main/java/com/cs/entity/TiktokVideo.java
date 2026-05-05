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
 * TikTok 视频实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tiktok_video")
@Schema(description = "TikTok 视频")
public class TiktokVideo extends Model<TiktokVideo> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("video_id")
    @Schema(description = "TikTok 视频 ID")
    private String videoId;

    @TableField("video_title")
    @Schema(description = "视频标题")
    private String videoTitle;

    @TableField("video_desc")
    @Schema(description = "视频描述")
    private String videoDesc;

    @TableField("video_url")
    @Schema(description = "视频 URL")
    private String videoUrl;

    @TableField("thumbnail_url")
    @Schema(description = "缩略图 URL")
    private String thumbnailUrl;

    @TableField("duration")
    @Schema(description = "视频时长（秒）")
    private Integer duration;

    @TableField("view_count")
    @Schema(description = "播放量")
    private Long viewCount;

    @TableField("like_count")
    @Schema(description = "点赞数")
    private Long likeCount;

    @TableField("comment_count")
    @Schema(description = "评论数")
    private Long commentCount;

    @TableField("share_count")
    @Schema(description = "分享数")
    private Long shareCount;

    @TableField("save_count")
    @Schema(description = "收藏数")
    private Long saveCount;

    @TableField("video_status")
    @Schema(description = "视频状态")
    private String videoStatus;

    @TableField("privacy_level")
    @Schema(description = "隐私级别")
    private String privacyLevel;

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
