package com.pl.pro.sncsrv.config.server.heartbeat;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 功能描述：
 *
 * @author 88403636_徐航
 * @since 16:34 2019/3/14
 */
@Component
public class StartServerListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(event.getApplicationContext().getParent() == null) {
            new Server(9995, 30).run();
            System.err.println("begin start");
        }
    }
}
