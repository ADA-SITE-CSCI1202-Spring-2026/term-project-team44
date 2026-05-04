package aresbase.processors;

import aresbase.manager.ResourceManager;
import aresbase.tasks.ColonyTask;

public interface IBaseModule {

    boolean canProcess(ColonyTask task); // this checks if this can handle the given task to it

    String processTask(ColonyTask task, ResourceManager rm); // This performs the task, but in condition that canProcess returns True

    String getName();


}
