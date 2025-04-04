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
 * @author Michael Kölling and David J. Barnes
 * @author Alejandro Olea
 * @version 2025.04.02
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player(currentRoom);   // Create a player with the starting room
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, overlook, arcade, garage;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        overlook = new Room("in the scenic overlook");
        arcade = new Room("in the campus arcade");
        garage = new Room("in the parking garage");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", overlook);
        
        overlook.setExit("south", outside);
        overlook.setExit("east", garage);
        
        arcade.setExit("north", pub);
        arcade.setExit("east", lab);
        
        garage.setExit("west", overlook);
        garage.setExit("south", theater);

        theater.setExit("west", outside);
        theater.setExit("north", garage);

        pub.setExit("east", outside);
        pub.setExit("south", arcade);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("west", arcade);

        office.setExit("west", lab);
        
        // Add items to rooms with proper constructor arguments
        outside.addItem(new Item("map", "A detailed map of the university campus", 0.2));
        
        pub.addItem(new Item("beer", "A cold glass of beer", 1.2));
        pub.addItem(new Item("water", "A room temperature water", 1));
        
        lab.addItem(new Item("laptop", "A black touchscreen laptop", 2.5));
        
        theater.addItem(new Item("key", "A small metal key", 0.3));
        
        garage.addItem(new Item("ticket", "A ticket for the university parking", 0.1));
        garage.addItem(new Item("wallet", "A stranger's missing wallet", 0.4));
        
        arcade.addItem(new Item("token", "A golden arcade token used to play games", 0.3));
        
        overlook.addItem(new Item("binoculars", "A blue pair of binoculars to see into the distance", 1.5));
        
        office.addItem(new Item("stapler", "A silver handheld stapler", 0.5));
        office.addItem(new Item("keyboard", "An ergonomic keyboard", 1.8));

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
                
            case BACK:
                goBack();
                break;
                
            case TAKE:
                takeItem(command);
                break;
                
            case DROP:
                dropItem(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case INSPECT:
                System.out.println(currentRoom.inspectItems());
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
     * Print the description of the current room.
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            player.moveToRoom(nextRoom);  // Update player's room
            currentRoom = nextRoom;       // Keep Game's currentRoom in sync
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Allow player to backtrack to previous room
     */
    private void goBack()
    {
        player.goBack();
        currentRoom = player.getCurrentRoom();  // Sync room
    }

    /**
     * Allow the player to take an item from the current room.
     * If no item exists or no item name is specified, prints an error message.
     */
    private void takeItem(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item item = player.getCurrentRoom().removeItem(itemName);
        
        if (item != null) {
            player.takeItem(item);
            System.out.println("You picked up the " + item.getName() + ".");
        } else {
            System.out.println("There is no such item here.");
        }
    }
    
    /**
     * Allows the player to drop an item
     */
    private void dropItem(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item item = findItemInInventory(itemName);
        
        if (item != null) {
            player.dropItem(item);
            player.getCurrentRoom().addItem(item);
            System.out.println("You dropped the " + item.getName() + ".");
        } else {
            System.out.println("You don't have that item.");
        }
    }
    
    /**
     * Find an item in the player's inventory
     */
    private Item findItemInInventory(String itemName)
    {
        for (Item item : player.getInventory()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
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
        } else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Main method to run the game standalone.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) 
    {
        Game game = new Game();  // Create a new game instance
        game.play();             // Start the game
    }
}
