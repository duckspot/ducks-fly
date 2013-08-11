package com.duckspot.fly.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class ItemTest implements ChangeListener {
    
    static Settings settings = new Settings();
    static DtUtil dtUtil = settings.getDtUtil();
    static Date testDate = new GregorianCalendar(2013, 7, 1).getTime();
    
    Item instance;
    int state;
    
    public ItemTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Item(settings);
        state = 0;
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = null;
        String result = instance.getName();
        assertEquals(expResult, result);        
    }

    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "item";
        instance.setName(name);
        String expResult = name;
        String result = instance.getName();
        assertEquals(expResult, result);        
    }

    @Test
    public void testGetURL() {
        System.out.println("getURL");
        String expResult = null;
        String result = instance.getURL();
        assertEquals(expResult, result);        
    }

    @Test
    public void testSetURL() {
        System.out.println("setURL");
        String url = "http://duckspot.com/";
        instance.setURL(url);
        String expResult = url;
        String result = instance.getURL();
        assertEquals(expResult, result);                
    }

    @Test
    public void testGetDurationMinutes() {
        System.out.println("getDurationMinutes");
        int expResult = 10;
        int result = instance.getDurationMinutes();
        assertEquals(expResult, result);        
    }

    @Test
    public void testSetDurationMinutes() {
        System.out.println("setDurationMinutes");
        int durationMinutes = 20;
        instance.setDurationMinutes(durationMinutes);
        int expResult = durationMinutes;
        int result = instance.getDurationMinutes();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetBeginMinute() {
        System.out.println("getBeginMinute");
        int expResult = 0;
        int result = instance.getBeginMinute();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetBeginMinute() {
        System.out.println("setBeginMinute");
        int beginMinute = 8*60;
        instance.setBeginMinute(beginMinute);
        int expResult = beginMinute;
        int result = instance.getBeginMinute();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsBeginSet() {
        System.out.println("isBeginSet");
        boolean expResult = false;
        boolean result = instance.isBeginSet();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetBeginSet() {
        System.out.println("setBeginSet");
        boolean beginSet = true;
        instance.setBeginSet(beginSet);
        boolean expResult = beginSet;
        boolean result = instance.isBeginSet();
        assertEquals(expResult, result);        
    }

    @Test
    public void testGetEarliestStartMinute() {
        System.out.println("getEarliestStartMinute");
        int result = instance.getEarliestStartMinute();
        assertTrue(result < 0);
    }

    @Test
    public void testSetEarliestStartMinute() {
        System.out.println("setEarliestStartMinute");
        int earliestStartMinute = 8*60;
        instance.setEarliestStartMinute(earliestStartMinute);
        int expResult = earliestStartMinute;
        int result = instance.getEarliestStartMinute();
        assertEquals(expResult, result);        
    }

    @Test
    public void testGetLatestEndMinute() {
        System.out.println("getLatestEndMinute");
        int result = instance.getLatestEndMinute();
        assertTrue(result < 0);        
    }

    @Test
    public void testSetLatestEndMinute() {
        System.out.println("setLatestEndMinute");
        int latestEndMinute = 12*60;
        instance.setLatestEndMinute(latestEndMinute);
        int expResult = latestEndMinute;
        int result = instance.getLatestEndMinute();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDate() {
        System.out.println("getDate");
        Date expResult = null;
        Date result = instance.getDate();
        assertEquals(expResult, result);        
    }

    @Test
    public void testSetDate() {
        System.out.println("setDate");
        Calendar today = Calendar.getInstance();
        Util.clearTime(today);
        Date date = today.getTime();        
        instance.setDate(date);
        Date expResult = date;
        Date result = instance.getDate();
        assertEquals(expResult, result);        
    }

    @Test
    public void testIsKey() {
        System.out.println("isKey");
        boolean expResult = false;
        boolean result = instance.isKey();
        assertEquals(expResult, result);        
    }

    @Test
    public void testSetKey() {
        System.out.println("setKey");
        boolean key = true;
        instance.setKey(key);
        boolean expResult = key;
        boolean result = instance.isKey();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsFollowUp() {
        System.out.println("isFollowUp");
        boolean expResult = false;
        boolean result = instance.isFollowUp();
        assertEquals(expResult, result);        
    }

    @Test
    public void testSetFollowUp() {
        System.out.println("setFollowUp");
        boolean followUp = true;
        instance.setFollowUp(followUp);
        boolean expResult = followUp;
        boolean result = instance.isFollowUp();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsRecur() {
        System.out.println("isRecur");
        boolean expResult = false;
        boolean result = instance.isRecur();
        assertEquals(expResult, result);        
    }

    @Test
    public void testSetRecur() {
        System.out.println("setRecur");
        boolean recur = true;
        instance.setRecur(recur);
        boolean expResult = recur;
        boolean result = instance.isRecur();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetRecurType() {
        System.out.println("getRecurType");
        RecurType expResult = null;
        RecurType result = instance.getRecurType();
        assertEquals(expResult, result);        
    }

    @Test
    public void testSetRecurType() {
        System.out.println("setRecurType");
        RecurType recurType = RecurType.STANDARD;
        instance.setRecurType(recurType);
        RecurType expResult = recurType;
        RecurType result = instance.getRecurType();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMarvel() {
        System.out.println("getMarvel");
        float expResult = 0.0F;
        float result = instance.getMarvel();
        assertEquals(expResult, result, 0.0);        
    }

    @Test
    public void testSetMarvel() {
        System.out.println("setMarvel");
        float marvel = 0.1F;
        instance.setMarvel(marvel);
        float expResult = marvel;
        float result = instance.getMarvel();
        assertEquals(expResult, result, 0.0);        
    }

    @Test
    public void testNextTime() {
        System.out.println("nextTime");
        int expResult = 10;
        int result = instance.nextTime();
        assertEquals(expResult, result); 
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = new Item(settings);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);        
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Object o = new Item(settings);        
        int expResult = o.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    @Test
    public void testSave() {
        System.out.println("save");
        instance.addChangeListener(this);
        instance.save();
        assertEquals(1, state);
    }

    @Test
    public void testStart() {
        System.out.println("start");
        Day day = new Day(settings, dtUtil.getToday());
        day.addItem(instance);
        instance.start();
        assertEquals(dtUtil.getMinuteInDay(), instance.getBeginMinute());
        assertEquals(true, instance.isBeginSet());
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Day day = new Day(settings, testDate);
        day.addItem(instance);
        String expResult = String.format("WrapPOJO { class: Item, "
                + "URL: null, beginMinute: %d, beginSet: false, "
                + "date: Thu Aug 01 00:00:00 PDT 2013, "
                + "durationMinutes: 10, earliestStartMinute: -1, "
                + "followUp: false, key: false, latestEndMinute: -1, "
                + "marvel: 0.0, name: null, recur: false, recurType: null }",               
                instance.getBeginMinute());
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        state++;
    }
}