package com.duckspot.fly.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Peter Dobson
 */
public class DelayWrite extends Thread {

    DayDAO dayDAO;
    long delay;
    boolean running = false;
    
    /**
     * Initialize WriteLater.
     * 
     * @param dayDAO  used to write day
     * @param delay   number of milliseconds between last queueWrite() for a 
     *                given Day, and when it's actually written=
     */    
    DelayWrite(DayDAO dayDAO, long delay) {
        this.dayDAO = dayDAO;
    }
    
    List<Day> queue = new ArrayList<Day>();     
    List<Long> writeAt = new ArrayList<Long>();
    
    /**
     * Add Day to the write queue, or move to the end of the queue, such that 
     * it will be written 'delay' milliseconds after the last call to 
     * queueWrite for that Day.  This code starts the background thread the 
     * first time it's called.
     * 
     * @param day 
     */
    public synchronized void queueWrite(Day day) {
        
        long when = System.currentTimeMillis()+delay;
        
        // is Day already in queue?
        int i = queue.indexOf(day);
        if (i >= 0) {
            // yes
            
            // is Day at end of queue
            if (queue.size() == i+1) {
                
                // yes
                // just update when
                writeAt.set(i, when);
                return;
            
            } else {
                
                // not at end of queue
                // remove from queue
                queue.remove(i);
                writeAt.remove(i);                
            }
        }
        
        // day not in queue (perhaps just removed from queue)
        // add day to end of queue
        queue.add(day);
        writeAt.add(when);
        
        // start the thread if we must
        if (!running) {
            running = true;
            start();
        }
        
        notifyAll();
    }
    
    /**
     * wait for something to be in the queue.  If it's time to write it write 
     * it, pop it from the queue and write it.
     */
    public void dequeue() {

        Day day = null;
        
        synchronized (this) {
            
            // wait for data in queue
            while (queue.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DelayWrite.class.getName())
                          .log(Level.SEVERE, null, ex);
                    // ignore
                }
            }

            // is it time?
            if (writeAt.get(0) >= System.currentTimeMillis()) {
                
                // remove item from queue (& writeAt)
                day = queue.remove(0);
                writeAt.remove(0);
            }
        }
        
        // perform write (outside of synchronized region) if we have queueEntry
        if (day != null) {
            dayDAO.write(day);
        }
    }
    
    /**
     * Determine how long it is before the first item in the queue will be
     * ready to write, or 0 if queue empty.
     * 
     * @return milliseconds before next item, or 0 if queue empty
     */
    private synchronized long sleepTime() {
        if (queue.size() == 0) {
            return 0L;
        } else {
            return Math.max(0L, writeAt.get(0) - System.currentTimeMillis());
        }
    }
    
    @Override
    public void run() {
        
        while (running) {
            dequeue(); // waits if queue is empty, writes one if it's time
            try {                
                // sleep(0) if queue is empty
                // or wait appropriate time for first writeAt
                Thread.sleep(sleepTime());
            } catch (InterruptedException ex) {
                Logger.getLogger(DelayWrite.class.getName()).log(Level.SEVERE, null, ex);
                // ignore
            }
        }
    }
    
    public void exit() {
        this.running = false;
    }
    
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
