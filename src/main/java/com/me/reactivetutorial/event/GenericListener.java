package com.me.reactivetutorial.event;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class GenericListener {

    @EventListener(condition = "#event.success")
    public void handleSuccessfulEvent(@NonNull GenericAppEvent<String> event)
    {
        log.info("RECEIVED EVENT: {}", event.getItem());
    }
}
