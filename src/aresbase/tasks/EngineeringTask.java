package aresbase.tasks;

import aresbase.model.Resource;

public class EngineeringTask extends ColonyTask {
    public EngineeringTask(String name, int rations, int spare_parts, int reward) {
        super(name, reward, 0, rations, spare_parts);
    }


    public int get_req_rations() {
        return required.get(Resource.RATIONS);
    }

    public int get_req_spare_parts() {
        return required.get(Resource.SPARE_PARTS);
    }


    @Override
    public String toString() {
        return name + " // " + necessary_resources();
       /* this version overwhelms the list visually
       return "[Engineering Task] " + name
                + " | Reward: " + reward + " | Cost: " + necessary_resources(); */
    }

    @Override
    public String getType() {
        return "Engineering";
    }

}
