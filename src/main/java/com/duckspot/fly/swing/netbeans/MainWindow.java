package com.duckspot.fly.swing.netbeans;

import com.duckspot.fly.swing.DayTableModel;
import com.duckspot.fly.model.Day;
import com.duckspot.fly.model.FlyModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.table.TableModel;

/**
 * Quick prototype Java Swing program to copy key features of Above and Beyond.
 * 
 * @author Peter Dobson <pdobson@softwarehelps.com>
 */
public class MainWindow extends JFrame implements ActionListener {

    // TODO: add EditMenu: StartMenuItem
    // TODO: add EditMenu: StartAfterLastMenuItem
    // TODO: bold start time if it's fixed
 
    static DateFormat clockLabelFormat 
            = new SimpleDateFormat("hh:mm:ss a - EEE M/d/yyyy");
    static DateFormat dayLabelFormat 
            = new SimpleDateFormat("EEEE, M/d/yyyy");
    
    Timer clockTimer;
    
    FlyModel model = new FlyModel();
    Day day = model.getDay();
    TableModel tableModel = new DayTableModel(day);
    ItemDefinitionDialog itemDefinitionDialog = new ItemDefinitionDialog(this, true);
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        
        initComponents();
        
        setTableShortcuts();
        
        setDayLabel();
        setDayInfo();
        
        clockTimer = new Timer(1000, this);
        clockTimer.start();
        updateClock();
    }

    /**
     * Sets the label for the day from day.getDate().  
     * 
     * TODO: make sure this is called whenever we change from one day to another.
     */
    private void setDayLabel() {
        dayLabel.setText(dayLabelFormat.format(day.getDate()));
    }
    private void setDayInfo() {
        dayInfoLabel.setText("*****");
        // TODO: pull info from day and put together a status message
    }
    
    /**
     * Setup a shortcut, so while Table has focus, when key is pressed
     * an action is performed.
     * 
     * @param key use KeyEvent.VK_XXX to specify key to bind
     * @param name each shortcut in table must have unique name
     * @param action Swing action to perform when key pressed
     */
    private void setOneTableShortcut(int key, String name, Action action) {
        pItemTable.getInputMap().put(KeyStroke.getKeyStroke(key, 0), name);
        pItemTable.getActionMap().put(name, action);
    }
    
    /**
     * Setup actions and the shortcut keys that trigger them when the table has
     * focus.
     */
    private void setTableShortcuts() {
        setOneTableShortcut(KeyEvent.VK_ENTER, "edit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doEditSelectedItem();
            }
        });
        setOneTableShortcut(KeyEvent.VK_F5, "start", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doStartSelectedItem();
            }
        });
        setOneTableShortcut(KeyEvent.VK_DELETE, "delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doDeleteSelectedItem();
            }
        });
    }
    
    /**
     * Open the 'itemDefinitionDialog' for the selected item.
     */
    private void doEditSelectedItem() {
        itemDefinitionDialog.setItem(day.getItem(pItemTable.getSelectedRow()));
        itemDefinitionDialog.setVisible(true);
    }
    
    /**
     * Open the 'itemDefinitionDialog' for the selected item.
     */
    private void doStartSelectedItem() {
        if (!day.isToday()) {
            JOptionPane.showMessageDialog(rootPane, 
                    "May only start items on today's schedule.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int row = pItemTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(rootPane, 
                    "Must select an item to start.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        day.getItem(row).start();
    }
    
    private void doDeleteSelectedItem() {
        day.removeItem(pItemTable.getSelectedRow());        
    }
    
    private void doInsertItem() {
        itemDefinitionDialog.setItem(day.newItem());
        itemDefinitionDialog.setVisible(true);
    }
    
    private void updateClock() {
        clockLabel.setText(clockLabelFormat.format(new Date()));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {        
        if (e.getSource() == clockTimer) {
            updateClock();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jToolBar1 = new javax.swing.JToolBar();
        clockPanel = new javax.swing.JPanel();
        clockLabel = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        dayLabel = new javax.swing.JLabel();
        dayInfoLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pItemTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        insertMenu = new javax.swing.JMenu();
        insertGeneralItemMenuItem = new javax.swing.JMenuItem();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        clockLabel.setText("jLabel1");

        javax.swing.GroupLayout clockPanelLayout = new javax.swing.GroupLayout(clockPanel);
        clockPanel.setLayout(clockPanelLayout);
        clockPanelLayout.setHorizontalGroup(
            clockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clockLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        clockPanelLayout.setVerticalGroup(
            clockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clockLabel)
        );

        dayLabel.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        dayLabel.setText("Wednesday 12/31/2013");

        dayInfoLabel.setText("***** 1/5 undone (1:50) 11% full; 5:38 free; 0:00 conflicting");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addComponent(dayLabel)
                .addGap(18, 18, 18)
                .addComponent(dayInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 371, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(dayLabel)
                .addComponent(dayInfoLabel))
        );

        pItemTable.setModel(tableModel);
        jScrollPane1.setViewportView(pItemTable);

        fileMenu.setMnemonic('F');
        fileMenu.setText("File");

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        editMenu.setText("Edit");
        jMenuBar1.add(editMenu);

        insertMenu.setMnemonic('I');
        insertMenu.setText("Insert");

        insertGeneralItemMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT, 0));
        insertGeneralItemMenuItem.setMnemonic('I');
        insertGeneralItemMenuItem.setText("General Item...");
        insertGeneralItemMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertGeneralItemMenuItemActionPerformed(evt);
            }
        });
        insertMenu.add(insertGeneralItemMenuItem);

        jMenuBar1.add(insertMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 515, Short.MAX_VALUE))
            .addComponent(clockPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                    .addGap(6, 6, 6)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clockPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(328, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(64, Short.MAX_VALUE)
                    .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(281, 281, 281)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(119, 119, 119)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void insertGeneralItemMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertGeneralItemMenuItemActionPerformed
        doInsertItem();
    }//GEN-LAST:event_insertGeneralItemMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clockLabel;
    private javax.swing.JPanel clockPanel;
    private javax.swing.JLabel dayInfoLabel;
    private javax.swing.JLabel dayLabel;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JMenuItem insertGeneralItemMenuItem;
    private javax.swing.JMenu insertMenu;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable pItemTable;
    // End of variables declaration//GEN-END:variables
}
