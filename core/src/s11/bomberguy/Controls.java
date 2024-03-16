package s11.bomberguy;
import static s11.bomberguy.Movement.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Controls {
    public static Function<String[], Map<Movement,String>> createControls = (keys) -> {
        HashMap<Movement,String> controls = new HashMap<>();
        controls.put(UP, keys[0]);
        controls.put(DOWN, keys[1]);
        controls.put(LEFT, keys[2]);
        controls.put(RIGHT, keys[3]);
        controls.put(PLACE_BOMB, keys[4]);
        controls.put(PLACE_BOX, keys[5]);
        return controls;
    };


}
