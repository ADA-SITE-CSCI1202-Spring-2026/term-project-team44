package aresbase.processors;

import aresbase.manager.ResourceManager;
import aresbase.tasks.ColonyTask;
import aresbase.tasks.ResearchTask;

public class MedicalWard implements IBaseModule {


    @Override
    public String getName() {
        return "Medical Ward";
    }

    @Override
    public boolean canProcess(ColonyTask task) {
        return "Research".equals(task.getType());
    }


    @Override
    public String processTask(ColonyTask task, ResourceManager rm) {

        ResearchTask t = (ResearchTask) task;
        if (rm.hasResources(t.getRequired()).equals("exists")) {
            rm.consumeOxygen(t.get_req_oxygen());
            rm.consumeRations(t.get_req_rations());
            rm.earn_reward(task.getReward());
            return "Success";
        } else {
            return rm.hasResources(t.getRequired());
        }
    }


}
