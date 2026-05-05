package com.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * TikTok 连接器启动类
 *
 * @author Livepulse
 * @since 2.0
 */
@SpringBootApplication
@EnableScheduling
public class TiktokConnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiktokConnectorApplication.class, args);
    }
}
