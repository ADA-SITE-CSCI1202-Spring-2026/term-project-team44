package aresbase.manager;

import aresbase.model.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ResourceManager {
    public final HashMap<Resource, Integer> pricelist = new HashMap<>();
    private final HashMap<Resource, Integer> stock = new LinkedHashMap<>();

    public ResourceManager() {
        stock.put(Resource.OXYGEN, 100);
        stock.put(Resource.RATIONS, 110);
        stock.put(Resource.SPARE_PARTS, 105);
        stock.put(Resource.CREDITS, 500);
        pricelist.put(Resource.OXYGEN, 8);
        pricelist.put(Resource.RATIONS, 12);
        pricelist.put(Resource.SPARE_PARTS, 20);
    }


    public int getAmount(Resource r) {
        return stock.getOrDefault(r, 0); // if there resource is not in the map so return 0 instead of crashing
    }

    public int getPrice(Resource r, int amount) {
        return pricelist.get(r) * amount;
    }


    public HashMap<Resource, Integer> getStock() {
        return (new LinkedHashMap<>(stock));
    }

    public String hasResources(HashMap<Resource, Integer> needed) {
        StringBuilder deficit = new StringBuilder();
        boolean has_enough = true;
        for (HashMap.Entry<Resource, Integer> e : needed.entrySet()) {
            if (stock.get(e.getKey()) < e.getValue()) {
                has_enough = false;
                if (deficit.isEmpty()) {
                    deficit.append("Restock ").append(e.getKey());
                } else deficit.append(", ").append(e.getKey());
            }
        }
        if (has_enough) {
            return "exists";
        } else {
            return deficit.toString();
        }
    }


    public void buyOxygen(int amount, int price) {
        stock.merge(Resource.OXYGEN, amount, Integer::sum);
        stock.merge(Resource.CREDITS, -price, Integer::sum);
    }

    public void consumeOxygen(int amount) {
        stock.merge(Resource.OXYGEN, -amount, Integer::sum);
        if (stock.get(Resource.OXYGEN) < 0) stock.put(Resource.OXYGEN, 0);
    }

    public void consumeRations(int amount) {
        stock.merge(Resource.RATIONS, -amount, Integer::sum);
        if (stock.get(Resource.RATIONS) < 0) stock.put(Resource.RATIONS, 0);
    }

    public void buyRations(int amount, int price) {
        stock.merge(Resource.RATIONS, amount, Integer::sum);
        stock.merge(Resource.CREDITS, -price, Integer::sum);
    }

    public void consumeSpareParts(int amount) {
        stock.merge(Resource.SPARE_PARTS, -amount, Integer::sum);
        if (stock.get(Resource.SPARE_PARTS) < 0) stock.put(Resource.SPARE_PARTS, 0);
    }

    public void buySpareParts(int amount, int price) {
        stock.merge(Resource.SPARE_PARTS, amount, Integer::sum);
        stock.merge(Resource.CREDITS, -price, Integer::sum);
    }

    public void earn_reward(int reward) {
        stock.merge(Resource.CREDITS, reward, Integer::sum);
    }

    public void setStock(Resource r, int amount) {
        stock.put(r, amount);
    }


}
