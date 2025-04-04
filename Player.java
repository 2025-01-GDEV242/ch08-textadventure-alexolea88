import java.util.ArrayList;
import java.util.List;

/**
 * Class Player - A player object that stores the current and previous room of the player along with their inventory of items.
 *
 * @author Alejandro Olea
 * @version 2025.04.02
 */

public class Player
{
    private Room currentRoom;  // The room the player is currently in
    private Room previousRoom;  // The room the player was previously in
    private List<Item> inventory;  // List of items the player is carrying

    /**
     * Create a player with an initial room.
     * @param startingRoom The room the player starts in.
     */
    public Player(Room startingRoom)
    {
        this.currentRoom = startingRoom;
        this.previousRoom = null;  // No previous room at the start
        this.inventory = new ArrayList<>();
    }

    /**
     * Get the current room of the player.
     * @return The current room.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Get the previous room of the player.
     * @return The previous room.
     */
    public Room getPreviousRoom() {
        return previousRoom;
    }

    /**
     * Set the player's current room.
     * @param room The new room the player is in.
     */
    public void setCurrentRoom(Room room)
    {
        this.currentRoom = room;
    }
    
    /**
     * Move to a new room and store the current room as previous
     */
    public void moveToRoom(Room newRoom)
    {
        if (this.currentRoom != null) {
            this.previousRoom = this.currentRoom;  // Save current room as previous room before moving
        }
        this.currentRoom = newRoom;  // Update the current room
    }

    /**
     * Allow player to go back to the previous room
     */
    public void goBack() {
        if (previousRoom == null) {
            System.out.println("No previous room to go back to.");
            return;
        }
        Room temp = currentRoom;
        currentRoom = previousRoom;
        previousRoom = temp;  // Swap the rooms so "back" can be used again
    }

    /**
     * Add an item to the player's inventory.
     * @param item The item to add.
     */
    public void takeItem(Item item)
    {
        inventory.add(item);
    }

    /**
     * Remove an item from the player's inventory.
     * @param item The item to remove.
     * @return true if the item was removed successfully.
     */
    public boolean dropItem(Item item)
    {
        return inventory.remove(item);
    }

    /**
     * Get a list of all items the player is carrying.
     * @return List of items.
     */
    public List<Item> getInventory()
    {
        return inventory;
    }
}
