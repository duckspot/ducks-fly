package com.duckspot.fly.swing;

import com.duckspot.fly.model.Day;
import com.duckspot.fly.model.Item;
import com.duckspot.fly.model.Util;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Peter Dobson
 */
public class DayTableModel implements TableModel, ChangeListener {

    Day day;
    List<TableModelListener> listeners = new ArrayList<TableModelListener>();
    
    public DayTableModel(Day day) {
        this.day = day;
        day.addChangeListener(this);
    }
    @Override
    public int getRowCount() {
        return day.getCount();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:  return String.class;
            case 1:  return Image.class;
            default: return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    Image itemImage(int rowIndex) {
        Item item = day.getItem(rowIndex);
        return null;
    }
    
    String itemInfo(int rowIndex) {
        Item item = day.getItem(rowIndex);
        return String.format("%s    [%s]",item.getName(),
                Util.formatDuration(item.getDurationMinutes()));
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:  return Util.formatTime(
                                    day.getItem(rowIndex).getBeginMinute());
            case 1:  return itemImage(rowIndex);
            default: return itemInfo(rowIndex);
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
    
    public void fireTableModelEvent(TableModelEvent event) {
        for(TableModelListener l : listeners) {
            l.tableChanged(event);
        }
    }
    
    public void fireTableModelEvent() {
        fireTableModelEvent(new TableModelEvent(this));
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        fireTableModelEvent();
    }
}
