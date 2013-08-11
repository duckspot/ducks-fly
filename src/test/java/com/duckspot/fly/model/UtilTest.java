package com.duckspot.fly.model;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UtilTest {

	public UtilTest() {
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClearSeconds() {
		System.out.println("clearSeconds");
        Calendar calendar = Calendar.getInstance();
        Util.clearSeconds(calendar);
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MILLISECOND)); 
	}

	@Test
	public void testClearTime() {
		System.out.println("clearTime");
        Calendar calendar = Calendar.getInstance();
        Util.clearTime(calendar);
        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MILLISECOND)); 
	}

	@Test
	public void testIsEmpty() {
		System.out.println("isEmpty");        
        assertEquals(true, Util.isEmpty(null));
        assertEquals(true, Util.isEmpty(""));
        assertEquals(false, Util.isEmpty(0));
        assertEquals(false, Util.isEmpty("0"));
        assertEquals(false, Util.isEmpty("A"));        
	}

	@Test
	public void testParseInt() {
		System.out.println("parseInt");
        assertEquals(0, Util.parseInt(""));
        assertEquals(0, Util.parseInt("0"));
        assertEquals(1, Util.parseInt("1"));
	}

	@Test
	public void testFixupDuration() {
		System.out.println("fixupDuration");
        assertEquals("memo", Util.fixupDuration(""));
        assertEquals("memo", Util.fixupDuration("0"));
        assertEquals("memo", Util.fixupDuration("memo"));
        assertEquals("0:01", Util.fixupDuration("1"));
        assertEquals("1:00", Util.fixupDuration("1."));
	}

	@Test
	public void testFixupTime() {
		System.out.println("fixupTime");
        assertEquals(null, Util.fixupTime(null));
        assertEquals(null, Util.fixupTime(""));
        assertEquals("1:00a", Util.fixupTime("1"));
        assertEquals("1:00p", Util.fixupTime("1p"));
        assertEquals("12:00p", Util.fixupTime("12"));
        assertEquals("12:30p", Util.fixupTime("12:30p"));
        assertEquals("12:30a", Util.fixupTime("12:30a"));  
	}

    /**
     * Test of formatDuration method, of class Util.
     */
    @Test
    public void testFormatDuration() {
        System.out.println("formatDuration");
        int minutes = 85;
        String expResult = "1:25";
        String result = Util.formatDuration(minutes);
        assertEquals(expResult, result);        
    }

    /**
     * Test of parseDuration method, of class Util.
     */
    @Test
    public void testParseDuration() {
        System.out.println("parseDuration");
        String duration = "1:25";
        int expResult = 85;
        int result = Util.parseDuration(duration);
        assertEquals(expResult, result);        
    }

    /**
     * Test of parseTime method, of class Util.
     */
    @Test
    public void testParseTime() {
        System.out.println("parseTime");
        String time = "12:15p";
        int expResult = 12*60+15;
        int result = Util.parseTime(time);
        assertEquals(expResult, result);        
    }

    /**
     * Test of formatTime method, of class Util.
     */
    @Test
    public void testFormatTime() {
        System.out.println("formatTime");
        int minutes = 12*60+15;
        String expResult = "12:15p";
        String result = Util.formatTime(minutes);
        assertEquals(expResult, result);
    }
}
