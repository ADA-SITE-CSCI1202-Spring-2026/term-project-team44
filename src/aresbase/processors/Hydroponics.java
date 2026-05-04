package aresbase.processors;

import aresbase.manager.ResourceManager;
import aresbase.tasks.ColonyTask;
import aresbase.tasks.LifeSupportTask;


public class Hydroponics implements IBaseModule {


    @Override
    public String getName() {
        return "Hydroponics";
    }

    @Override
    public boolean canProcess(ColonyTask task) {
        return "LifeSupport".equals(task.getType());
    }

    @Override
    public String processTask(ColonyTask task, ResourceManager rm) {

        LifeSupportTask t = (LifeSupportTask) task;
        if (rm.hasResources(t.getRequired()).equals("exists")) {
            rm.consumeOxygen(t.get_req_oxygen());
            rm.consumeSpareParts(t.get_req_spare_parts());
            rm.earn_reward(task.getReward());
            return "Success";
        } else {
            return rm.hasResources(t.getRequired());
        }
    }

}
