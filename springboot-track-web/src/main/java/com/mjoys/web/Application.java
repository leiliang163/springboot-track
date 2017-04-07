package com.mjoys.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Application.
 */
@Configuration
@ImportResource({ "spring/spring.xml" })
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
public class Application extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * main
     * 
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = null;
        try {
            long start = System.currentTimeMillis();
            SpringApplication sa = new SpringApplication(Application.class);
            Map<String, Object> defaultMap = new HashMap<>();
            defaultMap.put("spring.config.location", "classpath:tuia-media.properties");
//            defaultMap.put("spring.config.location", "http://dev.config.duibar.com:1024/tuia-media-dev.properties");
            sa.setDefaultProperties(defaultMap);
            ctx = sa.run(args);
            Thread.sleep(Long.MAX_VALUE);
            long period = System.currentTimeMillis() - start;
            log.error("Application start successfully in " + period + " ms.");
        } catch (Exception e) {
            log.error("Application start error :", e);
            System.exit(-1);
        } finally {
            if (ctx != null) {
                ctx.close();
            }
        }
    }

}
