package aresbase.tasks;

import aresbase.model.Resource;

import java.util.HashMap;

public abstract class ColonyTask {


    final HashMap<Resource, Integer> required = new HashMap<>();
    final String name;
    final int reward;                       // base credits awarded on success

    public ColonyTask(String name, int reward, int oxygen, int rations, int spare_parts) {
        required.put(Resource.OXYGEN, oxygen);
        required.put(Resource.RATIONS, rations);
        required.put(Resource.SPARE_PARTS, spare_parts);
        this.name = name;
        this.reward = reward;
    }

    public static ColonyTask deserialize(String data) {
        return switch (data) {
            case "Engineering" -> EngineeringTask.deserialize(data);
            case "LifeSupport" -> LifeSupportTask.deserialize(data);
            default -> ResearchTask.deserialize(data);
        };
    }

    public String necessary_resources() {
        return "Oxygen- " + required.get(Resource.OXYGEN) + " Rations- " + required.get(Resource.RATIONS) + " Spare parts- " + required.get(Resource.SPARE_PARTS);
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


    public String getRequiredStr() {
        return "OXYGEN: " + required.get(Resource.OXYGEN) + " RATIONS: " + required.get(Resource.RATIONS) + " SPARE_PARTS: " + required.get(Resource.SPARE_PARTS);
    }

    public abstract String getType(); // will be used in the child classes to return the type of the task
}
