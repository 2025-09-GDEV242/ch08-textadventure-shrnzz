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
    private Item inventory;   // the item the player is carrying (can be null)

    /**
     * Constructor for Player.
     * @param startRoom The room where the player starts.
     */
    public Player(Room startRoom) {
        currentRoom = startRoom;
        inventory = null; // player starts with no item
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
     * Get the item the player is carrying.
     * @return The item, or null if none.
     */
    public Item getInventory() {
        return inventory;
    }

    /**
     * Pick up an item.
     * @param item The item to pick up.
     */
    public void pickUp(Item item) {
        inventory = item;
    }

    /**
     * Drop the item the player is carrying.
     * @return The item dropped, or null if none.
     */
    public Item dropItem() {
        Item temp = inventory;
        inventory = null;
        return temp;
    }

    /**
     * Check if the player has an item.
     * @return true if the player has an item, false otherwise.
     */
    public boolean hasItem() {
        return inventory != null;
    }
}