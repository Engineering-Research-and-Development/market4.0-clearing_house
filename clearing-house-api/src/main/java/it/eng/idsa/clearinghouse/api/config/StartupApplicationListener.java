package it.eng.idsa.clearinghouse.api.config;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Log
@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("StartupApplication");
        try {
            log.info("Application Started!");
        } catch (Exception e) {
            log.severe("Error in " + getClass().getCanonicalName() + " : " + e.getMessage());
        }
    }
}
