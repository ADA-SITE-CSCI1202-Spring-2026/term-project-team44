package aresbase.manager;

import aresbase.model.Resource;
import aresbase.tasks.ColonyTask;

import java.util.*;

public class ResourceManager {


    private final Map<Resource, Integer> stock = new HashMap<>();

    private int credits; // the money

    public ResourceManager(){
        // The starting part
        stock.put(Resource.OXYGEN, 49);
        stock.put(Resource.RATIONS, 30);
        stock.put(Resource.SPARE_PARTS, 20);
        credits = 399;
    }

    public int getCredits() { // The GUI will call this method so it can display the available amount of credits(money)
        return credits;
    }


    public int getAmount(Resource r){
        return stock.getOrDefault(r,0); // if there resource is not in the map so return 0 instead of crashing
    }


    public Map<Resource, Integer> getAllView(){ // display all the resources at once
        return new HashMap<>(stock);
    }


    public boolean tryToConsume(ColonyTask task){
        Map<Resource, Integer> needed = task.getRequired();

        // FIRST VALIDATE
        for(Map.Entry<Resource, Integer> e: needed.entrySet()){
            if(getAmount(e.getKey()) < e.getValue()){
                return false;
            }
        }

        // IF ALL CHECKS PASSED then deduct from the stock
        for (Map.Entry<Resource, Integer> e : needed.entrySet()) {

            stock.put(e.getKey(), stock.get(e.getKey()) - e.getValue());

        }

        credits += task.getReward();
        return true;
    }

    // BUY 1 UNIT OF given resource, returns true if success, returns false if money is less
    public boolean purchase (Resource r, int quantity, int unitCost) {

        if (quantity <= 0 || unitCost < 0) {
            return false;

        }


        int totalCost = quantity * unitCost;

        if (credits < totalCost) {
            return false;

        }

        credits -= totalCost;

        stock.put(r, getAmount(r) + quantity);
        return true;
    }


    public void loadState (Map<Resource, Integer> restored, int restoredCredits) {
        // this will be used in order to load a saved game
        stock.clear(); // FOR SAFETY
        stock.putAll(restored);
        credits = restoredCredits;


    }



}


