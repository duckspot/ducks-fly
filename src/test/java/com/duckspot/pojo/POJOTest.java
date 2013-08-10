/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duckspot.pojo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
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
public class POJOTest {
    
    TestClass test, test2;    
    String testURL;
    WrapPOJO instance;
    
    static class TestClass2 {

        private int a;
        private String URL;
        private boolean b;

        public void setA(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        public boolean isB() {
            return b;
        }    
    }
    
    TestClass2 testC2;
    
    public POJOTest() {
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
        testC2 = new TestClass2();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of wrap method, of class POJO.
     */
    @Test
    public void testWrap() {
        System.out.println("wrap");
        Object object = test;        
        Wrapper result = POJO.wrap(object);        
        assertEquals(test.getA(), result.get("a"));
        assertEquals(test.isB(), result.get("b"));
        assertEquals(test.getURL(), result.get("URL"));        
    }

    /**
     * Test of copy method, of class POJO.
     */
    @Test
    public void testCopy() {
        System.out.println("copy");
        Object dest = test2;
        Object source = test;
        POJO.copy(dest, source);
        assertEquals(test.getA(), test2.getA());
        assertEquals(test.isB(), test2.isB());
        assertEquals(test.getURL(), test2.getURL());
    }

    /**
     * Test of toJSONObject method, of class POJO.
     */
    @Test
    public void testToJSONObject() throws JSONException {
        System.out.println("toJSONObject");
        Object object = test;        
        JSONObject result = POJO.toJSONObject(object);        
        assertEquals(test.getA(), result.getInt("a"));
        assertEquals(test.isB(), result.getBoolean("b"));
        assertEquals(test.getURL(), result.getString("URL"));
    }

    /**
     * Test of copySilent method, of class POJO.
     */
    @Test
    public void testCopySilent() {
        System.out.println("copySilent");
        Object dest = testC2;
        Object source = test;
        POJO.copySilent(dest, source);
        assertEquals(test.getA(), testC2.getA());
        assertEquals(test.isB(), testC2.isB());
    }
}