package com.breakfast.library.common;

/**
 * Created by Steven on 2017/6/17.
 */

public class Event<T> {



    private EventType eventType;

    public T getData() {
        return data;
    }

    public Event<T> setData(T data) {
        this.data = data;
        return this;
    }

    private T data;



    private EventCategory eventCategory;

    public Event( EventCategory eventCategory,EventType eventType) {
        this.eventType = eventType;
        this.eventCategory = eventCategory;
    }


    public EventType getEventType() {
        return eventType;
    }


    public EventCategory getEventCategory() {
        return eventCategory;
    }



    public enum EventType
    {
        DELETE,
        UPDATE,
        ADD,
    }

    public enum EventCategory
    {
        COURSE_CLASS,
        STUDENT_RECHARGE,
        //排班
        SCHEDULE,
        //课程报名
        COURSE_CLASS_STUDENT_REGISTRATION,
        TEACHER_CHARGE,
        TEACHER,
        COURSE,
        STUDENT
    }
}
