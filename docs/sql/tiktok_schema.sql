-- TikTok 连接器 PostgreSQL 表结构
-- 版本: 2.0
-- 说明: 支持多账户管理、广告/视频/用户同步、Webhook 事件订阅

-- ============================================
-- 清理已存在的表（按依赖关系顺序）
-- ============================================
DROP TABLE IF EXISTS tiktok_insight CASCADE;
DROP TABLE IF EXISTS tiktok_video CASCADE;
DROP TABLE IF EXISTS tiktok_ad CASCADE;
DROP TABLE IF EXISTS tiktok_user CASCADE;
DROP TABLE IF EXISTS tiktok_webhook_log CASCADE;
DROP TABLE IF EXISTS tiktok_sync_log CASCADE;
DROP TABLE IF EXISTS tiktok_account CASCADE;

-- ============================================
-- 1. TikTok 账户配置表
-- ============================================
CREATE TABLE tiktok_account (
    id BIGSERIAL PRIMARY KEY,
    account_id VARCHAR(255) NOT NULL,
    account_name VARCHAR(500),
    display_name VARCHAR(500),
    access_token VARCHAR(500) NOT NULL,
    app_id VARCHAR(255),
    app_secret VARCHAR(255),
    advertiser_id VARCHAR(255),
    region VARCHAR(50),
    currency VARCHAR(10),
    timezone VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE,
    sync_status VARCHAR(50) DEFAULT 'pending',
    last_ad_sync_time TIMESTAMP,
    last_video_sync_time TIMESTAMP,
    last_user_sync_time TIMESTAMP,
    last_insight_sync_time TIMESTAMP,
    webhook_enabled BOOLEAN DEFAULT FALSE,
    webhook_url VARCHAR(500),
    last_error_message TEXT,
    retry_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(100),
    update_by VARCHAR(100),
    CONSTRAINT uk_tiktok_account_id UNIQUE (account_id)
);

-- 表注释
COMMENT ON TABLE tiktok_account IS 'TikTok 账户配置表';
COMMENT ON COLUMN tiktok_account.account_id IS 'TikTok 账户 ID';
COMMENT ON COLUMN tiktok_account.account_name IS '账户名称';
COMMENT ON COLUMN tiktok_account.display_name IS '显示名称';
COMMENT ON COLUMN tiktok_account.access_token IS '访问令牌';
COMMENT ON COLUMN tiktok_account.app_id IS '应用 ID';
COMMENT ON COLUMN tiktok_account.app_secret IS '应用密钥';
COMMENT ON COLUMN tiktok_account.advertiser_id IS '广告主 ID';
COMMENT ON COLUMN tiktok_account.region IS '地区: US/GB/CA/AU/JP/KR/SG/MY/TH/PH/VN/ID/TW/CN';
COMMENT ON COLUMN tiktok_account.currency IS '货币代码';
COMMENT ON COLUMN tiktok_account.timezone IS '时区';
COMMENT ON COLUMN tiktok_account.enabled IS '是否启用';
COMMENT ON COLUMN tiktok_account.sync_status IS '同步状态: pending/syncing/success/failed';
COMMENT ON COLUMN tiktok_account.last_ad_sync_time IS '最后广告同步时间';
COMMENT ON COLUMN tiktok_account.last_video_sync_time IS '最后视频同步时间';
COMMENT ON COLUMN tiktok_account.last_user_sync_time IS '最后用户同步时间';
COMMENT ON COLUMN tiktok_account.last_insight_sync_time IS '最后洞察数据同步时间';
COMMENT ON COLUMN tiktok_account.webhook_enabled IS '是否启用 Webhook';
COMMENT ON COLUMN tiktok_account.webhook_url IS 'Webhook 回调 URL';
COMMENT ON COLUMN tiktok_account.last_error_message IS '最后错误信息';
COMMENT ON COLUMN tiktok_account.retry_count IS '重试次数';
COMMENT ON COLUMN tiktok_account.create_time IS '创建时间';
COMMENT ON COLUMN tiktok_account.update_time IS '更新时间';
COMMENT ON COLUMN tiktok_account.create_by IS '创建人';
COMMENT ON COLUMN tiktok_account.update_by IS '更新人';

-- ============================================
-- 2. TikTok 广告表
-- ============================================
CREATE TABLE tiktok_ad (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    ad_id VARCHAR(255) NOT NULL,
    ad_name VARCHAR(500),
    ad_group_id VARCHAR(255),
    campaign_id VARCHAR(255),
    ad_status VARCHAR(50),
    ad_type VARCHAR(100),
    objective VARCHAR(100),
    budget_mode VARCHAR(50),
    budget DECIMAL(15, 2),
    bid_price DECIMAL(15, 2),
    optimization_goal VARCHAR(100),
    creative_type VARCHAR(100),
    targeting TEXT,
    created_time TIMESTAMP,
    updated_time TIMESTAMP,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tiktok_ad_account FOREIGN KEY (account_id) REFERENCES tiktok_account(id) ON DELETE CASCADE,
    CONSTRAINT uk_tiktok_ad_id UNIQUE (account_id, ad_id)
);

CREATE INDEX idx_tiktok_ad_account_id ON tiktok_ad(account_id);
CREATE INDEX idx_tiktok_ad_status ON tiktok_ad(ad_status);
CREATE INDEX idx_tiktok_ad_created_time ON tiktok_ad(created_time);
CREATE INDEX idx_tiktok_ad_campaign_id ON tiktok_ad(campaign_id);

-- 表注释
COMMENT ON TABLE tiktok_ad IS 'TikTok 广告表';
COMMENT ON COLUMN tiktok_ad.account_id IS '关联的账户 ID';
COMMENT ON COLUMN tiktok_ad.ad_id IS 'TikTok 广告 ID';
COMMENT ON COLUMN tiktok_ad.ad_name IS '广告名称';
COMMENT ON COLUMN tiktok_ad.ad_group_id IS '广告组 ID';
COMMENT ON COLUMN tiktok_ad.campaign_id IS '广告系列 ID';
COMMENT ON COLUMN tiktok_ad.ad_status IS '广告状态: ACTIVE/PAUSED/DISABLED/DELETED/NOT_STARTED';
COMMENT ON COLUMN tiktok_ad.ad_type IS '广告类型: VIDEO/IMAGE/SPIRAL';
COMMENT ON COLUMN tiktok_ad.objective IS '广告目标: CONVERSION/TRAFFIC/APP_INSTALL/VIDEO_VIEWS/LIKES/COMMENTS/SHARES/FOLLOWS';
COMMENT ON COLUMN tiktok_ad.budget_mode IS '预算模式: DAY_BUDGET/LIFETIME_BUDGET';
COMMENT ON COLUMN tiktok_ad.budget IS '预算';
COMMENT ON COLUMN tiktok_ad.bid_price IS '出价';
COMMENT ON COLUMN tiktok_ad.optimization_goal IS '优化目标: CONVERSION/CLICKS/IMPRESSIONS/REACH';
COMMENT ON COLUMN tiktok_ad.creative_type IS '创意类型';
COMMENT ON COLUMN tiktok_ad.targeting IS '定向条件 (JSON)';
COMMENT ON COLUMN tiktok_ad.created_time IS 'TikTok 创建时间';
COMMENT ON COLUMN tiktok_ad.updated_time IS 'TikTok 更新时间';
COMMENT ON COLUMN tiktok_ad.processed IS '是否已处理';
COMMENT ON COLUMN tiktok_ad.processed_time IS '处理时间';
COMMENT ON COLUMN tiktok_ad.create_time IS '创建时间';
COMMENT ON COLUMN tiktok_ad.update_time IS '更新时间';

-- ============================================
-- 3. TikTok 视频表
-- ============================================
CREATE TABLE tiktok_video (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    video_id VARCHAR(255) NOT NULL,
    video_title VARCHAR(500),
    video_desc TEXT,
    video_url VARCHAR(1000),
    thumbnail_url VARCHAR(1000),
    duration INT,
    view_count BIGINT DEFAULT 0,
    like_count BIGINT DEFAULT 0,
    comment_count BIGINT DEFAULT 0,
    share_count BIGINT DEFAULT 0,
    save_count BIGINT DEFAULT 0,
    video_status VARCHAR(50),
    privacy_level VARCHAR(50),
    create_time_tiktok TIMESTAMP,
    update_time_tiktok TIMESTAMP,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tiktok_video_account FOREIGN KEY (account_id) REFERENCES tiktok_account(id) ON DELETE CASCADE,
    CONSTRAINT uk_tiktok_video_id UNIQUE (account_id, video_id)
);

CREATE INDEX idx_tiktok_video_account_id ON tiktok_video(account_id);
CREATE INDEX idx_tiktok_video_status ON tiktok_video(video_status);
CREATE INDEX idx_tiktok_video_create_time ON tiktok_video(create_time_tiktok);
CREATE INDEX idx_tiktok_video_view_count ON tiktok_video(view_count);

-- 表注释
COMMENT ON TABLE tiktok_video IS 'TikTok 视频表';
COMMENT ON COLUMN tiktok_video.account_id IS '关联的账户 ID';
COMMENT ON COLUMN tiktok_video.video_id IS 'TikTok 视频 ID';
COMMENT ON COLUMN tiktok_video.video_title IS '视频标题';
COMMENT ON COLUMN tiktok_video.video_desc IS '视频描述';
COMMENT ON COLUMN tiktok_video.video_url IS '视频 URL';
COMMENT ON COLUMN tiktok_video.thumbnail_url IS '缩略图 URL';
COMMENT ON COLUMN tiktok_video.duration IS '视频时长（秒）';
COMMENT ON COLUMN tiktok_video.view_count IS '播放量';
COMMENT ON COLUMN tiktok_video.like_count IS '点赞数';
COMMENT ON COLUMN tiktok_video.comment_count IS '评论数';
COMMENT ON COLUMN tiktok_video.share_count IS '分享数';
COMMENT ON COLUMN tiktok_video.save_count IS '收藏数';
COMMENT ON COLUMN tiktok_video.video_status IS '视频状态: ACTIVE/DELETED/PRIVATE/BLOCKED';
COMMENT ON COLUMN tiktok_video.privacy_level IS '隐私级别: PUBLIC/PRIVATE/FRIENDS';
COMMENT ON COLUMN tiktok_video.create_time_tiktok IS 'TikTok 创建时间';
COMMENT ON COLUMN tiktok_video.update_time_tiktok IS 'TikTok 更新时间';
COMMENT ON COLUMN tiktok_video.processed IS '是否已处理';
COMMENT ON COLUMN tiktok_video.processed_time IS '处理时间';
COMMENT ON COLUMN tiktok_video.create_time IS '创建时间';
COMMENT ON COLUMN tiktok_video.update_time IS '更新时间';

-- ============================================
-- 4. TikTok 用户表
-- ============================================
CREATE TABLE tiktok_user (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    username VARCHAR(255),
    display_name VARCHAR(500),
    avatar_url VARCHAR(1000),
    bio TEXT,
    verified BOOLEAN DEFAULT FALSE,
    follower_count BIGINT DEFAULT 0,
    following_count BIGINT DEFAULT 0,
    video_count BIGINT DEFAULT 0,
    heart_count BIGINT DEFAULT 0,
    user_status VARCHAR(50),
    create_time_tiktok TIMESTAMP,
    update_time_tiktok TIMESTAMP,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tiktok_user_account FOREIGN KEY (account_id) REFERENCES tiktok_account(id) ON DELETE CASCADE,
    CONSTRAINT uk_tiktok_user_id UNIQUE (account_id, user_id)
);

CREATE INDEX idx_tiktok_user_account_id ON tiktok_user(account_id);
CREATE INDEX idx_tiktok_user_username ON tiktok_user(username);
CREATE INDEX idx_tiktok_user_create_time ON tiktok_user(create_time_tiktok);
CREATE INDEX idx_tiktok_user_follower_count ON tiktok_user(follower_count);

-- 表注释
COMMENT ON TABLE tiktok_user IS 'TikTok 用户表';
COMMENT ON COLUMN tiktok_user.account_id IS '关联的账户 ID';
COMMENT ON COLUMN tiktok_user.user_id IS 'TikTok 用户 ID';
COMMENT ON COLUMN tiktok_user.username IS '用户名';
COMMENT ON COLUMN tiktok_user.display_name IS '显示名称';
COMMENT ON COLUMN tiktok_user.avatar_url IS '头像 URL';
COMMENT ON COLUMN tiktok_user.bio IS '个人简介';
COMMENT ON COLUMN tiktok_user.verified IS '是否认证';
COMMENT ON COLUMN tiktok_user.follower_count IS '粉丝数';
COMMENT ON COLUMN tiktok_user.following_count IS '关注数';
COMMENT ON COLUMN tiktok_user.video_count IS '视频数';
COMMENT ON COLUMN tiktok_user.heart_count IS '获赞数';
COMMENT ON COLUMN tiktok_user.user_status IS '用户状态: ACTIVE/DELETED/BLOCKED';
COMMENT ON COLUMN tiktok_user.create_time_tiktok IS 'TikTok 创建时间';
COMMENT ON COLUMN tiktok_user.update_time_tiktok IS 'TikTok 更新时间';
COMMENT ON COLUMN tiktok_user.processed IS '是否已处理';
COMMENT ON COLUMN tiktok_user.processed_time IS '处理时间';
COMMENT ON COLUMN tiktok_user.create_time IS '创建时间';
COMMENT ON COLUMN tiktok_user.update_time IS '更新时间';

-- ============================================
-- 5. TikTok 洞察数据表
-- ============================================
CREATE TABLE tiktok_insight (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    resource_type VARCHAR(50) NOT NULL,
    resource_id VARCHAR(255) NOT NULL,
    insight_date DATE NOT NULL,
    impressions BIGINT DEFAULT 0,
    clicks BIGINT DEFAULT 0,
    spend DECIMAL(15, 2) DEFAULT 0,
    cpc DECIMAL(15, 2) DEFAULT 0,
    ctr DECIMAL(10, 4) DEFAULT 0,
    reach BIGINT DEFAULT 0,
    frequency DECIMAL(10, 2) DEFAULT 0,
    conversions INT DEFAULT 0,
    conversion_values DECIMAL(15, 2) DEFAULT 0,
    cost_per_conversion DECIMAL(15, 2) DEFAULT 0,
    video_views BIGINT DEFAULT 0,
    video_watched_25 BIGINT DEFAULT 0,
    video_watched_50 BIGINT DEFAULT 0,
    video_watched_75 BIGINT DEFAULT 0,
    video_watched_100 BIGINT DEFAULT 0,
    engagement_rate DECIMAL(10, 4) DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tiktok_insight_account FOREIGN KEY (account_id) REFERENCES tiktok_account(id) ON DELETE CASCADE,
    CONSTRAINT uk_tiktok_insight UNIQUE (account_id, resource_type, resource_id, insight_date)
);

CREATE INDEX idx_tiktok_insight_account_id ON tiktok_insight(account_id);
CREATE INDEX idx_tiktok_insight_resource ON tiktok_insight(resource_type, resource_id);
CREATE INDEX idx_tiktok_insight_date ON tiktok_insight(insight_date);

-- 表注释
COMMENT ON TABLE tiktok_insight IS 'TikTok 洞察数据表';
COMMENT ON COLUMN tiktok_insight.account_id IS '关联的账户 ID';
COMMENT ON COLUMN tiktok_insight.resource_type IS '资源类型: ad/video/user';
COMMENT ON COLUMN tiktok_insight.resource_id IS '资源 ID';
COMMENT ON COLUMN tiktok_insight.insight_date IS '数据日期';
COMMENT ON COLUMN tiktok_insight.impressions IS '展示次数';
COMMENT ON COLUMN tiktok_insight.clicks IS '点击次数';
COMMENT ON COLUMN tiktok_insight.spend IS '花费';
COMMENT ON COLUMN tiktok_insight.cpc IS '平均点击成本';
COMMENT ON COLUMN tiktok_insight.ctr IS '点击率';
COMMENT ON COLUMN tiktok_insight.reach IS '触达人数';
COMMENT ON COLUMN tiktok_insight.frequency IS '平均频次';
COMMENT ON COLUMN tiktok_insight.conversions IS '转化次数';
COMMENT ON COLUMN tiktok_insight.conversion_values IS '转化价值';
COMMENT ON COLUMN tiktok_insight.cost_per_conversion IS '单次转化成本';
COMMENT ON COLUMN tiktok_insight.video_views IS '视频播放次数';
COMMENT ON COLUMN tiktok_insight.video_watched_25 IS '观看25%完成数';
COMMENT ON COLUMN tiktok_insight.video_watched_50 IS '观看50%完成数';
COMMENT ON COLUMN tiktok_insight.video_watched_75 IS '观看75%完成数';
COMMENT ON COLUMN tiktok_insight.video_watched_100 IS '观看100%完成数';
COMMENT ON COLUMN tiktok_insight.engagement_rate IS '互动率';
COMMENT ON COLUMN tiktok_insight.create_time IS '创建时间';
COMMENT ON COLUMN tiktok_insight.update_time IS '更新时间';

-- ============================================
-- 6. TikTok Webhook 日志表
-- ============================================
CREATE TABLE tiktok_webhook_log (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT,
    webhook_type VARCHAR(255) NOT NULL,
    webhook_id BIGINT,
    resource_type VARCHAR(100),
    resource_id BIGINT,
    payload JSONB,
    headers JSONB,
    processed_status VARCHAR(50) DEFAULT 'pending',
    error_message TEXT,
    received_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_time TIMESTAMP,
    retry_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tiktok_webhook_account FOREIGN KEY (account_id) REFERENCES tiktok_account(id) ON DELETE SET NULL
);

CREATE INDEX idx_tiktok_webhook_account_id ON tiktok_webhook_log(account_id);
CREATE INDEX idx_tiktok_webhook_type ON tiktok_webhook_log(webhook_type);
CREATE INDEX idx_tiktok_webhook_status ON tiktok_webhook_log(processed_status);
CREATE INDEX idx_tiktok_webhook_resource ON tiktok_webhook_log(resource_type, resource_id);
CREATE INDEX idx_tiktok_webhook_received_time ON tiktok_webhook_log(received_time);

-- 表注释
COMMENT ON TABLE tiktok_webhook_log IS 'TikTok Webhook 事件日志表';
COMMENT ON COLUMN tiktok_webhook_log.account_id IS '关联的账户 ID';
COMMENT ON COLUMN tiktok_webhook_log.webhook_type IS 'Webhook 类型';
COMMENT ON COLUMN tiktok_webhook_log.webhook_id IS 'TikTok Webhook ID';
COMMENT ON COLUMN tiktok_webhook_log.resource_type IS '资源类型: ad/video/user';
COMMENT ON COLUMN tiktok_webhook_log.resource_id IS '资源 ID';
COMMENT ON COLUMN tiktok_webhook_log.payload IS 'Webhook 负载';
COMMENT ON COLUMN tiktok_webhook_log.headers IS '请求头';
COMMENT ON COLUMN tiktok_webhook_log.processed_status IS '处理状态: pending/success/failed';
COMMENT ON COLUMN tiktok_webhook_log.error_message IS '错误信息';
COMMENT ON COLUMN tiktok_webhook_log.received_time IS '接收时间';
COMMENT ON COLUMN tiktok_webhook_log.processed_time IS '处理时间';
COMMENT ON COLUMN tiktok_webhook_log.retry_count IS '重试次数';
COMMENT ON COLUMN tiktok_webhook_log.create_time IS '创建时间';

-- ============================================
-- 7. TikTok 同步日志表
-- ============================================
CREATE TABLE tiktok_sync_log (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    sync_type VARCHAR(50) NOT NULL,
    sync_method VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    sync_status VARCHAR(50) DEFAULT 'running',
    total_count INT DEFAULT 0,
    success_count INT DEFAULT 0,
    failure_count INT DEFAULT 0,
    error_message TEXT,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP,
    duration BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tiktok_sync_account FOREIGN KEY (account_id) REFERENCES tiktok_account(id) ON DELETE CASCADE
);

CREATE INDEX idx_tiktok_sync_account_id ON tiktok_sync_log(account_id);
CREATE INDEX idx_tiktok_sync_type ON tiktok_sync_log(sync_type);
CREATE INDEX idx_tiktok_sync_status ON tiktok_sync_log(sync_status);
CREATE INDEX idx_tiktok_sync_start_time ON tiktok_sync_log(start_time);

-- 表注释
COMMENT ON TABLE tiktok_sync_log IS 'TikTok 同步日志表';
COMMENT ON COLUMN tiktok_sync_log.account_id IS '关联的账户 ID';
COMMENT ON COLUMN tiktok_sync_log.sync_type IS '同步类型: ad/video/user/insight/full';
COMMENT ON COLUMN tiktok_sync_log.sync_method IS '同步方式: scheduled/manual/webhook';
COMMENT ON COLUMN tiktok_sync_log.start_date IS '开始日期';
COMMENT ON COLUMN tiktok_sync_log.end_date IS '结束日期';
COMMENT ON COLUMN tiktok_sync_log.sync_status IS '同步状态: running/success/failed';
COMMENT ON COLUMN tiktok_sync_log.total_count IS '总记录数';
COMMENT ON COLUMN tiktok_sync_log.success_count IS '成功数量';
COMMENT ON COLUMN tiktok_sync_log.failure_count IS '失败数量';
COMMENT ON COLUMN tiktok_sync_log.error_message IS '错误信息';
COMMENT ON COLUMN tiktok_sync_log.start_time IS '开始时间';
COMMENT ON COLUMN tiktok_sync_log.end_time IS '结束时间';
COMMENT ON COLUMN tiktok_sync_log.duration IS '耗时（毫秒）';
COMMENT ON COLUMN tiktok_sync_log.create_time IS '创建时间';

-- ============================================
-- 初始化数据
-- ============================================

-- 创建示例账户配置（开发环境）
-- INSERT INTO tiktok_account (account_id, account_name, access_token, enabled, create_by)
-- VALUES ('tiktok_123456789', 'Demo Account', 'your-access-token-here', TRUE, 'system');
