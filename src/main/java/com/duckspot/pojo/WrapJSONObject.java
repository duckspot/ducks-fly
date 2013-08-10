package com.duckspot.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Wraps a simple JSONObject so that it can be accessed like a Map.
 * 
 * @author Peter Dobson
 */
public class WrapJSONObject extends Wrapper implements Map<String,Object> {

    public WrapJSONObject(JSONObject object) {
        super(object);
    }
    
    @Override
    public Object get(Object key) {
        try {
            return ((JSONObject)object).get((String)key);
        } catch (JSONException ex) {
            throw new Error("unexpected exception", ex);
        }
    }

    @Override
    public Object put(String key, Object value) {
        try {
            ((JSONObject)object).put(key, value);
        } catch (JSONException ex) {
            throw new Error("unexpected exception", ex);
        }
        return null;
    }

    @Override
    public Set<String> keySet() {
        Set<String> result = new HashSet();
        Iterator<String> it = ((JSONObject)object).keys();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result;
    }
    
    @Override
    public String toString() {
        return ((JSONObject)object).toString();
    }

    @Override
    public Set<String> putKeySet() {
        return keySet();
    }
}
