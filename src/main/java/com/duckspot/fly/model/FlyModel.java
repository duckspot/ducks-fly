/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duckspot.fly.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Peter Dobson
 */
public class FlyModel implements ChangeListener {
    
    private static final long DELAY = 1000L; // delay write 1 second
    private static final int CACHELIMIT = 500; // maximum number of days cached
        
    Settings settings = new Settings();
    
    // simple day cache    
    final Map<Date,Day> dayCache = new HashMap<>();
    
    // read-write Days from dataDirectory
    DayDAO dayDAO = new DayDAO(settings);
    // delay writes from the cache 'til day stops changing
    DelayWrite delayWrite = new DelayWrite(dayDAO, DELAY);
    
    public Day getDay(Date date) {
        
        // check cache
        synchronized(dayCache) {
            if (dayCache.containsKey(date)) {
                return dayCache.get(date);
            }
            
            // flush cache if appropriate
            if (dayCache.size() > CACHELIMIT && delayWrite.isEmpty()) {
                dayCache.clear();
            }
        }                
        
        // read or create new day
        Day day = dayDAO.read(date);
        if (day == null) {
            day = new Day(settings, date);
        }
        day.addChangeListener(this);
        
        // check cache again & and use cache, or insert new if not there
        synchronized(dayCache) {
            if (dayCache.containsKey(date)) {
                day = dayCache.get(date);
            } else {
                dayCache.put(date, day);                
            }
        }
        
        return day;
    }

    public Day getDay() {
        Calendar today = Calendar.getInstance();
        Util.clearTime(today);
        return getDay(today.getTime());
    }

    public Settings getSettings() {
        // TODO: read settings from file using SettingsDAO
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        
        if (e.getSource() instanceof Item) {
            Item source = (Item)e.getSource();
            // item ChangeEvent here because item changed day
            Day newDay = getDay(source.getDate());
            // add item into its new day
            newDay.addItem(source);
        }
        
        if (e.getSource() instanceof Day) {
            Day source = (Day)e.getSource();
            // day changed so write it after it stops changing
            delayWrite.queueWrite(source);
        }
    }
}
