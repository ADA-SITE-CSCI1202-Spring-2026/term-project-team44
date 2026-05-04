package aresbase.tasks;

import aresbase.model.Resource;

public class LifeSupportTask extends ColonyTask {

    public LifeSupportTask(String name, int oxygen, int spare_parts, int reward) {
        super(name, reward, oxygen, 0, spare_parts);

    }

    public int get_req_oxygen() {
        return required.get(Resource.OXYGEN);
    }

    public int get_req_spare_parts() {
        return required.get(Resource.SPARE_PARTS);
    }

    @Override
    public String toString() {
        return name + " // " + necessary_resources();
       /* return "[Life Support Task] " + name
                + " | Reward: " + reward + " | Cost: " + necessary_resources(); */
    }

    @Override
    public String getType() {
        return "LifeSupport";
    }


}
