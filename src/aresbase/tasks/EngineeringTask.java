package aresbase.tasks;

public class EngineeringTask extends ColonyTask {

    public EngineeringTask(boolean isDone) {
        super("Engineering Task", 5, 5, 5, 5, isDone);
    }

    // LATER ADD method to return type

    @Override
    public String toString() {
        return "EngineeringTask";
    }

    @Override
    public String getType() {
        return "Engineering";
    }
}
