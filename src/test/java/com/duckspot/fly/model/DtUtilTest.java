package com.duckspot.fly.model;

import static com.duckspot.fly.model.DayDAOTest.settings;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.duckspot.fly.model.DtUtil;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 *
 * @author Peter Dobson
 */
public class DtUtilTest {
    
    static Date date = new GregorianCalendar(2013, 7, 1).getTime();
    DtUtil instance = DtUtil.getInstance();

    public DtUtilTest() {
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

    @Test
    public void testGetInstance_Locale_TimeZone() {
        System.out.println("getInstance");
        Locale locale = Locale.getDefault();
        TimeZone timeZone = TimeZone.getDefault();
        DtUtil result = DtUtil.getInstance(locale, timeZone);
        assertTrue(result instanceof DtUtil);
    }

    @Test
    public void testGetInstance_Locale() {
        System.out.println("getInstance");
        Locale locale = Locale.getDefault();
        DtUtil result = DtUtil.getInstance(locale);
        assertTrue(result instanceof DtUtil);
    }

    @Test
    public void testGetInstance_TimeZone() {
        System.out.println("getInstance");
        TimeZone timeZone = TimeZone.getDefault();
        DtUtil result = DtUtil.getInstance(timeZone);
        assertTrue(result instanceof DtUtil);
    }

    @Test
    public void testGetInstance_0args() {
        System.out.println("getInstance");
        DtUtil result = DtUtil.getInstance();
        assertTrue(result instanceof DtUtil);
    }

    @Test
    public void testFlushCache() throws InterruptedException {
        if (false) {
            System.out.println("flushCache");
            Date result = instance.getThisMinute();
            System.out.println("-- wait 1 minute ");
        
            System.out.print("-- ");
            for (int i=0; i<60; i++) {
                if (i%10 == 0) System.out.print("|"); else System.out.print("-");
                System.out.flush();
                Thread.sleep(1000L);
            }
            System.out.println("|");
            Date result2 = instance.getThisMinute();
            assertTrue(!result.equals(result2));  
        }
    }    

    /**
     * Test of render method, of class DtUtil.
     */
    @Test
    public void testRender_3args() {
        System.out.println("render");
        Object o = date;
        String format = "prefixDate";
        Locale locale = Locale.getDefault();
        String expResult = "Thursday, August 1, 2013";
        String result = instance.render(o, format, locale);
        assertEquals(expResult, result);
    }

    /**
     * Test of render method, of class DtUtil.
     */
    @Test
    public void testRender_Object_String() {
        System.out.println("render");
        Object o = date;
        String format = "prefixDate";
        String expResult = "Thursday, August 1, 2013";
        String result = instance.render(o, format);
        assertEquals(expResult, result);
        format = "shortTime";
        expResult = "12:00a";
        result = instance.render(o, format);
        assertEquals(expResult, result);
        format = "mediumDate";
        expResult = "Aug 1, 2013";
        result = instance.render(o, format);
        assertEquals(expResult, result);
        format = "timeDateCode";
        expResult = "2013-08-01-00-00";
        result = instance.render(o, format);
        assertEquals(expResult, result);
        format = "dateCode";
        expResult = "2013-08-01";
        result = instance.render(o, format);
        assertEquals(expResult, result);
    }

    /**
     * Test of defineFormat method, of class DtUtil.
     */
    @Test
    public void testDefineFormat_String_DateFormat() {
        System.out.println("defineFormat");
        String name = "monthYear";
        DateFormat format = new SimpleDateFormat("MMM-yyyy");
        instance.defineFormat(name, format);
        Object o = date;
        String expResult = "Aug-2013";
        String result = instance.render(o, name);
        assertEquals(expResult, result);
    }

    /**
     * Test of defineFormat method, of class DtUtil.
     */
    @Test
    public void testDefineFormat_String_String() {
        System.out.println("defineFormat");
        String name = "monthYear";
        String format = "MMM-yyyy";
        instance.defineFormat(name, format);
        Object o = date;
        String expResult = "Aug-2013";
        String result = instance.render(o, name);
        assertEquals(expResult, result);
    }

    /**
     * Test of defineFormat method, of class DtUtil.
     */
    @Test
    public void testDefineFormat_3args() {
        System.out.println("defineFormat");
        String name = "inMorning";
        String format = "h:mm a";
        String[] newAmpms = {"morning", "afternooon"};
        instance.defineFormat(name, format, newAmpms);
        Object o = date;
        String expResult = "12:00 morning";
        String result = instance.render(o, name);
        assertEquals(expResult, result);
    }

    /**
     * Test of parse method, of class DtUtil.
     */
    @Test
    public void testParse() throws Exception {
        System.out.println("parse");
        String s = "2013-08-01";
        String format = "dateCode";
        Date expResult = date;
        Date result = instance.parse(s, format);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCalendar method, of class DtUtil.
     */
    @Test
    public void testGetCalendar() {
        System.out.println("getCalendar");
        Calendar result = instance.getCalendar();
        assertEquals(TimeZone.getDefault(), result.getTimeZone());        
    }

    /**
     * Test of getTodayCal method, of class DtUtil.
     */
    @Test
    public void testGetTodayCal() {
        System.out.println("getTodayCal");
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(Calendar.HOUR_OF_DAY,0);
        expCalendar.set(Calendar.MINUTE,0);
        expCalendar.set(Calendar.SECOND,0);
        expCalendar.set(Calendar.MILLISECOND,0);
        Calendar result = instance.getTodayCal();
        assertEquals(expCalendar, result);
    }

    /**
     * Test of getToday method, of class DtUtil.
     */
    @Test
    public void testGetToday() {
        System.out.println("getToday");
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(Calendar.HOUR_OF_DAY,0);
        expCalendar.set(Calendar.MINUTE,0);
        expCalendar.set(Calendar.SECOND,0);
        expCalendar.set(Calendar.MILLISECOND,0);
        Date expResult = expCalendar.getTime();
        Date result = instance.getToday();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTomorrowCal method, of class DtUtil.
     */
    @Test
    public void testGetTomorrowCal() {
        System.out.println("getTomorrowCal");
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(Calendar.HOUR_OF_DAY,0);
        expCalendar.set(Calendar.MINUTE,0);
        expCalendar.set(Calendar.SECOND,0);
        expCalendar.set(Calendar.MILLISECOND,0);
        expCalendar.add(Calendar.DATE, 1);
        Calendar expResult = expCalendar;
        Calendar result = instance.getTomorrowCal();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTomorrow method, of class DtUtil.
     */
    @Test
    public void testGetTomorrow() {
        System.out.println("getTomorrow");
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(Calendar.HOUR_OF_DAY,0);
        expCalendar.set(Calendar.MINUTE,0);
        expCalendar.set(Calendar.SECOND,0);
        expCalendar.set(Calendar.MILLISECOND,0);
        expCalendar.add(Calendar.DATE, 1);
        Date expResult = expCalendar.getTime();
        Date result = instance.getTomorrow();
        assertEquals(expResult, result);
    }

    /**
     * Test of getYesterdayCal method, of class DtUtil.
     */
    @Test
    public void testGetYesterdayCal() {
        System.out.println("getYesterdayCal");
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(Calendar.HOUR_OF_DAY,0);
        expCalendar.set(Calendar.MINUTE,0);
        expCalendar.set(Calendar.SECOND,0);
        expCalendar.set(Calendar.MILLISECOND,0);
        expCalendar.add(Calendar.DATE, -1);
        Calendar expResult = expCalendar;
        Calendar result = instance.getYesterdayCal();
        assertEquals(expResult, result);
    }

    /**
     * Test of getYesterday method, of class DtUtil.
     */
    @Test
    public void testGetYesterday() {
        System.out.println("getYesterday");
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(Calendar.HOUR_OF_DAY,0);
        expCalendar.set(Calendar.MINUTE,0);
        expCalendar.set(Calendar.SECOND,0);
        expCalendar.set(Calendar.MILLISECOND,0);
        expCalendar.add(Calendar.DATE, -1);
        Date expResult = expCalendar.getTime();
        Date result = instance.getYesterday();
        assertEquals(expResult, result);
    }

    /**
     * Test of getThisMinuteCal method, of class DtUtil.
     */
    @Test
    public void testGetThisMinuteCal() {
        System.out.println("getThisMinuteCal");
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(Calendar.SECOND,0);
        expCalendar.set(Calendar.MILLISECOND,0);
        Calendar expResult = expCalendar;
        Calendar result = instance.getThisMinuteCal();
        assertEquals(expResult, result);        
    }

    /**
     * Test of getThisMinute method, of class DtUtil.
     */
    @Test
    public void testGetThisMinute() {
        System.out.println("getThisMinute");
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(Calendar.SECOND,0);
        expCalendar.set(Calendar.MILLISECOND,0);
        Date expResult = expCalendar.getTime();
        Date result = instance.getThisMinute();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNow method, of class DtUtil.
     */
    @Test
    public void testGetNow() {
        System.out.println("getNow");
        Date expResult = new Date();
        Date result = instance.getNow();
        assertTrue(Math.abs(expResult.getTime()-result.getTime()) < 100);
    }

    /**
     * Test of getNowMinute method, of class DtUtil.
     */
    @Test
    public void testGetNowMinute() {
        System.out.println("getNowMinute");
        Calendar expCalendar = Calendar.getInstance();        
        int expResult = expCalendar.get(Calendar.HOUR_OF_DAY) * 60 
                      + expCalendar.get(Calendar.MINUTE);
        int result = instance.getMinuteInDay();
        assertEquals(expResult, result);
    }

    /**
     * Test of parseTime method, of class DtUtil.
     */
    @Test
    public void testParseTime() throws Exception {
        System.out.println("parseTime");
        Calendar when = Calendar.getInstance();        
        Util.clearSeconds(when);        
        Date date = when.getTime();
        String time = instance.render(when, "hh:mm a");
        Date expResult = when.getTime();
        Date result = instance.parseTime(date, time);
        assertEquals(expResult, result);        
    }     

    /**
     * Test of getMinuteInDay method, of class DtUtil.
     */
    @Test
    public void testGetMinuteInDay() {
        System.out.println("getMinuteInDay");
        Calendar when = Calendar.getInstance();        
        Util.clearSeconds(when);        
        Date date = when.getTime();
        Util.clearTime(when);
        long ms = date.getTime() - when.getTimeInMillis();
        int expResult = (int)(ms/60000L);
        int result = instance.getMinuteInDay();
        assertEquals(expResult, result);
    }
}