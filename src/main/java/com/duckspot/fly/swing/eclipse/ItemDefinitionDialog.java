package com.duckspot.fly.swing.eclipse;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import javax.swing.border.EmptyBorder;

import com.duckspot.fly.model.Item;
import com.duckspot.fly.model.Util;

import com.duckspot.fly.swing.FixupDuration;
import com.duckspot.fly.swing.FixupTime;

public class ItemDefinitionDialog extends JDialog {

	private Item item;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextArea  nameTextArea;
	private JTextField durationField;
	private JTextField beginTimeField;

	/**
	 * Create the dialog.
	 */
	public ItemDefinitionDialog(java.awt.Frame owner, boolean modal) {
		
		super(owner, modal);
		
		setTitle("Item Definition");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setLayout(null);
		
		JLabel lblItem = new JLabel("Item:");
		lblItem.setBounds(12, 12, 36, 15);
		lblItem.setDisplayedMnemonic('I');
		contentPanel.add(lblItem);
		
		nameTextArea = new JTextArea();
		lblItem.setLabelFor(nameTextArea);
		nameTextArea.setRows(4);
		nameTextArea.setColumns(50);
		nameTextArea.setBounds(66, 12, 366, 49);
		contentPanel.add(nameTextArea);
		
		JLabel lblNewLabel = new JLabel("Duration:");
		lblNewLabel.setDisplayedMnemonic('t');
		lblNewLabel.setBounds(12, 73, 70, 15);
		contentPanel.add(lblNewLabel);
		
		durationField = new JTextField();
		lblNewLabel.setLabelFor(durationField);
		durationField.setText(":10");
		durationField.setBounds(87, 71, 44, 19);
		contentPanel.add(durationField);
		durationField.setColumns(6);
		
		JLabel lblBeginTime = new JLabel("Begin time:");
		lblBeginTime.setDisplayedMnemonic('B');
		lblBeginTime.setBounds(159, 73, 90, 15);
		contentPanel.add(lblBeginTime);
		
		beginTimeField = new JTextField();
		lblBeginTime.setLabelFor(beginTimeField);
		beginTimeField.setBounds(245, 71, 56, 19);
		contentPanel.add(beginTimeField);
		beginTimeField.setColumns(7);
		
		JLabel lbloptional = new JLabel("(optional)");
		lbloptional.setBounds(310, 75, 70, 15);
		contentPanel.add(lbloptional);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						submit();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		enableTabBetweenFieldsTextArea(nameTextArea);
        
		// TODO: Enter in textArea doesn't do as we wish... 
		// TODO: we probably have to add a key listener to the text area specifically
        addKeyListenerToDialog(this, KeyEvent.VK_ENTER, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
        
        addKeyListenerToTextArea(nameTextArea, KeyEvent.VK_ENTER, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
        
        addKeyListenerToDialog(this, KeyEvent.VK_ESCAPE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        
        durationField.setInputVerifier(new FixupDuration());
        beginTimeField.setInputVerifier(new FixupTime(false));
	}
	
	/**
	 * make TAB and shift TAB keys navigate between fields
     * even in a JTextArea see http://stackoverflow.com/questions/5042429/\
     * how-can-i-modify-the-behavior-of-the-tab-key-in-a-jtextarea
     */
	private void enableTabBetweenFieldsTextArea(JTextArea textArea) {
		textArea.setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		textArea.setFocusTraversalKeys(
                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
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
    public static void addKeyListenerToDialog(
    		final ItemDefinitionDialog dialog, int key, ActionListener listener) {
    	
        dialog.getRootPane().registerKeyboardAction(listener,
                KeyStroke.getKeyStroke(key, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }    
    // TODO: see if we can make this a little less specific ToField or something
    public static void addKeyListenerToTextArea(
    		final JTextArea textArea, int key, ActionListener listener) {
    	
        textArea.registerKeyboardAction(listener,
                KeyStroke.getKeyStroke(key, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    
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
}
