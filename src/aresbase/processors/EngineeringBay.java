package aresbase.processors;

import aresbase.manager.ResourceManager;
import aresbase.tasks.ColonyTask;
import aresbase.tasks.EngineeringTask;


public class EngineeringBay implements IBaseModule {

    @Override
    public String getName() {
        return "Engineering Bay";
    }

    @Override
    public boolean canProcess(ColonyTask task) {
        return "Engineering".equals(task.getType());
    }

    @Override
    public String processTask(ColonyTask task, ResourceManager rm) {
        EngineeringTask t = (EngineeringTask) task;
        if (rm.hasResources(t.getRequired()).equals("exists")) {
            rm.consumeRations(t.get_req_rations());
            rm.consumeSpareParts(t.get_req_spare_parts());
            rm.earn_reward(task.getReward());
            return "Success";
        } else {
            return rm.hasResources(t.getRequired());
        }

    }


}
