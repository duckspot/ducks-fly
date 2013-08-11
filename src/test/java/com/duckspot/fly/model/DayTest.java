package com.duckspot.fly.model;

import java.util.Date;
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
 * @author Peter Dobson
 */
public class DayTest implements ChangeListener {
    
    static final Settings settings = new Settings();
    static final DtUtil dtUtil = settings.getDtUtil();
    static final Date today = dtUtil.getToday();
    
    Day instance;
    int state;
    
    public DayTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Day(settings, today);
        state = 0;
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetDate() {
        System.out.println("getDate");
        Date expResult = today;
        Date result = instance.getDate();
        assertEquals(expResult, result);        
    }

    /**
     * minutesSinceMidnight in my local TimeZone.
     * @return 
     */
    private static int thisMinute() {
        long result = dtUtil.getThisMinute().getTime()
                    - dtUtil.getToday().getTime();
        result /= 1000L * 60L;
        return (int)result;
    }
    
    @Test
    public void testNextTime() {
        System.out.println("nextTime");
        int expResult = Math.max(settings.getDayStartMinute(),thisMinute());
        int result = instance.nextTime();
        assertEquals(expResult, result);
    }

    @Test
    public void testNewItem() {
        System.out.println("newItem");
        int beginMinute = instance.nextTime();
        Item result = instance.newItem();
        assertEquals(today, result.getDate());
        assertEquals(beginMinute, result.getBeginMinute());
        result = instance.newItem();
        assertEquals(today, result.getDate());
        beginMinute += result.getDurationMinutes();
        assertEquals(beginMinute, result.getBeginMinute());
        result = instance.newItem();
        assertEquals(today, result.getDate());
        beginMinute += result.getDurationMinutes();
        assertEquals(beginMinute, result.getBeginMinute());
    }

    @Test
    public void testGetLastEvent() {
        System.out.println("getLastEvent");
        instance.newItem();
        Item expResult = instance.newItem();
        Item result = instance.getLastItem();
        assertEquals(expResult, result);        
    }

    @Test
    public void testGetCount() {
        System.out.println("getCount");
        instance.newItem();
        int expResult = 1;
        int result = instance.getCount();
        assertEquals(expResult, result);        
    }

    @Test
    public void testGetEvent() {
        System.out.println("getEvent");
        int index = 0;
        Item expResult = instance.newItem();
        Item expResult2 = instance.newItem();
        Item result = instance.getItem(index);
        assertEquals(expResult, result);
        index = 1;
        result = instance.getItem(index);
        assertEquals(expResult2, result);
    }

    @Test
    public void testIsToday() {
        System.out.println("isToday");
        boolean expResult = true;
        boolean result = instance.isToday();
        instance = new Day(settings, dtUtil.getTomorrow());
        expResult = false;
        result = instance.isToday();
        assertEquals(expResult, result);        
    }

    @Test
    public void testGetLastItem() {
        System.out.println("getLastItem");
        Item expResult = instance.newItem();
        Item result = instance.getLastItem();        
        assertEquals(expResult, result);
        expResult = instance.newItem();
        result = instance.getLastItem();        
        assertEquals(expResult, result);
    }

    @Test
    public void testGetItem() {
        System.out.println("getItem");
        int index = 0;
        Item expResult = instance.newItem();
        Item result = instance.getItem(index);
        assertEquals(expResult, result);
        Item expResult2 = instance.newItem();
        result = instance.getItem(index);
        assertEquals(expResult, result);
        index = 1;
        result = instance.getItem(index);
        assertEquals(expResult2, result);
    }   

    @Test
    public void testRemoveItem_Item() {
        System.out.println("removeItem");
        Item item = instance.newItem();
        instance.removeItem(item);
        assertEquals(0, instance.getCount());
    }

    @Test
    public void testRemoveItem_int() {
        System.out.println("removeItem");
        int index = 0;
        Item expResult = instance.newItem();
        Item result = instance.removeItem(index);
        assertEquals(expResult, result);
        assertEquals(0, instance.getCount());
    }

    @Test
    public void testStateChanged() {
        System.out.println("stateChanged");
        instance.addChangeListener(this);
        assertEquals(0, state);        
        Item item = instance.newItem();
        assertEquals(1, state);
        item.save();
        assertEquals(2, state);
    }
    
    @Test
    public void testAddItem() {
        System.out.println("addItem");
        Item item = instance.newItem();
        Day instance2 = new Day(settings, dtUtil.getTomorrow());
        instance2.addChangeListener(this);
        instance2.addItem(item);
        assertEquals(dtUtil.getTomorrow(), item.getDate());
        assertEquals(0, instance.getCount());
        assertEquals(1, instance2.getCount());        
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        state++;
    }
}