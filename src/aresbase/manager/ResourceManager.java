package aresbase.manager;

import aresbase.model.Resource;
import aresbase.tasks.ColonyTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.*;
import java.util.*;

public class ResourceManager {
    private final ObservableMap<Resource, Integer> stock = FXCollections.observableMap(new LinkedHashMap<>());
    private final Queue<ColonyTask> taskQueue = new LinkedList<>();

    public ResourceManager() {
        // The starting part
        stock.put(Resource.OXYGEN, 49);
        stock.put(Resource.RATIONS, 30);
        stock.put(Resource.SPARE_PARTS, 20);
        stock.put(Resource.CREDITS, 399);
    }

    public void addResource(Resource key, Integer value) {
        stock.put(key, value);
    }

    public void addTask(ColonyTask task) {
        taskQueue.add(task);
    }

    public Integer getResourceQuantity(Resource key) {
        return stock.get(key);
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


    public ObservableMap<Resource, Integer> getStock() {
        return stock;
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

    public Queue<ColonyTask> getTaskQueue() {
        Queue<ColonyTask> queue = new LinkedList<>(taskQueue);
        return queue;
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

    public void saveState(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/aresbase/manager/state.txt"))) {
            for (Map.Entry<Resource, Integer> ent : stock.entrySet()) {
                writer.write(ent.getKey().toString() + "=" + ent.getValue() + "\n");
            }
            writer.write("\n");
            for (ColonyTask task : taskQueue) {
                writer.write(task.serialize() + "\n");
            }
            writer.write("\nlogs=\n");
            writer.write(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getState() {
        File file = new File("src/aresbase/manager/state.txt");

        if (!file.exists()) {
            System.out.println("Failed to restore state");
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (Resource i : stock.keySet()) {
                String str = reader.readLine();
                if (str != null) {
                    if (str.contains("=")) {
                        String num = str.split("=")[1];
                        stock.put(i, Integer.valueOf(num));
                    }
                }

            }
            reader.readLine();
            String line;
            StringBuilder builder = new StringBuilder();
            boolean read = false;
            while ((line = reader.readLine()) != null) {
                if (line.equals("logs=")) {
                    read = true;
                    continue;
                }
                if (read) {
                    builder.append(line).append("\n");
                } else {
                    taskQueue.add(ColonyTask.deserialize(line));
                }
            }
            return builder.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}


