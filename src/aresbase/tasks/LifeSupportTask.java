package aresbase.tasks;

public class LifeSupportTask extends ColonyTask {

    public LifeSupportTask(String name, int oxygen, int spare_parts, int reward) {
        super(name, reward, oxygen, 0, spare_parts);

    }

    public static ColonyTask deserialize(String data) {
        ColonyTask task = switch (data) {
            case "CO2 Scrubber Maintenance" -> new LifeSupportTask("CO2 Scrubber Maintenance", 3, 15, 80);
            case "Water Recycler Repair" -> new LifeSupportTask("Water Recycler Repair", 5, 10, 120);
            default -> new LifeSupportTask("Hydroponics Bay Leak Seal", 2, 10, 90);
        };
        return task;
    }

    @Override
    public String toString() {
        return name +" // " + necessary_resources();
       /* return "[Life Support Task] " + name
                + " | Reward: " + reward + " | Cost: " + necessary_resources(); */
    }

    @Override
    public String getType() {
        return "LifeSupport";
    }


}
