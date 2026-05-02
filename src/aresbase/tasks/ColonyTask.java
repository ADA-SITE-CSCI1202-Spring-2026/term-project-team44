package aresbase.tasks;

import aresbase.model.Resource;

import java.util.HashMap;

public abstract class ColonyTask {

    final int oxygen;
    final int rations;
    final int spare_parts;
    final HashMap<Resource, Integer> required = new HashMap<>();
    final String name;
    final int reward;                       // base credits awarded on success
    boolean isDone;

    public ColonyTask(String name, int reward, int oxygen, int rations, int spare_parts, boolean isDone) {
        this.name = name;
        this.reward = reward;
        this.oxygen = oxygen;
        this.rations = rations;
        this.spare_parts = spare_parts;
        this.isDone = isDone;
        this.required.put(Resource.OXYGEN, oxygen);
        this.required.put(Resource.RATIONS, rations);
        this.required.put(Resource.SPARE_PARTS, spare_parts);
    }

    public String getName() {
        return this.name;
    }

    public String serialize() {
        return this.toString()+" "+this.isDone;
    }
    public static ColonyTask deserialize(String line) {
        String[] values= line.split(" ");
        ColonyTask task=new EngineeringTask(false);
        switch (values[0]){
            case "EngineeringTask":
                task = new EngineeringTask(Boolean.valueOf(values[1]));
                break;
            case "LifeSupportTask":
                task = new LifeSupportTask(Boolean.valueOf(values[1]));
                break;
            case "ResearchTask":
                task = new ResearchTask(Boolean.valueOf(values[1]));
        }
        return task;

    }


    public int getReward() {
        return this.reward;
    }

    public HashMap<Resource, Integer> getRequired() {
        return this.required;

    }


    public abstract  String getType(); // will be used in the child classes to return the type of the task

}
