package au.edu.usc;
import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // ENTER FILE NAME below
        File file = new File("T1_test_cases/input_01.txt");
        //--------------------

        // Create a HashMap to store the inventory of items (item name -> quantity)
        HashMap<String, Integer> inventory = new HashMap<>();
        // Create a HashMap to store the cost of each item (item name -> cost)
        HashMap<String, Double> itemCosts = new HashMap<>();
        double finCost = 0.0; // Initialize the total cost

        try {
            // Create a Scanner to read the input file
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String line = scan.nextLine();

                // Process "ADD" commands
                if (line.startsWith("ADD")) {
                    String[] tokens = line.split(" ");
                    if (tokens.length != 0) {
                        String itemName = tokens[1];
                        int quantity = Integer.parseInt(tokens[2]);
                        double cost = Double.parseDouble(tokens[3]);

                        // Update inventory
                        inventory.put(itemName, inventory.getOrDefault(itemName, 0) + quantity);
                        // Update item cost
                        itemCosts.put(itemName, cost);

                        // Update total cost
                        finCost = finCost - (cost * quantity); // Fix subtraction here
                    }
                }
                // Process "SELL" commands
                else if (line.startsWith("SELL")) {
                    String[] tokens = line.split(" ");
                    if (tokens.length != 0) {
                        String sellItemName = tokens[1];
                        int sellQuantity = Integer.parseInt(tokens[2]);
                        double sellCost = Double.parseDouble(tokens[3]);

                        // Ensure there is enough quantity to sell
                        if (inventory.containsKey(sellItemName) && inventory.get(sellItemName) >= sellQuantity) {
                            // Update inventory
                            inventory.put(sellItemName, inventory.get(sellItemName) - sellQuantity);

                            // Update total cost
                            finCost = finCost + (sellCost * sellQuantity); // Fix addition here
                        }
                    }
                }
                // Process "WRITEOFF" commands
                else if (line.startsWith("WRITEOFF")) {
                    String[] tokens = line.split(" ");
                    if (tokens.length == 3) {
                        String woItemName = tokens[1];
                        int woQuantity = Integer.parseInt(tokens[2]);

                        // Check if the item exists in inventory and itemCosts
                        if (inventory.containsKey(woItemName) && itemCosts.containsKey(woItemName)) {
                            // Get the previous cost of the item
                            double woCost = itemCosts.get(woItemName);

                            // Update inventory
                            inventory.put(woItemName, inventory.get(woItemName) - woQuantity);
                            // Update total cost
                            finCost = finCost - (woCost * woQuantity);
                        }
                    }
                }
                // Process "DONATE" commands
                else if (line.startsWith("DONATE")) {
                    String[] tokens = line.split(" ");
                    if (tokens.length == 3) {
                        String donItemName = tokens[1];
                        int donQuantity = Integer.parseInt(tokens[2]);

                        // Remove donated items from inventory
                        if (inventory.containsKey(donItemName)) {
                            inventory.put(donItemName, inventory.get(donItemName) - donQuantity);
                        }
                    }
                }
                // Process "RETURN" commands
                else if (line.startsWith("RETURN")) {
                    String[] tokens = line.split(" ");
                    if (tokens.length != 0) {
                        int reQuantity = Integer.parseInt(tokens[2]);
                        double reCost = Double.parseDouble(tokens[3]);

                        // Update total cost
                        finCost = finCost + (reCost * reQuantity);

                    }
                }
                // Process "CHECK" commands
                else if (line.equals("CHECK")) {
                    // Print the current inventory
                    System.out.println("Current Inventory:");
                    // Sort the inventory items alphabetically by name
                    TreeMap<String, Integer> sortedInventory = new TreeMap<>(inventory);
                    for (Map.Entry<String, Integer> entry : sortedInventory.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                }
                // Process "PROFIT" commands
                else if (line.equals("PROFIT")) {
                    // Print the total profit/loss
                    System.out.println("Profit/Loss: $" + finCost);
                }
            }

            // Close the scanner
            scan.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}