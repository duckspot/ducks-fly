package com.duckspot.fly.swing.eclipse;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import javax.swing.table.TableModel;

import com.duckspot.fly.model.Day;
import com.duckspot.fly.model.FlyModel;
import com.duckspot.fly.model.Settings;

import com.duckspot.fly.swing.DayTableModel;

public class MainWindow {

	private static DateFormat dayLabelFormat = new SimpleDateFormat("EEEE, M/dd/yyyy");
	private static DateFormat clockFormat = new SimpleDateFormat("h:mm:ss aa - EEE, M/dd/yyyy");
    
	private JFrame frmFly;
	
	private FlyModel model = new FlyModel();
	private Settings settings = model.getSettings();
	private Day day = model.getDay();
	private TableModel tableModel = new DayTableModel(day);
	private ItemDefinitionDialog itemDefinitionDialog = new ItemDefinitionDialog(frmFly, true);
	private JLabel clockLabel;
	private JLabel dayLabel;
	private JLabel statusLabel;
	private JTable table;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmFly.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFly = new JFrame();
		frmFly.setTitle("Fly");
		frmFly.setBounds(100, 100, 450, 300);
		frmFly.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmFly.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mntmExit.setMnemonic(KeyEvent.VK_X);
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem startMenuItem = new JMenuItem("Start");
		startMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doStartSelectedItem();
			}
		});
		startMenuItem.setMnemonic('S');
		startMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		mnEdit.add(startMenuItem);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doDeleteSelectedItem();
			}
		});
		mntmDelete.setMnemonic(KeyEvent.VK_DELETE);
		mnEdit.add(mntmDelete);
		
		JMenu mnInsert = new JMenu("Insert");
		mnInsert.setMnemonic('I');
		menuBar.add(mnInsert);
		
		JMenuItem mntmItem = new JMenuItem("Item");
		mntmItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doInsertItem();
			}
		});
		mntmItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0));
		mntmItem.setMnemonic('i');
		mnInsert.add(mntmItem);
		frmFly.getContentPane().setLayout(new BoxLayout(frmFly.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel clockPanel = new JPanel();		
		frmFly.getContentPane().add(clockPanel);
		clockPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));		
		
		clockLabel = new JLabel("clock");
		clockPanel.add(clockLabel);
		
		JPanel dayLabelPanel = new JPanel();
		frmFly.getContentPane().add(dayLabelPanel);
		dayLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		dayLabel = new JLabel("Sunday 8/4/2013");
		dayLabelPanel.add(dayLabel);
		
		statusLabel = new JLabel("****");
		dayLabelPanel.add(statusLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		frmFly.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		
		setTableShortcuts();
		
		updateClock();
		updateDayLabel();
		updateStatus();
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
     * Setup a shortcut, so while Table has focus, when key is pressed
     * an action is performed.
     * 
     * @param key use KeyEvent.VK_XXX to specify key to bind
     * @param name each shortcut in table must have unique name
     * @param action Swing action to perform when key pressed
     */
    private void setOneTableShortcut(int key, String name, Action action) {
    }
    
    private void updateClock() {
        clockLabel.setText(clockFormat.format(settings.getDtUtil().getNow()));
    }
    
    /**
     * Sets the label for the day from day.getDate().  
     * 
     * TODO: make sure this is called whenever we change from one day to another.
     */
    private void updateDayLabel() {
        dayLabel.setText(dayLabelFormat.format(day.getDate()));
    }
    
    private void updateStatus() {
        statusLabel.setText("*****");
        // TODO: pull info from day and put together a status message
    }
    
    /**
     * Open the 'itemDefinitionDialog' for the selected item.
     */
    private void doEditSelectedItem() {
        itemDefinitionDialog.setItem(day.getItem(table.getSelectedRow()));
        itemDefinitionDialog.setVisible(true);
    }
    
    /**
     * Start the selected item.
     */
    private void doStartSelectedItem() {
        if (!day.isToday()) {
            JOptionPane.showMessageDialog(frmFly, 
                    "May only start items on today's schedule.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (table.getSelectedRow() < 0) {
        	JOptionPane.showMessageDialog(frmFly, 
                    "Select an item to start first.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int row = table.getSelectedRow();
        day.getItem(row).start();
        // TODO: restore selection to same row
    }
    
    private void doDeleteSelectedItem() {
        day.removeItem(table.getSelectedRow());        
    }
    
    private void doInsertItem() {
        itemDefinitionDialog.setItem(day.newItem());
        itemDefinitionDialog.setVisible(true);
    }        
}
