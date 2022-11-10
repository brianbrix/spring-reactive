package com.me.reactivetutorial.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericAppEvent<T> {
    private T item;
    protected boolean success;

}
