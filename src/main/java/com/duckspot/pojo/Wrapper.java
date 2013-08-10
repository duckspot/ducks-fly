/* /com/duckspot/pojo/Wrapper.java
 * 
 */
package com.duckspot.pojo;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/**
 * An abstract class that provides common code for WrapJSONObject &amp; 
 * WrapPOJO.  All Wrapper object implement at Map&lt;String,Object&lt; that
 * allowed attributes of properties of a wrapped object to be retrieved and
 * modified.
 * 
 * @author Peter Dobson
 */
public abstract class Wrapper implements Map<String,Object> {

    Object object;
    
    Wrapper(Object object) {
        this.object = object;
    }
    
    @Override
    public int size() {        
        return keySet().size();
    }

    @Override
    public boolean isEmpty() {
        return keySet().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keySet().contains(key);
    }

    abstract public Set<String> putKeySet();
    
    public boolean containsPutKey(Object key) {
        return putKeySet().contains(key);
    }
    
    @Override
    public boolean containsValue(Object value) {        
        for(String key: keySet()) {
            Object wrappedValue = get(key);
            if (value == null ? wrappedValue == null 
                              : value.equals(wrappedValue)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void putAll(Map<? extends String, ? extends Object> m,
                       boolean silent) {
        
        for (String key: m.keySet()) {            
            if (!key.equals("class")) {
                if (!silent || this.containsPutKey(key)) {
                    this.put(key, m.get(key));
                }
            }
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        // default silent = true
        putAll(m, true);
    }
    
    @Override
    public void clear() {        
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Collection<Object> values() {
        Collection<Object> result = new ArrayList<>();
        for(String key: keySet()) {            
            result.add(get(key));
        }
        return result;
    }
    
    @Override
    public Set<Entry<String, Object>> entrySet() {
        Set<Entry<String, Object>> result = new HashSet<>();
        for(String key: keySet()) {            
            result.add(new AbstractMap.SimpleEntry(key, get(key)));
        }
        return result;
    }    
}
