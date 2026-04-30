package aresbase.manager;

import aresbase.model.Resource;
import aresbase.tasks.ColonyTask;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ResourceManager {

    private HashMap<Resource, Integer> stock = new HashMap<>();
    private Queue<ColonyTask> taskQueue = new LinkedList<>();

    public ResourceManager() {
        // The starting part
        stock.put(Resource.OXYGEN, 49);
        stock.put(Resource.RATIONS, 30);
        stock.put(Resource.SPARE_PARTS, 20);
        stock.put(Resource.CREDITS, 399);
    }

    public void addTask(ColonyTask task) {
        taskQueue.add(task);
    }

    public ColonyTask getTask() {
        return taskQueue.poll();
    }

    public int getCredits() { // The GUI will call this method so it can display the available amount of credits(money)
        return stock.get(Resource.CREDITS);
    }


    public int getAmount(Resource r) {
        return stock.getOrDefault(r, 0); // if there resource is not in the map so return 0 instead of crashing
    }


    public HashMap<Resource, Integer> getAllView() { // display all the resources at once
        return new HashMap<>(stock);
    }


    public boolean tryToConsume(ColonyTask task) {

        HashMap<Resource, Integer> needed = task.getRequired();

        // VALIDATE
        for (HashMap.Entry<Resource, Integer> e : needed.entrySet()) {
            if (getAmount(e.getKey()) < e.getValue()) {
                return false;
            }
        }

        // APPLY
        for (HashMap.Entry<Resource, Integer> e : needed.entrySet()) {
            stock.merge(e.getKey(), -e.getValue(), Integer::sum);
        }

        stock.merge(Resource.CREDITS, task.getReward(), Integer::sum);
        return true;
    }

    // BUY 1 UNIT OF given resource, returns true if success, returns false if money is less
    public boolean purchase(Resource r, int quantity, int unitCost) {

        if (quantity <= 0 || unitCost < 0) {
            return false;

        }


        int totalCost = quantity * unitCost;

        if (stock.get(Resource.CREDITS) < totalCost) {
            return false;

        }
        stock.put(Resource.CREDITS, stock.get(Resource.CREDITS) - totalCost);


        stock.put(r, getAmount(r) + quantity);
        return true;
    }


    public void loadState(HashMap<Resource, Integer> restored) {
        // this will be used in order to load a saved game
        stock.clear(); // FOR SAFETY
        stock.putAll(restored);


    }


}


