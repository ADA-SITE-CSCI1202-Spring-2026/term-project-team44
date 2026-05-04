package aresbase.tasks;

import aresbase.model.Resource;
import java.util.HashMap;

public abstract class ColonyTask {


    final HashMap<Resource, Integer> required = new HashMap<>();
    final String name;
    final private int reward;

    public ColonyTask(String name, int reward, int oxygen, int rations, int spare_parts) {
        required.put(Resource.OXYGEN, oxygen);
        required.put(Resource.RATIONS, rations);
        required.put(Resource.SPARE_PARTS, spare_parts);
        this.name = name;
        this.reward = reward;
    }

    public static ColonyTask deserialize(String name) {
        return switch (name) {
            case "Solar Array Repair" -> new EngineeringTask("Solar Array Repair", 6, 4, 100);
            case "Hull Breach Sealing" -> new EngineeringTask("Hull Breach Sealing", 9, 6, 250);
            case "Power Grid Reroute" -> new EngineeringTask("Power Grid Reroute", 4, 2, 110);
            case "CO2 Scrubber Maintenance" -> new LifeSupportTask("CO2 Scrubber Maintenance", 3, 15, 80);
            case "Water Recycler Repair" -> new LifeSupportTask("Water Recycler Repair", 5, 10, 120);
            case "Hydroponics Bay Leak Seal" -> new LifeSupportTask("Hydroponics Bay Leak Seal", 2, 10, 90);
            case "Pathogen Analysis" -> new ResearchTask("Pathogen Analysis", 15, 15, 200);
            case "Radiation Exposure Study" -> new ResearchTask("Radiation Exposure Study", 10, 10, 350);
            case "Crew Psych Assessment" -> new ResearchTask("Crew Psych Assessment", 5, 5, 150);
            default -> throw new IllegalArgumentException("Unknown task name: " + name);
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


    public abstract String getType();
}
