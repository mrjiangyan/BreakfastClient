package com.breakfast.library.common;

import com.google.common.eventbus.EventBus;

/**
 * Created by Steven on 2017/6/17.
 */

public class EventBusCenter {
    private static EventBus eventBus = new EventBus();

    private EventBusCenter() {

    }

    public static EventBus getInstance() {
        return eventBus;
    }

    public static void register(Object obj) {
        eventBus.register(obj);
    }

    public static void unregister(Object obj) {
        eventBus.unregister(obj);
    }

    public static void post(Event event) {
        eventBus.post(event);
    }
}
