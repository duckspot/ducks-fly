package com.duckspot.pojo;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pdobson
 */
public class WrapPOJOTest {
    
    TestClass test, test2;
    String testURL;
    WrapPOJO instance, instance2;
    
    
    public WrapPOJOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        test = new TestClass();
        test.setA(1);
        testURL = "http://duckspot.com";
        test.setURL(testURL);
        instance = new WrapPOJO(test);
        test2 = new TestClass();
        instance2 = new WrapPOJO(test2);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of get method, of class WrapPOJO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Object key = "a";
        Object expResult = 1;
        Object result = instance.get(key);
        assertEquals(expResult, result);
        key = "URL";
        expResult = testURL;        
        result = instance.get(key);
        assertEquals(expResult, result);
        key = "b";
        expResult = false;
        result = instance.get(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of put method, of class WrapPOJO.
     */
    @Test
    public void testPut() {
        System.out.println("put");
        String key = "b";
        Object value = true;
        Object expResult = false;
        Object result = instance.put(key, value);
        assertEquals(expResult, result);
        assertEquals(value, instance.get(key));
    }

    /**
     * Test of keySet method, of class WrapPOJO.
     */
    @Test
    public void testKeySet() {
        System.out.println("keySet");
        Set result = instance.keySet();
        assertEquals(4, result.size());
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("URL"));
    }

    /**
     * Test of size method, of class Wrapper.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        int expResult = 4;
        int result = instance.size();
        assertEquals(expResult, result);        
    }

    /**
     * Test of isEmpty method, of class Wrapper.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);        
    }

    /**
     * Test of containsKey method, of class Wrapper.
     */
    @Test
    public void testContainsKey() {
        System.out.println("containsKey");
        Object key = "b";
        boolean expResult = true;
        boolean result = instance.containsKey(key);
        assertEquals(expResult, result);
        key = "c";
        expResult = false;
        result = instance.containsKey(key);
    }

    /**
     * Test of containsValue method, of class Wrapper.
     */
    @Test
    public void testContainsValue() {
        System.out.println("containsValue");
        Object value = 1;
        boolean expResult = true;
        boolean result = instance.containsValue(value);
        assertEquals(expResult, result);
        value = 2;
        expResult = false;
        result = instance.containsValue(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of remove method, of class Wrapper.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Object key = "a";
        Object result = null;        
        try {
            result = instance.remove(key);
        } catch (Exception ex) {
            result = ex;
        }
        assertTrue(result instanceof UnsupportedOperationException);
    }

    /**
     * Test of putAll method, of class Wrapper.
     */
    @Test
    public void testPutAll() {
        System.out.println("putAll");
        Map<? extends String, ? extends Object> m = instance;      
        instance2.putAll(m);
        assertEquals(1,test2.getA());
        assertEquals(false,test2.isB());
        assertEquals(testURL,test2.getURL());
    }

    /**
     * Test of clear method, of class Wrapper.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Object result = null;        
        try {
            instance.clear();
        } catch (Exception ex) {
            result = ex;
        }
        assertTrue(result instanceof UnsupportedOperationException);
    }

    /**
     * Test of values method, of class Wrapper.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        Collection result = instance.values();
        assertEquals(4, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(false));
        assertTrue(result.contains(testURL));
        assertTrue(result.contains(TestClass.class));
    }

    /**
     * Test of entrySet method, of class Wrapper.
     */
    @Test
    public void testEntrySet() {
        System.out.println("entrySet");
        Set<Entry<String,Object>> result = instance.entrySet();
        assertEquals(4, result.size());
        for (Entry entry : result) {
            if ("a".equals(entry.getKey())) {
                assertEquals(1, entry.getValue());
            } else
            if ("b".equals(entry.getKey())) {
                assertEquals(false, entry.getValue());
            } else
            if ("URL".equals(entry.getKey())) {
                assertEquals(testURL, entry.getValue());
            } else
            if ("class".equals(entry.getKey())) {
                assertEquals(TestClass.class, entry.getValue());
            }
        }        
    }

    /**
     * Test of putKeySet method, of class WrapPOJO.
     */
    @Test
    public void testPutKeySet() {
        System.out.println("putKeySet");
        Set result = instance.putKeySet();
        assertEquals(3, result.size());
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("URL"));        
    }

    /**
     * Test of toString method, of class WrapPOJO.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "WrapPOJO { class: TestClass, "
                + "URL: \"http://duckspot.com\", a: 1, b: false }";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}