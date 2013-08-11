package com.duckspot.fly.model;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Peter Dobson
 */
public class Util {
    
    private static Pattern durationPattern = Pattern.compile("^(\\d*)(\\D*(\\d*))?$");    
    private static Pattern timePattern = Pattern.compile("^(\\d+)(\\W+)?(\\d+)?\\s*(([apAP])[mM]?)?$");
    
    public static void clearSeconds(Calendar calendar) {
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    
    public static void clearTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        clearSeconds(calendar);
    }
    
    public static boolean isEmpty(Object obj) {
        
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            String s = (String)obj;
            return s.trim().length() == 0;
        }
        return false;
    }
    
    public static int parseInt(String s) {
        
        if (isEmpty(s)) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }
    
    public static String formatDuration(int minutes) {
        
        if (minutes < 0) {
            return null;
        }        
        if (minutes == 0) {
            return "memo";
        }
        
        int hours = minutes / 60;
        minutes = minutes - hours*60;
        
        return String.format("%d:%02d", hours, minutes);
    }

    public static String fixupDuration(String duration) {
        
        return formatDuration(parseDuration(duration));        
    }
        
    /**
     * Parse duration from hours and minutes, and return minutes, or -1 if it 
     * doesn't parse.  Zero length string returns 0, as does the string 'memo' (ignore case).
     * 
     * @param time
     * @return minutes
     */
    public static int parseDuration(String duration) {
        
        if (duration == null) {
            return -1;
        }
        
        String trimmed = duration.trim();
        if (trimmed.equalsIgnoreCase("memo")) {
            return 0;
        }
        if (trimmed.length() == 0) {
            return 0;
        }
        
        Matcher match = durationPattern.matcher(trimmed);
        if (!match.matches()) {
            return -1;
        }
        
        int hours = 0;
        int minutes = 0;
        
        // is there separator between hours/minutes?
        if (match.group(2).length() > 0) {
            hours = parseInt(match.group(1));
            minutes = parseInt(match.group(3));
        } else {
            minutes = parseInt(match.group(1));
        }
        
        return minutes + hours*60;
    }
    
    /**
     * parse time parses military and 12 hour format time, and returns 
     * minutes since midnight, or -1 if string doesn't parse.
     * 
     * @param time
     * @return minutes since midnight
     */
    public static int parseTime(String time) {
        
        if (isEmpty(time)) {
            return -1;
        }
        Matcher match = timePattern.matcher(time);
        if (!match.matches()) {
            return -1;
        }
        int hours = parseInt(match.group(1));
        int minutes = parseInt(match.group(3));
        String aOrP = match.group(5);
        if (!isEmpty(aOrP) && aOrP.equalsIgnoreCase("p")) {
            if (hours < 12) {
                hours += 12;
            }
        } else {
            if (hours == 12 && !isEmpty(aOrP) && aOrP.equalsIgnoreCase("a")) {
                hours = 0;
            }
        }
        minutes += hours*60;
        return minutes;
    }
    
    public static String formatTime(int minutes) {
        if (minutes < 0) {
            return null;
        }        
        int hours = minutes / 60;
        minutes -= hours*60;
        String xm = "a";        
        if (hours >= 12) {            
            xm = "p";
            if (hours > 12) {
                hours -= 12;
            }
        } else {
            if (hours == 0) {
                hours = 12;
            }            
        }
        return String.format("%d:%02d%s", hours, minutes, xm);
    }
    
    public static String fixupTime(String s) {
        return formatTime(parseTime(s));        
    }    
}
