package aresbase.processors;

import aresbase.tasks.ColonyTask;


public class Hydroponics implements IProcessor {


    @Override
    public String getName(){
        return "Hydroponics";
    }

    @Override
    public boolean canProcess(ColonyTask task){
        return "Research".equals(task.getType());
    }

    @Override
    public void processTask(ColonyTask task) {
       //
    }

}
