package aresbase.tasks;

import aresbase.model.Resource;

// this is the Parent Task class and other xTask classes extend this class
public class ResearchTask extends ColonyTask {

    public ResearchTask(String name, int oxygen, int rations, int reward) {
        super(name, reward, oxygen, rations, 0);

    }


    public int get_req_oxygen() {
        return required.get(Resource.OXYGEN);
    }

    public int get_req_rations() {
        return required.get(Resource.RATIONS);
    }


    @Override
    public String toString() {
        return name + " // " + necessary_resources();
        /*
        return "[Research Task] " + name
                + " | Reward: " + reward + " | Cost: " + necessary_resources(); */
    }

    @Override
    public String getType() {
        return "Research";
    }

}
