package aresbase.model;

import java.util.Map;

public abstract class ColonyTask {
    private String name;
    private int timetoFix; // this will be in seconds

    private Map<String, Integer> requiredResources;

    public ColonyTask(String name, int timetoFix, Map<String, Integer> requiredResources) {
        this.name = name;
        this.timetoFix = timetoFix;
        this.requiredResources = requiredResources;
    }


    // GETTERS HEREE

    public String getName() {
        return name;
    }

    public int getTimetoFix() {
        return timetoFix;
    }


    public Map<String, Integer> getRequiredResources() {
        return requiredResources;
    }

    
    // USE THIS FOR POLY later

    public abstract String getTaskType();
    
}
