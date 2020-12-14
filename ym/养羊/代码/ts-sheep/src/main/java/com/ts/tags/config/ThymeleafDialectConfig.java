package com.ts.tags.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ts.tags.define.SysDialect;
/**
 * Thymeleaf配置
*/
@Configuration
public class ThymeleafDialectConfig {
    /**
     * 自定义方言注入
     * 字典下拉框下拉框
     * @return
     */
    @Bean
    public SysDialect sysDialect() {
        return new SysDialect();
    }
}