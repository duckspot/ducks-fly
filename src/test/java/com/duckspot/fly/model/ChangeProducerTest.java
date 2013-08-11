/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duckspot.fly.model;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
public class ChangeProducerTest implements ChangeListener {
    
    int state;
    
    public ChangeProducerTest() {
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
     * Test of addChangeListener method, of class ChangeProducer.
     */
    @Test
    public void testAddChangeListener() {
        System.out.println("addChangeListener");
        ChangeListener listener = this;
        ChangeProducer instance = new ChangeProducerImpl();
        instance.addChangeListener(listener);
        instance.fireStateChanged();
        assertEquals(1, state);
    }

    /**
     * Test of removeChangeListener method, of class ChangeProducer.
     */
    @Test
    public void testRemoveChangeListener() {
        System.out.println("removeChangeListener");
        ChangeListener listener = this;
        ChangeProducer instance = new ChangeProducerImpl();
        instance.addChangeListener(listener);
        instance.removeChangeListener(listener);
        instance.fireStateChanged();
        assertEquals(0, state);
    }

    /**
     * Test of fireStateChanged method, of class ChangeProducer.
     */
    @Test
    public void testFireStateChanged_ChangeEvent() {
        System.out.println("fireStateChanged");
        ChangeEvent event = new ChangeEvent(this);
        ChangeProducer instance = new ChangeProducerImpl();
        instance.addChangeListener(this);
        instance.fireStateChanged(event);
        assertEquals(1, state);
    }

    /**
     * Test of fireStateChanged method, of class ChangeProducer.
     */
    @Test
    public void testFireStateChanged_0args() {
        System.out.println("fireStateChanged");
        ChangeProducer instance = new ChangeProducerImpl();
        instance.addChangeListener(this);
        instance.fireStateChanged();
        assertEquals(1, state);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        state++;
    }

    public class ChangeProducerImpl extends ChangeProducer {
    }
}