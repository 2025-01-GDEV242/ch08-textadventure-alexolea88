import java.util.ArrayList;
import java.util.List;

/**
 * Class Item - an item in an adventure game.
 * 
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * Represents an item in the game.
 * 
 * @author Alejandro Olea
 * @version 2025.04.02
 */

public class Item {
    private String name;
    private String description;
    private double weight;

    /**
     * Constructs an item with a name, description, and weight.
     * @param name The name of the item.
     * @param description A brief description of the item.
     * @param weight The weight of the item.
     */
    public Item(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The weight of the item.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return A string representation of the item.
     */
    @Override
    public String toString() {
        return name + " - " + description + " (Weight: " + weight + ")";
    }
}
