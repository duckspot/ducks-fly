package com.duckspot.pojo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Scans methods in a Class for getters and setters and builds two maps, 
 * one with setters by property name, and one with getters by property name.
 * 
 * @author Peter Dobson
 */
class ClassInfo {
        
    private static Map<Class,ClassInfo> cache = new HashMap<>();
    
    /**
     * returns ClassInfo object for Class.  Uses simple static cache to 
     * increase performance.
     * 
     * @param clazz
     * @return 
     */
    public static ClassInfo getClassInfo(Class clazz) {
        
        if (cache.containsKey(clazz)) {            
            return cache.get(clazz);            
        } else {
            ClassInfo result = new ClassInfo(clazz);
            cache.put(clazz, result);
            return result;
        }
    }
    
    /**
     * Map of getter methods by property name.
     */
    Map<String,Method> getters = new HashMap<>();
    /**
     * Map of setter methods by property name.
     */
    Map<String,Method> setters = new HashMap<>();    

    private static boolean isSetter(Method m) {        
        return m.getName().startsWith("set") 
                && m.getParameterTypes().length == 1;
    }
    
    private static boolean isGetter(Method m) {        
        String name = m.getName();
        return (name.startsWith("is") || name.startsWith("get")) 
                && m.getParameterTypes().length == 0
                && !m.getReturnType().equals(Void.TYPE);
    }
        
    private static String propertyName(Method m) {        
        
        String name = m.getName();
        int startIndex = (name.startsWith("is") ? 2 : 3);
        
        // special rule for 2 caps after is/get/set as in getURL()
        if (name.length() > startIndex+2) {
            String firstTwo = name.substring(startIndex, startIndex+2);
            if (firstTwo.toUpperCase().equals(firstTwo)) {
                // result is unaltered string after is/get/set (URL)
                return name.substring(startIndex);
            }
        }
        // otherwise result is toLowerCase of first character
        String result = name.substring(startIndex, startIndex+1).toLowerCase();        
        // and the rest of the string with no case changes
        if (name.length() > startIndex+1) {
            result += name.substring(startIndex+1);                               
        }
        return result;
    }
    
    /**
     * Constructor is private, use ClassInfo.getClassInfo(Class) instead.
     * 
     * @param clazz
     */
    private ClassInfo(Class clazz) {
        
        for (Method method: clazz.getMethods()) {
                        
            if (isSetter(method)) {
                setters.put(propertyName(method), method);                
            }
            else if (isGetter(method)) {
                getters.put(propertyName(method), method);                
            }
        }
    }        
}
