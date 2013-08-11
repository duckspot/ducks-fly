package com.duckspot.fly.model;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class DayDAOTest {
    
    static final Settings settings = new Settings();
    static File dataDirectory = settings.getDataDirectory();
    static String directoryPath;
    static String filePath;
    static Date date = new GregorianCalendar(2013, 7, 1).getTime();
    static DtUtil dtUtil = settings.getDtUtil();
    
    Day day1;
    DayDAO instance;
    
    public DayDAOTest() {        
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        directoryPath = dataDirectory.getCanonicalPath();        
        filePath = directoryPath+File.separator+"day-2013-08-01.json";
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        day1 = new Day(settings, date);
        instance = new DayDAO(settings);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testRead() throws InterruptedException {
        System.out.println("read");
        File file = instance.getFile(date);
        file.delete();
        Day expResult = null;
        Day result = instance.read(date);
        assertEquals(expResult, result);        
    }

    @Test
    public void testWrite() {
        System.out.println("write");
        Item item1 = day1.newItem();
        item1.setName("one");
        item1.setDurationMinutes(20);
        Item item2 = day1.newItem();
        item2.setName("two");        
        instance.write(day1);
        Day day2 = instance.read(date);
        assertTrue(null != day2);
        assertEquals(day1.getDate(), day2.getDate());
        assertEquals(day1.getCount(), day2.getCount());
        for (int i=Math.min(day1.getCount()-1,day2.getCount());i>=0;i--) {
            Item a = day1.getItem(i);
            Item b = day2.getItem(i);
            assertTrue(a.equals(b));
        }
    }

    @Test
    public void testGetFile() throws IOException {
        System.out.println("getFile");        
        File result = instance.getFile(date);
        assertEquals(filePath, result.getCanonicalPath());
    }

    /**
     * Test of renameFile method, of class DayDAO.
     */
    @Test
    public void testRenameFile() {
        System.out.println("renameFile");
        Date date = new Date();
        instance.renameFile(date);
    }
}