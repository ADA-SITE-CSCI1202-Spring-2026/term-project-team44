package aresbase.tasks;

public class EngineeringTask extends ColonyTask {

    public EngineeringTask() {
        super("Engineering Task", 5, 5, 5, 5, 5);
    }

    // LATER ADD method to return type

    @Override
    public String toString() {
        return "Fix the engine!";
    }

    @Override
    public String getType() {
        return "Engineering";
    }
}
