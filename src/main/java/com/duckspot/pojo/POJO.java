/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duckspot.pojo;

import org.json.JSONObject;

/**
 * Provides a method of copying between POJOs and JSONObjects.
 * 
 * @author Peter Dobson
 */
public class POJO {
    
    /**
     * returns an object wrapped in the appropriate Wrapper.
     * 
     * @param object
     * @return wrapped object implements Map&lt;String,Object&gt;
     */
    public static Wrapper wrap(Object object) {
        if (object instanceof JSONObject) {
            return new WrapJSONObject((JSONObject)object);
        } else {
            return new WrapPOJO(object);
        }        
    }
    
    /**
     * Uses wrappers to copy values from one object to another.
     * 
     * @param dest
     * @param source 
     */
    public static void copySilent(Object dest, Object source) {
        
        Wrapper copyFrom = wrap(source);
        Wrapper copyTo = wrap(dest);
        for (String key: copyFrom.keySet()) {
            if (copyTo.containsPutKey(key)) {
                copyTo.put(key, copyFrom.get(key));
            }
        }
    }
    
    /**
     * Uses wrappers to copy values from one object to another.
     * 
     * @param dest
     * @param source 
     */
    public static void copy(Object dest, Object source) {
        Wrapper copyFrom = wrap(source);
        Wrapper copyTo = wrap(dest);
        for (String key: copyFrom.keySet()) {
            if (!key.equals("class")) {
                copyTo.put(key, copyFrom.get(key));
            }
        };
    }
    
    /**
     * Creates a JSONObject that contains values from source.  Or simply 
     * returns it's argument if object is already a JSONObject.
     * 
     * @param object
     * @return 
     */
    public static JSONObject toJSONObject(Object object) {
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        } else {
            JSONObject result = new JSONObject();
            copy(result, object);
            return result;
        }
    }    
}
