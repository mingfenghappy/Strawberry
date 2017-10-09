package com.magicbeans.xgate.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class EventBean implements Serializable {

    //语言切换
    public static final int EVENT_LANGUAGE_CHANGE = 0xffa102;
    //未登录
    public static final int EVENT_NOLOGIN = 0xffa101;

    private int event;
    private Map<String, Object> map = new HashMap<>();

    public EventBean() {
    }

    public EventBean(int event) {
        this.event = event;
    }

    public EventBean put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Object get(String key) {
        return map.get(key);
    }

    //////////get & set///////////////

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
