package com.ivo.sapweb;

import com.ivo.sapweb.sap.core.SapConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author wangjian
 */
@SpringBootApplication
public class SapwebApplication {

    private final static Logger logger = LoggerFactory.getLogger(SapwebApplication.class);


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SapwebApplication.class, args);

        String[] profiles = context.getEnvironment().getActiveProfiles();
        String active_profile = "dev";
        if(profiles.length > 0) {
            active_profile = profiles[0];
        }
        logger.info("当前运行环境为 " + active_profile + " 环境");
        init(active_profile);
    }

    /**
     * 初始化，建立sap连接池
     */
    public static void init(String active_profile) {
        SapConnectionPool.setEnv(active_profile);
        SapConnectionPool.RegetJocodestination();
    }
}
