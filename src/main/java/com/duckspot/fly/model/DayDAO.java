package com.duckspot.fly.model;

import com.duckspot.pojo.POJO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

/**
 * Read and write Day from data files.
 * 
 * @author Peter Dobson
 */
public class DayDAO {

    private static DateFormat fileNameFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private Settings settings;    
    private File dataDirectory;    
    
    DayDAO(Settings settings) {
        this.settings = settings;        
        this.dataDirectory = settings.getDataDirectory();
    }
    
    void renameFile(Date date) {
        DtUtil dtUtil = settings.getDtUtil();        
        String oldFileName = "day-" + dtUtil.render(date,"yyyy-MM-dd") 
                           + ".json";
        File oldFile = new File(dataDirectory, oldFileName);
        if (oldFile.exists()) {
            long time = oldFile.lastModified();
            String newFileName = "day-" + dtUtil.render(date,"yyyy-MM-dd") 
                               + dtUtil.render(time, "yyyyMMdd-HHmmss")
                               + ".bak";
            File newFile = new File(dataDirectory, newFileName);
            while (newFile.exists()) {
                time++;
                newFileName = "day-" + dtUtil.render(date,"yyyy-MM-dd") 
                               + dtUtil.render(time, "yyyyMMdd-HHmmss-SSS")
                               + ".bak";
                newFile = new File(dataDirectory, newFileName);
            }
            oldFile.renameTo(newFile);
        }
    }
    
    File getFile(Date date) {
        DtUtil dtUtil = settings.getDtUtil();
        String fileName = "day-" + dtUtil.render(date,"yyyy-MM-dd") + ".json";
        return new File(dataDirectory, fileName);
    }
    
    public Day read(Date date) {
            
        File file = getFile(date);
        if (!file.canRead()) {
            return null;
        }
        Day day = new Day(settings, date);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            JSONTokener jt = new JSONTokener(fileReader);
            JSONArray jsonItems = (JSONArray)jt.nextValue();            
            for (int i=0; i<jsonItems.length(); i++) {
                POJO.copy(day.newItem(), (JSONObject)jsonItems.get(i));
                day.fixupItem(i-1); // sometimes adjust duration of item before
            }
        } catch (FileNotFoundException ex) {
            throw new Error("unexpected exception", ex);
        } catch (JSONException ex) {
            throw new Error("unexpected exception", ex);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    throw new Error("unexpected exception", ex);
                }
            }
        }
        return day;
    }
    
    public void write(Day day) {
        
        renameFile(day.getDate());
        File file = getFile(day.getDate());
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            JSONWriter output = new JSONWriter(fileWriter);
            output.array();
            for (int i = 0; i < day.getCount(); i++) {
                Item item = day.getItem(i);
                output.object();
                output.key("name").value(item.getName());
                output.key("durationMinutes").value(item.getDurationMinutes());
                output.key("beginSet").value(item.isBeginSet());
                if (item.isBeginSet()) {
                    output.key("beginMinute").value(item.getBeginMinute());
                }
                output.endObject();
            }
            output.endArray();                
            fileWriter.close();
        } catch (FileNotFoundException ex) {
            try {
                throw new Error("Can't write to directory "+dataDirectory.getCanonicalPath(), ex);
            } catch (IOException ex1) {
                throw new Error("unexpected exception", ex);
            }
        } catch (IOException ex) {
            throw new Error("unexpected exception", ex);
        } catch (JSONException ex) {
            throw new Error("unexpected exception", ex);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new Error("unexpected exception", ex);
                }
            }
        }
    }
}
