package com.stateManger;

/**
 * 紀錄發送的通知訊息
 */
public class StateEntity {
    APP_STATE_TYPE tag;
    Object entity;


    public StateEntity(APP_STATE_TYPE tag, Object entity) {
        this.tag = tag;
        this.entity = entity;
    }

    public StateEntity() {
    }

    public StateEntity setTag(APP_STATE_TYPE tag) {
        this.tag = tag;
        return this;
    }

    public StateEntity setEntity(Object entity) {
        this.entity = entity;
        return this;
    }

    public APP_STATE_TYPE getTag() {
        return tag;
    }

    public Object getEntity() {
        return entity;
    }
}
