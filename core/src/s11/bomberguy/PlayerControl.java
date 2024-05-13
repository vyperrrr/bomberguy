package s11.bomberguy;

import static com.badlogic.gdx.Input.*;

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
                upButton = Keys.W;
                downButton = Keys.S;
                leftButton = Keys.A;
                rightButton = Keys.D;
                bombButton = Keys.E;
                extraButton = Keys.Q;
                break;
            case 2:
                upButton = Keys.I;
                downButton = Keys.K;
                leftButton = Keys.J;
                rightButton = Keys.L;
                bombButton = Keys.O;
                extraButton = Keys.U;
                break;
            case 3:
                upButton = Keys.NUMPAD_8;
                downButton = Keys.NUMPAD_5;
                leftButton = Keys.NUMPAD_4;
                rightButton = Keys.NUMPAD_6;
                bombButton = Keys.NUMPAD_9;
                extraButton = Keys.NUMPAD_7;
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

    @Override
    public String toString() {
        return "PlayerControl{" +
                "upButton=" + upButton +
                ", downButton=" + downButton +
                ", leftButton=" + leftButton +
                ", rightButton=" + rightButton +
                ", bombButton=" + bombButton +
                ", extraButton=" + extraButton +
                '}';
    }
}
