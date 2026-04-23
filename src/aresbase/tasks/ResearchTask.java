package aresbase.tasks;
import java.util.*;

 // this is the Parent Task class and other xTask classes extend this class
public class ResearchTask  extends ColonyTask{

    public ResearchTask(String name, int reward, Map<Resource, Integer> required) {
        super(name, reward, required);
    }


}
