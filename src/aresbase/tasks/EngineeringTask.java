package aresbase.tasks;

public class EngineeringTask extends ColonyTask {
    public EngineeringTask(String name, int rations, int spare_parts, int reward) {
        super(name, reward, 0, rations, spare_parts);
    }

    // LATER ADD method to return type

    public static ColonyTask deserialize(String data) {
        ColonyTask task = switch (data) {
            case "Solar Array Repair" -> new EngineeringTask("Solar Array Repair", 6, 4, 100);
            case "Hull Breach Sealing" -> new EngineeringTask("Hull Breach Sealing", 9, 6, 250);
            default -> new EngineeringTask("Power Grid Reroute", 4, 2, 110);
        };
        return task;
    }

    @Override
    public String toString() {
        return name +" // " + necessary_resources();
       /* this version overwhelms the list visually
       return "[Engineering Task] " + name
                + " | Reward: " + reward + " | Cost: " + necessary_resources(); */
    }

    @Override
    public String getType() {
        return "Engineering";
    }

}
