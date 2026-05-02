package aresbase.tasks;

public class LifeSupportTask extends ColonyTask {

    public LifeSupportTask(boolean isDone) {
        super("Life Support Task", 5, 5, 5, 5, isDone);
    }

    @Override
    public String toString() {
        return "LifeSupportTask";
    }

    @Override
    public String getType() {

        return "Life Support";
    }

}
