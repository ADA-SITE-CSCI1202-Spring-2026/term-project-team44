package aresbase.tasks;

import aresbase.model.Resource; // import it so this file in this package could see Resource class from other package

import java.util.*;


public class LifeSupportTask extends ColonyTask{

    public LifeSupportTask(String name, int reward, Map<Resource, Integer> required) {

        super(name, reward, required);


    }

    @Override
    public String getType(){
        return "Life Support";
    }

}
