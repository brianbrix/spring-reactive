package com.me.reactivetutorial.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GenericPublisher<T> implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;
    @Override
//    @PostConstruct
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        System.out.println("publisher: " + applicationEventPublisher);
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(final T item, boolean success)
    {
        GenericAppEvent<T> genericAppEvent = new GenericAppEvent<>(item, success);
        this.applicationEventPublisher.publishEvent(genericAppEvent);
    }


}
