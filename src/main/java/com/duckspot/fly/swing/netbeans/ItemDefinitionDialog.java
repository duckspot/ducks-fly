package com.duckspot.fly.swing.netbeans;

import com.duckspot.fly.swing.FixupTime;
import com.duckspot.fly.swing.FixupDuration;
import com.duckspot.fly.model.Item;
import com.duckspot.fly.model.Util;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

/**
 *
 * @author Peter Dobson
 */
public class ItemDefinitionDialog extends JDialog {

    Item item;
    
    /**
     * Creates new form ItemDefinitionDialog
     */
    public ItemDefinitionDialog(java.awt.Frame owner, boolean modal) {
        
        super(owner, modal);

        initComponents();
        
        /* make TAB and shift TAB keys navigate between fields
         * even in a JTextArea see http://stackoverflow.com/questions/5042429/\
         * how-can-i-modify-the-behavior-of-the-tab-key-in-a-jtextarea
         */
        nameTextArea.setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        nameTextArea.setFocusTraversalKeys(
                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /* make nameTextArea submit the dialog when the ENTER key is pressed
         * (we're just using a TextArea to allow long text to wrap to 
         * multiple lines)
         */
        nameTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
        nameTextArea.getActionMap().put("submit", new AbstractAction() {            
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
        
        addEnterListener(this);
        addEscapeListener(this);
        
        durationField.setInputVerifier(new FixupDuration());
        beginTimeField.setInputVerifier(new FixupTime(false));
    }

/*
    public void setupInputMap() {
        // make ENTER key submit dialog
        getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"submit");
        // make ESC key close dialog
        getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"close");     
    }
*/
    public void setItem(Item item) {
        
        // save item
        this.item = item;
        
        // load all fields from item
        nameTextArea.setText(item.getName());
        durationField.setText(Util.formatDuration(item.getDurationMinutes()));        
        if (item.isBeginSet()) {
            beginTimeField.setText(Util.formatTime(item.getBeginMinute()));
        } else {
            beginTimeField.setText("");
        }
        
        nameTextArea.requestFocusInWindow();
        //setupInputMap();
    }    
    
    public void submit() {
        
        // save all fields into item
        item.setName(nameTextArea.getText());
        item.setDurationMinutes(Util.parseDuration(durationField.getText()));
        if (Util.isEmpty(beginTimeField.getText())) {
            item.setBeginSet(false);
        } else {
            item.setBeginSet(true);
            item.setBeginMinute(Util.parseTime(beginTimeField.getText()));
        }
        item.save();
        close();
    }
    
    /**
     * Close dialog box.  Actually we just hide it cause we're probably gonna 
     * use it again with a different item.
     */
    public void close() {
        setVisible(false);
    }
    
    /**
     * Modify a JDialog under ItemDefinitionDialog so RETURN key causes 
     * dialog.submit() call.
     * 
     * code lifted from http://stackoverflow.com/questions/642925/\
     * swing-how-do-i-close-a-dialog-when-the-esc-key-is-pressed
     * 
     * @param dialog 
     */
    public static void addEnterListener(final ItemDefinitionDialog dialog) {
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.submit();
            }
        };

        dialog.getRootPane().registerKeyboardAction(listener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }    
    
    /**
     * Modify a JDialog under ItemDefinitionDialog so ESCAPE key causes 
     * dialog.submit() call.
     * 
     * code lifted from http://stackoverflow.com/questions/642925/\
     * swing-how-do-i-close-a-dialog-when-the-esc-key-is-pressed
     * 
     * @param dialog 
     */
    public static void addEscapeListener(final ItemDefinitionDialog dialog) {
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.close();
            }
        };

        dialog.getRootPane().registerKeyboardAction(listener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        nameTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        durationField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        beginTimeField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Item Definition");

        jLabel1.setDisplayedMnemonic('I');
        jLabel1.setLabelFor(nameTextArea);
        jLabel1.setText("Item");

        nameTextArea.setColumns(20);
        nameTextArea.setLineWrap(true);
        nameTextArea.setRows(5);
        jScrollPane1.setViewportView(nameTextArea);

        jLabel2.setDisplayedMnemonic('t');
        jLabel2.setLabelFor(durationField);
        jLabel2.setText("Duration:");

        durationField.setColumns(5);
        durationField.setText(":10");

        jLabel3.setDisplayedMnemonic('B');
        jLabel3.setLabelFor(beginTimeField);
        jLabel3.setText("Begin time:");

        beginTimeField.setColumns(6);

        jLabel4.setText("[optional]");

        okButton.setMnemonic('O');
        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1)
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                        .addGap(75, 75, 75))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(durationField, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(beginTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(durationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(beginTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        submit();
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelButtonActionPerformed
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField beginTimeField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField durationField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea nameTextArea;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}
