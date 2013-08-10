/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duckspot.pojo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

/**
 * Wraps a simple POJO so that it can be accessed like a Map.
 * 
 * @author Peter Dobson
 */
public class WrapPOJO extends Wrapper {
    
    ClassInfo info;
    
    public WrapPOJO(Object object) {
        super(object);
        this.info = ClassInfo.getClassInfo(object.getClass());
    }
    
    @Override
    public Object get(Object key) {
        Method getter = info.getters.get(key);
        if (getter == null) {
            return null;
        }            
        try {
            return getter.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new Error("getter exception", ex);
        }
    }

    @Override
    public Object put(String key, Object value) {
        
        Object previousValue = null;        
        Method setMethod = info.setters.get(key);
        if (setMethod == null) {
            if (containsKey(key)) {
                throw new IllegalArgumentException(key + " is read only");
            } else {
                throw new IllegalArgumentException(key+" property not found");
            }
        }
        
        if (containsKey(key)) {
            previousValue = get(key);
        }
        try {
            setMethod.invoke(object, value);            
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new Error("setter exception", ex);
        }
        return previousValue;
    }

    @Override
    public Set<String> keySet() {
        return info.getters.keySet();
    }
    
    @Override
    public Set<String> putKeySet() {
        return info.setters.keySet();
    }        
    
    private static String value(Object o) {
        if (o == null) {
            return "null";
        } 
        else if (o instanceof String) {            
            return "\""+StringEscapeUtils.escapeJava((String)o)+"\"";
        }
        return o.toString();
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("WrapPOJO { class: ");
        sb.append(object.getClass().getSimpleName());
        Object[] keys = keySet().toArray();
        Arrays.sort(keys);
        for (int i=0; i<keys.length; i++) {
            String key = (String)keys[i];
            if (!key.equals("class")) {
                sb.append(", ").append(key)
                  .append(": ").append(value(get(key)));
            }
        }
        sb.append(" }");
        return sb.toString();        
    }
}
