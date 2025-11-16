import java.util.ArrayList;

/**
 * Player class for the text adventure game. 
 * Creates a player that can travel to different rooms and have items in their
 * inventory.
 *
 * @author Sahar Naz
 * @version 2026.11.16
 */
public class Player {

    private Room currentRoom; // the room the player is currently in
    private ArrayList<Item> inventory;   // the item the player is carrying (can be null)

    /**
     * Constructor for Player.
     * @param startRoom The room where the player starts.
     */
    public Player(Room startRoom) {
        currentRoom = startRoom;
        inventory = new ArrayList<>();
    }

    /**
     * Get the current room of the player.
     * @return The current room.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Set the current room of the player.
     * @param room The room to move to.
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    /**
     * Get the list of items the player is carrying.
     * @return The player's inventory.
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    
    /**
     * Pick up an item.
     * @param item The item to pick up.
     */
    public void pickUp(Item item) {
        inventory.add(item);
    }

     /**
     * Drop an item from the inventory.
     * @param item The item to drop.
     * @return true if item was dropped, false if it was not found.
     */
    public boolean dropItem(Item item) {
        return inventory.remove(item);
    }

     /**
     * Check if the player has a specific item.
     * @param item The item to check.
     * @return true if the player has the item, false otherwise.
     */
    public boolean hasItem(Item item) {
        return inventory.contains(item);
    }
}