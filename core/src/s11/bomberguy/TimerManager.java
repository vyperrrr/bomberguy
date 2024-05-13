package s11.bomberguy;

import com.badlogic.gdx.utils.Timer;
import java.util.ArrayList;
import java.util.List;

public class TimerManager {
    private static final List<Timer.Task> tasks = new ArrayList<>();

    public static void addTask(Timer.Task task) {
        tasks.add(task);
    }

    public static void scheduleTask(Timer.Task task, float delaySeconds) {
        Timer.schedule(task, delaySeconds);
        addTask(task);
    }

    public static void disposeAllTasks() {
        for (Timer.Task task : tasks) {
            task.cancel();
        }
        tasks.clear();
    }

}
