package com.duckspot.fly.swing;

import com.duckspot.fly.model.Util;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * An input verifier that fixes up a well enough formed time into a nice 
 * 1:00p or 1:00a display, and returns false if field does not parse.
 * 
 * @author Peter Dobson
 */
public class FixupTime extends InputVerifier {
    
    boolean allowEmpty;
    
    public FixupTime(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }
    public FixupTime() {
        this(false);
    }
    
    public boolean verify(JComponent input) {
        if (input instanceof JTextField) {
            
            JTextField tf = (JTextField) input;
            String s = tf.getText().trim();
            if (allowEmpty && s.length() == 0) {
                return true;
            }
            int minute = Util.parseTime(s);
            if (minute < 0) {
                return false;
            }
            tf.setText(Util.formatTime(minute));
        }
        return true;
    }
}
