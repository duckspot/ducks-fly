package com.duckspot.fly.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Peter Dobson
 */
public class Day extends ChangeProducer implements ChangeListener {

    private Settings settings; 
    private Date date;
    private long tomorrowStarts;
    
    List<Item> items = new ArrayList<Item>();
    
    Day(Settings settings, Date date) {
        this.settings = settings;
        this.date = date;
    }
    
    public synchronized Date getDate() {
        return date;
    }
    
    public synchronized boolean isToday() {        
        return date.equals(settings.getDtUtil().getToday());
    }
    
    public Item newItem() {        
        Item item = new Item(settings);
        addItem(item);
        return item;
    }
    
    public void addItem(Item item) {
        // TODO: figure out where in day to add item
        item.setDate(getDate());
        item.setBeginMinute(nextTime());
        item.addChangeListener(this);
        synchronized (items) {
            items.add(item);
        }
        item.save();
    }
    
    synchronized Item getLastItem() {
        return items.get(items.size()-1);
    }
    
    private int nowMinute() {
        DtUtil dtUtil = settings.getDtUtil();
        long nowMs;
        // repeat calculation in case of extremely rare negative result
        do {
            nowMs = dtUtil.getThisMinute().getTime()
                  - dtUtil.getToday().getTime();
        } while (nowMs < 0);
        long nowMinute = nowMs / (1000L * 60L);
        return (int)nowMinute;
    }
    
    public int nextTime(int index) {
        int result = settings.getDayStartMinute();
        if (index >= 0) {            
            Item item = getItem(index);
            return item.nextTime();
        }
        if (isToday() && result < nowMinute()) {
            result = nowMinute();
        }
        return result;
    }
    
    public int nextTime() {        
        return nextTime(getCount() - 1);
    }
    
    public synchronized int getCount() {
        return items.size();
    }
    
    public synchronized Item getItem(int index) {        
        return items.get(index);
    }
    
    public void removeItem(Item item) {        
        synchronized (this) {
            items.remove(item);
        }
        item.removeChangeListener(this);
        fireStateChanged();
    }
    
    public Item removeItem(int index) {
        Item result;
        synchronized (this) {
            result = items.remove(index);
        }
        result.removeChangeListener(this);
        fireStateChanged();
        return result;
    }
    
    /**
     * Item may need to have it's duration lengthened if it's the currently 
     * active item.
     * 
     * @param index 
     */
    void fixupItem(int index) {
        if (index < 0) {
            return;
        }
        // if there is an item after
        if (index+1 < getCount()) {
            // we may need to lengthen the duration of item
            Item item = getItem(index);
            Item itemAfter = getItem(index+1);
            if (item.isBeginSet() && !itemAfter.isBeginSet()) {
                // TODO: should we limit this to isToday()?
                int minutes = itemAfter.getBeginMinute() 
                            - item.getBeginMinute();
                // we only lengthen durations ... not shorten them
                if (minutes > item.getDurationMinutes()) {
                    item.setDurationMinutes(minutes);
                }
            }
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
                
        if (e.getSource() instanceof Item) {
            
            Item source = (Item)e.getSource();
            if (!source.getDate().equals(this.getDate())) {
                
                // item changed it's date                
                fireStateChanged(e);  // tell FlyModel to add to new date
                removeItem(source);   // remove from this date
                // don't fireStateChanged() here (it's done in removeItem())
            
            } else {
            
                // item change on this date means day changed state
                fireStateChanged();
            }
        }
    }
}
