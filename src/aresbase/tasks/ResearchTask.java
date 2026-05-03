package aresbase.tasks;

// this is the Parent Task class and other xTask classes extend this class
public class ResearchTask extends ColonyTask {

    public ResearchTask(String name, int oxygen, int rations, int reward) {
        super(name, reward, oxygen, rations, 0);

    }

    public static ColonyTask deserialize(String data) {
        ColonyTask task = switch (data) {
            case "Pathogen Analysis" -> new ResearchTask("Pathogen Analysis", 15, 15, 200);
            case "Radiation Exposure Study" -> new ResearchTask("Radiation Exposure Study", 10, 10, 350);
            default -> new ResearchTask("Crew Psych Assessment", 5, 5, 150);
        };
        return task;
    }

    @Override
    public String toString() {
        return name  +" // "+ necessary_resources();
        /*
        return "[Research Task] " + name
                + " | Reward: " + reward + " | Cost: " + necessary_resources(); */
    }

    @Override
    public String getType() {
        return "Research";
    }

}
