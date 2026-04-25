package aresbase.processors;

import aresbase.tasks.ColonyTask;

public interface IProcessor {

    boolean canProcess(ColonyTask task); // this checks if this can handle the given task to it

    void processTask(ColonyTask task); // This performs the task, but in condition that canProcess returns True

    String getName();


}
