package com.duckspot.fly.model;

import java.io.File;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author Peter Dobson
 */
public class Settings {

    private File dataDirectory = new File("./");
    private Locale locale = Locale.getDefault();
    private TimeZone timeZone = TimeZone.getDefault();    
    private int dayStartMinute = 8*60; // day starts 8:00a
    
    // tool for calculations that change with Locale & TimeZone
    private DtUtil dtUtil;    

    /**
     * @return the dataDirectory
     */
    public File getDataDirectory() {
        return dataDirectory;
    }

    /**
     * @param dataDirectory the dataDirectory to set
     */
    public void setDataDirectory(File dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        this.dtUtil = null;
    }

    /**
     * @return the timeZone
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZone the timeZone to set
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        this.dtUtil = null;
    }


    /**
     * @return the dayStartMinute
     */
    public int getDayStartMinute() {
        return dayStartMinute;
    }

    /**
     * @param dayStartMinute the dayStartMinute to set
     */
    public void setDayStartMinute(int dayStartMinute) {
        this.dayStartMinute = dayStartMinute;
    }

    /**
     * @return the dtUtil
     */
    public DtUtil getDtUtil() {
        if (dtUtil == null) {
            dtUtil = DtUtil.getInstance(locale, timeZone);
        }
        return dtUtil;
    }
}
