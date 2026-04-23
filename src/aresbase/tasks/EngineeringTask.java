package aresbase.tasks;
import java.util.*;

import aresbase.model.Resource;

public class EngineeringTask extends ColonyTask {

    public EngineeringTask(String name, int reward, Map<Resource, Integer> required){
        super(name,reward, required);


    }

    // LATER ADD method to return type

    @Override
    public String getType(){
        return "Engineering";
    }
}
