package aresbase.tasks;

public class LifeSupportTask extends ColonyTask {

    public LifeSupportTask() {
        super("Life Support Task", 5, 5, 5, 5, 5);
    }

    @Override
    public String toString() {
        return "Need life support";
    }

    @Override
    public String getType() {

        return "Life Support";
    }

}
