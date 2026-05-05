package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.TiktokUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * TikTok 用户 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface TiktokUserMapper extends BaseMapper<TiktokUser> {
}
