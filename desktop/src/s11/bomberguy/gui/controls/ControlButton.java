package s11.bomberguy.gui.controls;

import javax.swing.*;

public class ControlButton extends JButton {
    private int keyCode;

    public ControlButton(String text, int keyCode) {
        super(text);
        this.keyCode = keyCode;
    }
    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
