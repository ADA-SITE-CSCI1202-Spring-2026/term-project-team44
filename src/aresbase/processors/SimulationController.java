package aresbase.processors;

import aresbase.manager.ResourceManager;
import aresbase.model.Resource;
import aresbase.tasks.ColonyTask;
import aresbase.tasks.EngineeringTask;
import aresbase.tasks.LifeSupportTask;
import aresbase.tasks.ResearchTask;
import java.io.*;
import java.util.*;

public class SimulationController {

    private final Queue<ColonyTask> taskQueue = new LinkedList<>();
    private final ResourceManager rm;
    private final List<IBaseModule> processors = List.of(new EngineeringBay(), new Hydroponics(), new MedicalWard());
    private final StringBuilder terminal = new StringBuilder();

    private final Random rand = new Random();
    private final String STATE_PATH = System.getProperty("user.dir") + "/src/aresbase/manager/state.txt";


    public SimulationController(ResourceManager rm) {
        this.rm = rm;
    }

    public void spawnRandomTask() {
        ColonyTask task = switch (rand.nextInt(9)) {
            case 0 -> new EngineeringTask("Solar Array Repair", 6, 4, 100);
            case 1 -> new EngineeringTask("Hull Breach Sealing", 9, 6, 250);
            case 2 -> new EngineeringTask("Power Grid Reroute", 4, 2, 110);

            case 3 -> new LifeSupportTask("CO2 Scrubber Maintenance", 3, 15, 80);
            case 4 -> new LifeSupportTask("Water Recycler Repair", 5, 10, 120);
            case 5 -> new LifeSupportTask("Hydroponics Bay Leak Seal", 2, 10, 90);

            case 6 -> new ResearchTask("Pathogen Analysis", 15, 15, 200);
            case 7 -> new ResearchTask("Radiation Exposure Study", 10, 10, 350);
            default -> new ResearchTask("Crew Psych Assessment", 5, 5, 150);
        };
        taskQueue.add(task);
        terminal.append("[Spawn] ").append(task.getName()).append(" added to queue\n");
    }

    public String processNextTask() {
        if (taskQueue.isEmpty()) return "No tasks!";

        ColonyTask task = taskQueue.peek();

        for (IBaseModule module : processors) {
            if (module.canProcess(task)) {
                String success_status = module.processTask(task, rm);
                if (success_status.equals("Success")) {
                    taskQueue.poll();
                    terminal.append("[Completed] ").append(task.getName()).append("\n");
                    return "Success";
                } else {
                    terminal.append("[Failure] ").append(task.getName()).append("\n");
                    terminal.append("[Info] Restock ").append(success_status).append("\n");
                    return success_status;
                }
            }
        }
        terminal.append("[Error] ").append(task.getName()).append(" could not be completed").append("\n");
        return "An error ocurred";
    }

    public String getLogs() {
        return terminal.toString();
    }

    public String buy_resources(Resource r, Integer i) {

        if (i == null || i <= 0) {
            terminal.append("[Error] unsuccessfut purchase request\n");
            return "Unsuccessful";
        }
        int total = i * rm.pricelist.get(r);
        if (total <= rm.getAmount(Resource.CREDITS)) {
            switch (r) {
                case OXYGEN -> rm.buyOxygen(i, total);
                case RATIONS -> rm.buyRations(i, total);
                case SPARE_PARTS -> rm.buySpareParts(i, total);
            }
            terminal.append("[Purchase] ").append(i).append(" units of ").append(r).append("added\n");
            terminal.append("[Expense] -").append(total).append("credits\n");
            return "Success";

        } else {
            terminal.append("[Failure] ").append(total - rm.getAmount(Resource.CREDITS)).append(" Credits needed\n");
            return "Failure";
        }
    }

    public Queue<ColonyTask> getTaskQueue() {
        return new LinkedList<>(taskQueue);
    }

    public void saveState() {
        File file = new File(STATE_PATH);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("=RESOURCES=");
            writer.newLine();
            Map<Resource, Integer> resources = rm.getStock();
            for (Resource resource : resources.keySet()) {
                writer.write(resource + "," + resources.get(resource));
                writer.newLine();
            }

            writer.write("=QUEUE=");
            writer.newLine();
            for (ColonyTask task : taskQueue) {
                writer.write(task.getName());
                writer.newLine();
            }

            writer.write("=LOGS=");
            writer.newLine();
            writer.write(terminal.toString().replace("\n", "\\n"));
            writer.newLine();

            System.out.println("[State] Saved to " + STATE_PATH);

        } catch (IOException e) {
            System.out.println("[State] ERROR saving: " + e.getMessage());
        }
    }


    public void loadState() {
        File file = new File(STATE_PATH);

        if (!file.exists()) {
            System.out.println("[State] No save file found.");
            return;
        }

        taskQueue.clear();
        terminal.setLength(0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String section = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equals("=RESOURCES=")) {
                    section = "RESOURCES";
                    continue;
                }
                if (line.equals("=QUEUE=")) {
                    section = "QUEUE";
                    continue;
                }
                if (line.equals("=LOGS=")) {
                    section = "LOGS";
                    continue;
                }

                switch (section) {
                    case "RESOURCES" -> {
                        String[] parts = line.split(",");
                        rm.setStock(
                                Resource.valueOf(parts[0].trim()),
                                Integer.parseInt(parts[1].trim())
                        );
                    }
                    case "QUEUE" -> {
                        taskQueue.add(ColonyTask.deserialize(line));
                    }
                    case "LOGS" -> {
                        terminal.append(line.replace("\\n", "\n"));
                    }
                }
            }

            System.out.println("[State] Loaded from " + STATE_PATH);

        } catch (IOException e) {
            System.out.println("[State] ERROR loading: " + e.getMessage());
        }
    }


    public void clearStateFile() {
        File stateFile = new File(STATE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(stateFile))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("[State] ERROR clearing state: " + e.getMessage());
        }
    }
}