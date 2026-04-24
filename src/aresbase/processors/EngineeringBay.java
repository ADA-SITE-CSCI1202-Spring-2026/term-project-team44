package aresbase.processors;

import aresbase.tasks.ColonyTask;


public class EngineeringBay implements IProcessor{

    @Override
    public String getName(){
        return "Engineering Bay";
    }

    @Override
    public boolean canProcess(ColonyTask task){
        return "Engineering".equals(task.getType());
    }

    @Override
    public void processTask(ColonyTask task){
        // add something later maybe or delete completely
    }


}
