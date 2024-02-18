package com.gliesestudio.os.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main spring boot initialization class
 *
 * @author MazidulIslam
 * @since 08-12-2023
 */
@SpringBootApplication
public class JWTSpringSecurityApplication {

    /**
     * Main starter method of the application which initializes spring boot
     *
     * @param args list of arguments passed while application initialization
     */
    public static void main(String[] args) {
        SpringApplication.run(JWTSpringSecurityApplication.class, args);
    }

}
