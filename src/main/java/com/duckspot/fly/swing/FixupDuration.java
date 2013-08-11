package com.duckspot.fly.swing;

import com.duckspot.fly.model.Util;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * An input verifier that fixes up a well enough formed duration into a nice 
 * h:mm display, and returns false if field does not parse.
 * 
 * @author Peter Dobson
 */
public class FixupDuration extends InputVerifier {
    
    boolean allowEmpty;
    
    public FixupDuration(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }
    public FixupDuration() {
        this(false);
    }
    
    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JTextField) {
            JTextField tf = (JTextField) input;
            String s = tf.getText().trim();
            if (allowEmpty && s.length() == 0) {
                return true;
            }
            int minutes = Util.parseDuration(s);
            if (minutes < 0) {            
                return false;
            }
            tf.setText(Util.formatDuration(minutes));
        }        
        return true;
    }
}
