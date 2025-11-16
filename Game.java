import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Sahar Naz
 * @version 2025.11.16
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom; // stores the last room visited
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player(currentRoom); // starts the player in the starting room
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
    
        // add multiple items for each room 
        outside.addItem(new Item("rock", 2));
        outside.addItem(new Item("leaf", 1));
    
        theater.addItem(new Item("projector", 10));
        theater.addItem(new Item("chalk", 1));
    
        pub.addItem(new Item("beer", 1));
        pub.addItem(new Item("pretzel", 1));
    
        lab.addItem(new Item("laptop", 5));
        lab.addItem(new Item("keyboard", 2));
        lab.addItem(new Item("mouse", 1));
    
        office.addItem(new Item("stapler", 1));
        office.addItem(new Item("notebook", 2));

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            //added case for look
            case LOOK:
                look();
                break;
                
            // added case for take 
            case TAKE:
                takeItem(command);
                break;
                
            //added case to go back
            case BACK:
                goBack();
                break;
                
            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // get exit from players current room 
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setCurrentRoom(nextRoom); // move player to next room
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }
    
    // Print thelong description of the current room
    private void look(){
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Player picks up an item from the current room.
     * @param command The take command with the item name.
     */
    private void takeItem(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
    
        String itemName = command.getSecondWord();
        ArrayList<Item> items = currentRoom.getItems(); // get all items in room
    
        for(Item item : items) {
            if(item.getDescription().equalsIgnoreCase(itemName)) {
                if(player.hasItem()) {
                    System.out.println("You are already carrying an item!");
                    return;
                } else {
                    player.pickUp(item);
                    items.remove(item);
                    System.out.println("You picked up the " + item.getDescription());
                    return;
                }
            }
        }
    
        System.out.println("There is no " + itemName + " here.");
    }
    //Move the player back to the previous room.
    // If there is no room, prints the player can't go back any further
    // updates the previous room so the player can go back and forth 
    private void goBack() {
        if (previousRoom == null) {
            System.out.println("You can't go back any further!");
        } else {
            Room temp = currentRoom; // store current
            currentRoom = previousRoom; // go back
            previousRoom = temp; // update previous room for next back
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
