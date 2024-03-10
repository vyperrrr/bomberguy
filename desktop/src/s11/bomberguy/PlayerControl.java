package s11.bomberguy;

import static java.awt.event.KeyEvent.*;

public class PlayerControl {
    private int upButton;
    private int downButton;
    private int leftButton;
    private int rightButton;
    private int bombButton;
    private int extraButton;

    public PlayerControl() {}

    public PlayerControl(int playerNum) {
        switch (playerNum) {
            case 1:
                upButton = VK_W;
                downButton = VK_S;
                leftButton = VK_A;
                rightButton = VK_D;
                bombButton = VK_E;
                extraButton = VK_Q;
                break;
            case 2:
                upButton = VK_I;
                downButton = VK_K;
                leftButton = VK_J;
                rightButton = VK_L;
                bombButton = VK_O;
                extraButton = VK_U;
                break;
            case 3:
                upButton = VK_NUMPAD8;
                downButton = VK_NUMPAD5;
                leftButton = VK_NUMPAD4;
                rightButton = VK_NUMPAD6;
                bombButton = VK_NUMPAD9;
                extraButton = VK_NUMPAD7;
                break;
            default:
                throw new IllegalArgumentException("Only 1, 2 and 3 can be used!");
        }
    }

    public int getUpButton() {
        return upButton;
    }

    public void setUpButton(int upButton) {
        this.upButton = upButton;
    }

    public int getDownButton() {
        return downButton;
    }

    public void setDownButton(int downButton) {
        this.downButton = downButton;
    }

    public int getLeftButton() {
        return leftButton;
    }

    public void setLeftButton(int leftButton) {
        this.leftButton = leftButton;
    }

    public int getRightButton() {
        return rightButton;
    }

    public void setRightButton(int rightButton) {
        this.rightButton = rightButton;
    }

    public int getBombButton() {
        return bombButton;
    }

    public void setBombButton(int bombButton) {
        this.bombButton = bombButton;
    }

    public int getExtraButton() {
        return extraButton;
    }

    public void setExtraButton(int extraButton) {
        this.extraButton = extraButton;
    }
}
