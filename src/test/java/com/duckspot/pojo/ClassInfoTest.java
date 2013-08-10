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
public class ClassInfoTest {
    
    public ClassInfoTest() {
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
     * Test of getClassInfo method, of class ClassInfo.
     */
    @Test
    public void testGetClassInfo() {
        System.out.println("getClassInfo");
        Class clazz = Object.class;
        ClassInfo result = ClassInfo.getClassInfo(clazz);
        assertEquals(1, result.getters.size());
        assertEquals(0, result.setters.size());
        clazz = TestClass.class;
        result = ClassInfo.getClassInfo(clazz);
        assertEquals(4, result.getters.size());
        assertEquals(3, result.setters.size());
        assertEquals(String.class,result.getters.get("URL").getReturnType());
    }
}