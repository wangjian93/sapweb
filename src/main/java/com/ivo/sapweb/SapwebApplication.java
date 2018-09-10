package com.ivo.sapweb;

import com.ivo.sapweb.sap.core.SapConnectionPool;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangjian
 */
@SpringBootApplication
public class SapwebApplication {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(SapwebApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(SapwebApplication.class, args);

        init();
    }

    public static void init() {
        //建立sap连接池
        SapConnectionPool.RegetJocodestination();
        logger.info("SAPJCO 连接池建立");
    }
}
