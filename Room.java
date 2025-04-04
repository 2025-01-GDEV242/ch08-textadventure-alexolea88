import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @author Alejandro Olea
 * @version 2025.04.02
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private List<Item> items;  // List of items to store multiple in one room

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>(); // initialize as ArrayList
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Add an item to the room.
     * @param item The item object.
     */
    public void addItem(Item item)
    {
        items.add(item);  // Add item to list
    }
    
    /**
     * Removes an item from the room by name.
     * @param itemName The name of the item to remove.
     * @return The removed item, or null if not found.
     */
    public Item removeItem(String itemName)
    {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                items.remove(item);  // Remove the first matching item
                return item;
            }
        }
        return null;  // Not found
    }
    
    /**
     * Get a description of all items in the room.
     * @return A string listing all items in the room.
     */
    public String inspectItems()
    {
        if (items.isEmpty()) {
            return "No items in this room.";
        }
        StringBuilder description = new StringBuilder("Items in this room:\n");
        for (Item item : items) {
            description.append(item.getDescription()).append("\n");
        }
        return description.toString();
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }
    
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Item: key (5kg)
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n" + "\n" + inspectItems();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

