package aresbase.processors;

import aresbase.tasks.ColonyTask;

public class MedicalWard implements IProcessor {


    @Override
    public String getName(){
        return "Medical Ward";
    }

    @Override
    public boolean canProcess(ColonyTask task){
        return "Life Support".equals(task.getType());
    }


    @Override
    public void processTask(ColonyTask task){
        // add something maybe or remove completely
    }


}
