package aresbase.tasks;

// this is the Parent Task class and other xTask classes extend this class
public class ResearchTask extends ColonyTask {

    public ResearchTask(boolean isDone) {
        super("Research Task", 5, 5, 5, 5, isDone);
    }

    @Override
    public String toString() {
        return "ResearchTask";
    }

    @Override
    public String getType() {
        return "Research";
    }


}
