package aresbase.tasks;

import java.util.*;

public abstract class ColonyTask {

    private final String name;
    private final int reward;                       // base credits awarded on success
    private final Map<Resource, Integer> required;  // resources needed to complete

     ColonyTask(String name, int reward, Map<Resource, Integer> required) {
        this.name = name;
        this.reward = reward;
        this.required = new HashMap<>(required);
    }

    public String getName(){
         return this.name;
    }

    public int getReward(){
         return this.reward;
    }

    public Map<Resource, Integer> getRequired(){
         return new HashMap<>(required);
    }

}
