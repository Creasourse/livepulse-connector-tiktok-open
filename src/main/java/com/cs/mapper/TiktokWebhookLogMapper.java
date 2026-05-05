package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.TiktokWebhookLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * TikTok Webhook 日志 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface TiktokWebhookLogMapper extends BaseMapper<TiktokWebhookLog> {
}
