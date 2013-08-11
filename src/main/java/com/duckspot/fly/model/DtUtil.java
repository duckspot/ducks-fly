/* ducks-fly/src/com/duckspot/fly/model/DtUtil.java
 * 
 * History:
 *  3/23/13 PD
 *  3/24/13 PD refresh today, tomorrow, yesterday, thisMinute appropriately
 *  3/24/13 PD synchronized(formats) and synchronized on refresh
 *  3/24/13 PD moved parseHoursMinutes to Util (it's not locale or tz dependent)
 *  4/ 4/13 PD links links in navCal are urlDate format 
 *  4/ 4/13 PD highlight current date in calendar
 *  4/ 5/13 PD fix small error in navCal link URLs
 *  4/ 5/13 PD fixed navCal was cached beyond change of today highlight
 *  8/ 3/13 PD adapt from com.duckspot.diar.model.DtUtil
 */
package com.duckspot.fly.model;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides a set of utility functions to work with dates/times for a given 
 * Locale and TimeZone.  The values that come from this are cached for the 
 * duration of the request, unless a concurrent request causes them to be 
 * recalculated?
 * 
 * @author Peter M Dobson
 */
public class DtUtil {
    
    static class LocaleTimeZone {
        public Locale locale;
        public TimeZone timeZone;
        LocaleTimeZone(Locale locale, TimeZone timeZone) {
            this.locale = locale;
            this.timeZone = timeZone;
        }
        @Override
        public boolean equals(Object object) {
            if (object instanceof LocaleTimeZone) {
                LocaleTimeZone ltz = (LocaleTimeZone)object;
                return (locale == null || locale.equals(ltz.locale)) && 
                       (timeZone != null || timeZone.equals(ltz.timeZone));
            }
            return false;
        }        

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + (this.locale != null ? 
                                this.locale.hashCode() : 0);
            hash = 71 * hash + (this.timeZone != null ? 
                                this.timeZone.hashCode() : 0);
            return hash;
        }
    }
    
    private static Map<LocaleTimeZone,DtUtil> cache 
            = new HashMap<LocaleTimeZone,DtUtil>();
    
    private static final DateFormat monthYearFormat 
            = new SimpleDateFormat("MMMM yyyy");
    private static final DateFormat dayFormat = new SimpleDateFormat("EE");
    
    private static final Pattern whiteSpace = Pattern.compile("\\s+");
    private static final Pattern digits = Pattern.compile("[0-9]+");
    private static final Pattern notAlphanumeric = Pattern.compile("\\W+");

    private Map<String,DateFormat> formats = new HashMap<String,DateFormat>();    
    private Locale locale;
    private TimeZone timeZone;
   
    private Calendar todayCal;        // cache these values until next minute    
    private Calendar yesterdayCal;    // (see refreshMs below)
    private Calendar tomorrowCal;
    private Calendar thisMinuteCal; 
    
    private long refreshMinuteMs; // recalc thisMinute field every minute
    private long refreshDay;      // recalc some other fields each day
    
    // cache navigation calendars that are built for each month/year/url
    
    private Map<String,String> navCalCache;
    
    public static DtUtil getInstance(Locale locale, TimeZone timeZone) {
        LocaleTimeZone ltz = new LocaleTimeZone(locale, timeZone);
        DtUtil dtUtil = cache.get(ltz);
        if (dtUtil == null) {
            dtUtil = new DtUtil(locale, timeZone);
            cache.put(ltz, dtUtil);
                        
            // define formats - the first 2 of these have special code in a 
            // switch statements of render() and parse() methods.
            dtUtil.defineFormat("shortTime", 
                    DateFormat.getTimeInstance(DateFormat.SHORT, locale));
            dtUtil.defineFormat("prefixDate", 
                    DateFormat.getDateInstance(DateFormat.FULL, locale));
            dtUtil.defineFormat("mediumDate", 
                    DateFormat.getDateInstance(DateFormat.MEDIUM, locale));
            dtUtil.defineFormat("timeDateCode", "yyyy-MM-dd-HH-mm");
            dtUtil.defineFormat("dateCode", "yyyy-MM-dd");
        }
        return dtUtil;
    }
    
    public static DtUtil getInstance(Locale locale) {
        return getInstance(locale, TimeZone.getDefault());
    }
    
    public static DtUtil getInstance(TimeZone timeZone) {
        return getInstance(Locale.getDefault(), timeZone);
    }
    
    public static DtUtil getInstance() {
        return getInstance(Locale.getDefault(), TimeZone.getDefault());
    }
    
    public DtUtil(Locale locale, TimeZone timeZone) {
        this.locale = locale;
        this.timeZone = timeZone; 
    }
    
    public synchronized void flushCache() {
        if (System.currentTimeMillis() >= refreshMinuteMs) {
            thisMinuteCal = null;
            refreshMinuteMs = System.currentTimeMillis() 
                            % (1000L*60L) + (1000L*60L);            
            int day = rawThisMinuteCal().get(Calendar.DAY_OF_YEAR);
            if (day != refreshDay) {
                todayCal = null;
                tomorrowCal = null;
                yesterdayCal = null;
                navCalCache = new HashMap<String,String>();
                refreshDay = day;
            }
        }
    }
    
    /**
     * Render object o given format and locale (NamedRenderer interface).  
     * @param o - object to render
     * @param format - name of defined format, or format to pass to 
     *        SimpleDateFormat().
     * @param locale - locale to pass to SimpleDateFormat() if format not 
     *        already cached.
     * @return formatted string that represents calendar or date.
     */
    public String render(Object o, String format, Locale locale) {
        
        DateFormat df;
        synchronized(formats) {
            df = formats.get(format);
            if (df == null) {
                df = new SimpleDateFormat(format, locale);
                formats.put(format, df);
            }
        }
        Date date;
        if (o instanceof Calendar) {
            date = ((Calendar)o).getTime();
        } else if (o instanceof Date) {            
            date = (Date)o;
        } else {
            date = new Date((long)o);
        }
        
        if ("prefixDate".equals(format)) {
            String prefix = "";
            Calendar day = getCalendar();
            day.setTime(date);
            if (day.equals(getTodayCal())) {
                prefix = "Today, ";
            }
            if (day.equals(getTomorrowCal())) {
                prefix = "Tomorrow, ";
            }
            if (day.equals(getYesterdayCal())) {
                prefix = "Yesterday, ";
            }
            return prefix+df.format(date);            
        } 
        else if ("shortTime".equals(format)) {
            return df.format(date).replace(" AM", "a").replace(" PM", "p");            
        }
        else {
            return df.format(date);
        }
    }

    public String render(Object o, String format) {
        return render(o, format, locale);
    }
    
    public void defineFormat(String name, DateFormat format) {
        synchronized(formats) {
            formats.put(name, format);
        }
    }
    
    public void defineFormat(String name, String format) {
        defineFormat(name, new SimpleDateFormat(format, locale));
    }
    
    public void defineFormat(String name, String format, String[] newAmpms) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        DateFormatSymbols dfs = sdf.getDateFormatSymbols();
        dfs.setAmPmStrings(newAmpms);
        sdf.setDateFormatSymbols(dfs);
        defineFormat(name, sdf);
    }

    public Date parse(String s, String format) throws ParseException {
        
        DateFormat df;
        synchronized(formats) {
            df = formats.get(format);
            if (df == null) {
                df = new SimpleDateFormat(format, locale);
                formats.put(format, df);
            }
        }
        return df.parse(s);        
    }
    
    public Calendar getCalendar()
    {
        return Calendar.getInstance(timeZone, locale);
    }
    
    public synchronized Calendar getTodayCal()
    {
        flushCache();
        if (todayCal == null) {
            todayCal = getCalendar();
            Util.clearTime(todayCal);
        }
        return todayCal;
    }
    
    public Date getToday() {
        return getTodayCal().getTime();
    }
    
    public synchronized Calendar getTomorrowCal() {
        flushCache();
        if (tomorrowCal == null) {
            tomorrowCal = getCalendar();
            Util.clearTime(tomorrowCal);
            tomorrowCal.add(Calendar.DATE, 1);
        }
        return tomorrowCal;
    }
    
    public Date getTomorrow() {
        return getTomorrowCal().getTime();
    }
    
    public synchronized Calendar getYesterdayCal() {
        flushCache();
        if (yesterdayCal == null) {
            yesterdayCal = getCalendar();
            Util.clearTime(yesterdayCal);
            yesterdayCal.add(Calendar.DATE, -1);
        }
        return yesterdayCal;        
    }
    
    public Date getYesterday() {
        return getYesterdayCal().getTime();
    }
    
    private synchronized Calendar rawThisMinuteCal()
    {
        if (thisMinuteCal == null) {
            thisMinuteCal = getCalendar();
            Util.clearSeconds(thisMinuteCal);            
        }
        return thisMinuteCal;
    }
    
    public Calendar getThisMinuteCal()
    {
        flushCache();        
        return rawThisMinuteCal();
    }
    
    public Date getThisMinute() {        
        return getThisMinuteCal().getTime();
    }
    
    public Date getNow() {
        return new Date();
    }
    
    /**
     * Number of minutes since midnight.
     * @return 
     */
    public int getMinuteInDay() {
        long nowMs = getNow().getTime() - getToday().getTime();
        return (int)(nowMs / (60L * 1000L));
    }
    
    public Date parseTime(Date date, String time) throws ParseException {
        
        // TODO: 9 this code is kinda messy        
        int hours = -1;
        int minutes = 0;
        
        int pos = 0;
        int end;
        
        if (time == null || time.isEmpty()) {
            throw new ParseException("invalid time", pos);
        }
        
        // setup matchers        
        Matcher mWhiteSpace = whiteSpace     .matcher(time);
        Matcher mDigits     = digits         .matcher(time);
        Matcher mSep        = notAlphanumeric.matcher(time);
        
        // skip white space
        if (mWhiteSpace.find(pos) && mWhiteSpace.start() == pos) { 
            pos = mWhiteSpace.end();
        }
        
        // extract hour
        mDigits = digits.matcher(time);
        if (mDigits.find(pos) && mDigits.start() == pos) {
            end = mDigits.end();
            hours = Integer.parseInt(time.substring(pos,end));
            pos = end;
        } else {
            throw new ParseException("invalid hour", pos);
        }
        
        // find hours/minutes separator
        if (mSep.find(pos) && mSep.start() == pos) {
            
            // skip separator
            pos = mSep.end();
            // find minutes
            if (mDigits.find(pos) && mDigits.start() == pos) {
                end = mDigits.end();
                minutes = Integer.parseInt(time.substring(pos,end));
                pos = end;
            }
        }
        // skip whitespace
        if (mWhiteSpace.find(pos) && mWhiteSpace.start() == pos) {
            pos = mWhiteSpace.end();
        }

        // take next character (if any)
        if (pos < time.length()) {
            char c = time.charAt(pos);
            // if it's upper or lower case A then we're in AM
            if (c == 'a' || c == 'A') {
                hours = hours%12;
            }
            // if it's upper or lower case P then we're in PM
            if (c == 'p' || c == 'P') {
                hours = hours%12+12;
            }
        }

        Calendar cal = getCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        Util.clearSeconds(cal);
        return cal.getTime();
    }
}
