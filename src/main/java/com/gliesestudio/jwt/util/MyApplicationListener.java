package com.gliesestudio.jwt.util;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Common listener for the application
 *
 * @author MazidulIslam
 * @since 10-12-2023
 */
@Component
@Slf4j
public class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * Listen to application startup
     *
     * @param event {@link ApplicationReadyEvent}
     */
    @Override
    public void onApplicationEvent(@Nullable ApplicationReadyEvent event) {
        log.info("\033[1;32mApplication started succesfully\033[m");
    }

}
