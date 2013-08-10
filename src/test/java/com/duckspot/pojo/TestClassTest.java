/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duckspot.pojo;

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
public class TestClassTest {
    
    public TestClassTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setA method, of class TestClass.
     */
    @Test
    public void testSetA() {
        System.out.println("setA");
        int a = 0;
        TestClass instance = new TestClass();
        instance.setA(a);        
        assertEquals(a, instance.getA());        
    }

    /**
     * Test of getA method, of class TestClass.
     */
    @Test
    public void testGetA() {
        System.out.println("getA");
        TestClass instance = new TestClass();
        int expResult = 0;
        int result = instance.getA();
        assertEquals(expResult, result);                
    }

    /**
     * Test of setURL method, of class TestClass.
     */
    @Test
    public void testSetURL() {
        System.out.println("setURL");
        String URL = "http://duckspot.com/";
        TestClass instance = new TestClass();
        instance.setURL(URL);
        assertEquals(URL, instance.getURL());
    }

    /**
     * Test of getURL method, of class TestClass.
     */
    @Test
    public void testGetURL() {
        System.out.println("getURL");
        TestClass instance = new TestClass();
        String expResult = null;
        String result = instance.getURL();
        assertEquals(expResult, result);
    }

    /**
     * Test of setB method, of class TestClass.
     */
    @Test
    public void testSetB() {
        System.out.println("setB");
        boolean b = true;
        TestClass instance = new TestClass();
        instance.setB(b);
        assertEquals(b, instance.isB());
    }

    /**
     * Test of isB method, of class TestClass.
     */
    @Test
    public void testIsB() {
        System.out.println("isB");
        TestClass instance = new TestClass();
        boolean expResult = false;
        boolean result = instance.isB();
        assertEquals(expResult, result);
    }
}