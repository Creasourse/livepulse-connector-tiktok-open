package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.TiktokAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * TikTok 账户配置 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface TiktokAccountMapper extends BaseMapper<TiktokAccount> {
}
