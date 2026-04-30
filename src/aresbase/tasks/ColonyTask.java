package aresbase.tasks;

import aresbase.model.Resource;

import java.util.HashMap;

public abstract class ColonyTask {

    final int oxygen;
    final int rations;
    final int spare_parts;
    final int creds_required;
    final HashMap<Resource, Integer> required = new HashMap<>();
    final String name;
    final int reward;                       // base credits awarded on success

    public ColonyTask(String name, int reward, int oxygen, int rations, int spare_parts, int creds_required) {
        this.name = name;
        this.reward = reward;
        this.oxygen = oxygen;
        this.rations = rations;
        this.spare_parts = spare_parts;
        this.creds_required = creds_required;
        this.required.put(Resource.OXYGEN, oxygen);
        this.required.put(Resource.RATIONS, rations);
        this.required.put(Resource.SPARE_PARTS, spare_parts);
        this.required.put(Resource.CREDITS, creds_required);
    }

    public String getName() {
        return this.name;
    }


    public int getReward() {
        return this.reward;
    }

    public HashMap<Resource, Integer> getRequired() {
        return this.required;

    }


    public abstract  String getType(); // will be used in the child classes to return the type of the task

}
