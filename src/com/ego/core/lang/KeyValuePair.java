package com.ego.core.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单键值对
 *
 * @author 95
 */
public class KeyValuePair {

    private Object key;
    private Object value;
    private Map<String, Object> key2Value;

    public KeyValuePair() {
    }

    public KeyValuePair(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Map<String, Object> getKey2Value() {
        return this.key2Value;
    }

    public KeyValuePair addKey2Value(String key, Object val) {
        if (this.key2Value == null) {
            this.key2Value = new HashMap();
        }
        this.key2Value.put(key, val);
        return this;
    }
}
