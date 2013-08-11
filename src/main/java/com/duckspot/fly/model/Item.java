package com.duckspot.fly.model;

import com.duckspot.pojo.POJO;
import java.util.Date;
import java.util.Objects;

/**
 * Event represents an Event that begins in a specific day and time, and lasts
 * a specific duration.  Recurring Events are a collection of Event all 
 * associated with a RecurranceRule.  Floating Events are Events where the 
 * WorkloadBalancer may move them from Day to Day as needed to balance the 
 * workload.
 * 
 * Events are created by a factory method in Day.
 * 
 * @author Peter Dobson
 */
public class Item extends ChangeProducer {

    private Settings settings;
    
    private Date date;
    private String name;
    private String url;
    private int durationMinutes = 10;
    private boolean beginSet;
    private int beginMinute;
    private int earliestStartMinute = -1;
    private int latestEndMinute = -1;
    private boolean key;
    private boolean followUp;
    private boolean recur;
    private RecurType recurType;
    private float marvel;
    
    Item(Settings settings) {
        this.settings = settings;
    }
    
    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the url
     */
    public String getURL() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setURL(String url) {
        this.url = url;
    }

    /**
     * @return the durationMinutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * @param durationMinutes the durationMinutes to set
     */
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * If the beginMinute was specified by the user, either by entering a 
     * start time, or declaring he started work on an Item, this is set to true.
     * 
     * @return the beginSet
     */
    public boolean isBeginSet() {
        return beginSet;
    }

    /**
     * @param beginSet the beginSet to set
     */
    public void setBeginSet(boolean beginSet) {
        this.beginSet = beginSet;
    }
    
    /**
     * Begin time is tracked as minutes past midnight.  The time may be altered
     * by the Day as it shuffles items around in the schedule.  If isBeginSet()
     * then day will not change this time.
     * 
     * @return the beginMinute
     */
    public int getBeginMinute() {
        return beginMinute;
    }

    /**
     * @param beginMinute the beginMinute to set
     */
    public void setBeginMinute(int beginMinute) {
        this.beginMinute = beginMinute;
    }

    /**
     * @return the earliestStartMinute
     */
    public int getEarliestStartMinute() {
        return earliestStartMinute;
    }

    /**
     * @param earliestStartMinute the earliestStartMinute to set
     */
    public void setEarliestStartMinute(int earliestStartMinute) {
        this.earliestStartMinute = earliestStartMinute;
    }

    /**
     * @return the latestEndMinute
     */
    public int getLatestEndMinute() {
        return latestEndMinute;
    }

    /**
     * @param latestEndMinute the latestEndMinute to set
     */
    public void setLatestEndMinute(int latestEndMinute) {
        this.latestEndMinute = latestEndMinute;
    }

    /**
     * @return the key
     */
    public boolean isKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(boolean key) {
        this.key = key;
    }

    /**
     * @return the followUp
     */
    public boolean isFollowUp() {
        return followUp;
    }

    /**
     * @param followUp the followUp to set
     */
    public void setFollowUp(boolean followUp) {
        this.followUp = followUp;
    }

    /**
     * @return the recur
     */
    public boolean isRecur() {
        return recur;
    }

    /**
     * @param recur the recur to set
     */
    public void setRecur(boolean recur) {
        this.recur = recur;
    }

    /**
     * @return the recurType
     */
    public RecurType getRecurType() {
        return recurType;
    }

    /**
     * @param recurType the recurType to set
     */
    public void setRecurType(RecurType recurType) {
        this.recurType = recurType;
    }

    /**
     * @return the marvel
     */
    public float getMarvel() {
        return marvel;
    }

    /**
     * @param marvel the marvel to set
     */
    public void setMarvel(float marvel) {
        this.marvel = marvel;        
    }
    
    public void save() {
        fireStateChanged();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Item) {
            Item that = (Item)o;
            return (this.name == that.name || 
                        (this.name != null && this.name.equals(that.name))) &&
                   (this.url == that.url ||
                        (this.url != null && this.url.equals(that.url))) &&
                   this.durationMinutes == that.durationMinutes &&
                   this.beginSet == that.beginSet &&
                   (!this.beginSet || this.beginMinute == that.beginMinute) &&
                   this.earliestStartMinute == that.earliestStartMinute &&
                   this.latestEndMinute == that.latestEndMinute &&
                   this.date == that.date &&
                   this.key == that.key &&
                   this.followUp == that.followUp &&
                   this.recur == that.recur &&
                   this.marvel == that.marvel;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.url);
        hash = 23 * hash + this.durationMinutes;
        if (this.beginSet) {
            hash = 23 * hash + this.beginMinute;
        }
        hash = 23 * hash + (this.beginSet ? 1 : 0);
        hash = 23 * hash + this.earliestStartMinute;
        hash = 23 * hash + this.latestEndMinute;
        hash = 23 * hash + Objects.hashCode(this.date);
        hash = 23 * hash + (this.key ? 1 : 0);
        hash = 23 * hash + (this.followUp ? 1 : 0);
        hash = 23 * hash + (this.recur ? 1 : 0);
        hash = 23 * hash + Objects.hashCode(this.recurType);
        hash = 23 * hash + Float.floatToIntBits(this.marvel);
        return hash;
    }
        
    /**
     * Return the begin time after this event.
     * 
     * @return beginMinute + duration.
     */
    public int nextTime() {
        return beginMinute + durationMinutes;
    }
    
    public void start() {
    	// TODO: doesn't set correct begin minute
        DtUtil dtUtil = settings.getDtUtil();
        
        // may only start items on todays schedule
        assert this.date.equals(dtUtil.getToday());
        
        beginMinute = dtUtil.getMinuteInDay();
        System.out.printf("item.java: beginMinute: %d",beginMinute);
        beginSet = true;
        save();
    }
    
    @Override
    public String toString() {
        return POJO.wrap(this).toString();
    }
}
