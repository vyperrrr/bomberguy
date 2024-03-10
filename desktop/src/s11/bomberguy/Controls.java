package s11.bomberguy;

import java.util.ArrayList;

public class Controls {
    private static ArrayList<PlayerControl> controls = null;
    private Controls() {}
    public static ArrayList<PlayerControl> getControls() {
        if (controls == null) {
            controls = new ArrayList<>();
            for (int i = 1; i <= 3 ; i++) {
                controls.add(new PlayerControl(i));
            }
        }
        return controls;
    }

    public static void setControls(ArrayList<PlayerControl> controlSettings) {
        controls = (ArrayList<PlayerControl>) controlSettings.clone();
    }
}
