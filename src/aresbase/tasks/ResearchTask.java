package aresbase.tasks;

// this is the Parent Task class and other xTask classes extend this class
public class ResearchTask extends ColonyTask {

    public ResearchTask() {
        super("Research Task", 5, 5, 5, 5, 5);
    }

    @Override
    public String toString() {
        return "Research Task";
    }

    @Override
    public String getType() {
        return "Research";
    }


}
