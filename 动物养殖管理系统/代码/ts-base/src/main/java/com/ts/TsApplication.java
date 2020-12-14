package com.ts;

import java.lang.reflect.Method;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ts.common.utils.SpringUtils;
import com.ts.system.base.BaseInterface;

@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.ts.*.dao")
@SpringBootApplication
@EnableCaching
public class TsApplication extends SpringBootServletInitializer {

    private static Logger _log = LoggerFactory.getLogger(TsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TsApplication.class, args);
        // 系统入口初始化
        Map<String, BaseInterface> baseInterfaceBeans = SpringUtils.getBeansOfType(BaseInterface.class);
        for (Object service : baseInterfaceBeans.values()) {
            _log.debug(">>>>> {}.init()", service.getClass().getName());
            try {
                Method init = service.getClass().getMethod("init");
                init.invoke(service);
            } catch (Exception e) {
                _log.error("初始化BaseInterface的init方法异常", e);
                e.printStackTrace();
            }
        }
        _log.debug("ts-base启动成功    ");
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        // 注意这里要指向原先用main方法执行的Application启动类
//        return builder.sources(TsApplication.class);
//    }
}
