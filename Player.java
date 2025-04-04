import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class Player - A player object that stores the current and previous room of the player along with their inventory of items.
 *
 * @author Alejandro Olea
 * @version 2025.04.02
 */

public class Player
{
    private Room currentRoom;  // The room the player is currently in
    private Stack<Room> roomHistory;  // Stack storing room history
    private List<Item> inventory;  // List of items the player is carrying

    /**
     * Create a player with an initial room.
     * @param startingRoom The room the player starts in.
     */
    public Player(Room startingRoom)
    {
        this.currentRoom = startingRoom;
        this.roomHistory = new Stack<>();  // No previous room at the start
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
            this.roomHistory.push(this.currentRoom);  // Push current room to history
        }
        this.currentRoom = newRoom;
    }

    /**
     * Allow player to go back to the previous room
     */
    public void goBack()
    {
        if (roomHistory.isEmpty()) {
            System.out.println("There is no room to go back to\n");
            System.out.println(currentRoom.getLongDescription());
            return;
        }
        this.currentRoom = roomHistory.pop();
        System.out.println("You go back" + currentRoom.getLongDescription().substring(7));  // Skip "You are "
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
