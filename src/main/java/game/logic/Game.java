package game.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import game.entities.Item;
import game.entities.Player;
import game.entities.Room;

public class Game {
    private Player player;
    private List<Room> rooms;
    private List<Item> items;
    private Room currentRoom;
    private Room enemyRoom;

    public Game(String configurationFile) {
        initializeRooms(configurationFile);
    }

    private void initializeRooms(String configurationFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(configurationFile))) {
            String line = br.readLine(); // read number of rooms
            int numberOfRooms = 0;
            int numberOfItems = 0;

            if(line != null && (numberOfRooms = Integer.parseInt(line)) > 0) {
                rooms = new LinkedList<Room>();

                // Initialize rooms with no exits
                for(int i = 0; i < numberOfRooms; i++) {
                    line = br.readLine();
                    rooms.add(new Room(i, line));
                }

                // Add exits to each room
                for(int i = 0; i < numberOfRooms; i++) {
                    Room tempRoom = null;
                    String[] strs;

                    line = br.readLine();
                    strs = line.split(" ");
                    tempRoom = findRoomByRoomStringID(strs[0]);
                    for(int j = 1; j < strs.length; j += 2)
                        tempRoom.addExit(strs[j], findRoomByRoomStringID(strs[j+1]));
                }

                line = br.readLine(); // read number of items
                if(line != null && (numberOfItems = Integer.parseInt(line)) > 0) {
                    items = new LinkedList<Item>();
                    for(int i = 0; i < numberOfItems; i++) {
                        String[] strs = br.readLine().split(" ");
                        String[] itemInfo = strs[0].split("-");
                        Room tempRoom = findRoomByRoomStringID(strs[1]);
                        Item tempItem = new Item(i, strs[0], itemInfo[0], tempRoom);

                        items.add(tempItem);
                        tempRoom.addItem(tempItem);
                    }
                } else {
                    System.out.println("Please specify a positive integer value for the number of items.");
                }
            } else {
                System.out.println("Please specify a positive integer value for the number of rooms.");
            }

        } catch (IOException e) {
            System.out.println("Failed to initialized game: Error in configuration file.");
        }
    }

    private void printItems() {
        for(Item item : items)
            System.out.println("There is a " + item.getItemType() + " in " + item.getLocation().getRoomStringID());
    }

    private void printRooms() {
        for(Room room : rooms) {
            System.out.println(room.getRoomID() + " is a " + room.getRoomType() + " and has " + room.getExits().size() + " exit(s):");
            printExits(room);
            System.out.println(room.getRoomID() + " also has the following items: ");
            printItems(room);
        }
    }

    private void printItems(Room room) {
        for(Item i : room.getItems())
            System.out.println("  " + i.getItemType());
    }

    private void printItems(Player player) {
        for(Item i : player.getItems())
            System.out.println("  " + i.getItemType());
    }

    private void printExits(Room room) {
        HashMap<String, Room> exits = room.getExits();
        for(String direction : exits.keySet())
            System.out.println("  " + direction + " towards a " + exits.get(direction).getRoomType());
    }

    private Room findRoomByRoomStringID(String roomStringID) {
        for(Room room : rooms)
            if(room.getRoomStringID().equals(roomStringID))
                return room;
        return null;
    }

    private Item findItemByItemType(Room room, String itemType) {
        for(Item item : room.getItems())
            if(item.getItemType().equalsIgnoreCase(itemType)) return item;
        return null;
    }
    
    private Item findItemByItemType(Player player, String itemType) {
        for(Item item : player.getItems())
            if(item.getItemType().equalsIgnoreCase(itemType)) return item;
        return null;
    }

    public void play() {
        int startRoomID = (int) (Math.random() * rooms.size());
        int enemyStartRoomID = (int) (Math.random() * rooms.size());
        String playerName;
        Scanner sc = new Scanner(System.in);
        boolean finished = false;
        String commandString;

        currentRoom = rooms.get(startRoomID);

        while(startRoomID == enemyStartRoomID)
            enemyStartRoomID= (int) (Math.random() * rooms.size());
        
        enemyRoom = rooms.get(enemyStartRoomID);

        System.out.print("What is your name? ");
        playerName = sc.nextLine();
        player = new Player(playerName);

        System.out.println("Welcome to the Dungeon, " + player.getName() + "...");
        System.out.println("In this place is a monster that can kill you if you are unable to arm yourself.");
        System.out.println("Find yourself a weapon and a shield or armor of some kind.");
        System.out.println("But be careful, as while you are moving around, IT is too...");

        do {
            Command currentCommand;

            System.out.println("You are in a " + currentRoom.getRoomType());
            System.out.println("You can go...");
            printExits(currentRoom);
            if(currentRoom.getItems().size() > 0) {
                System.out.println("There are items in the room:");
                printItems(currentRoom);
            }
            System.out.print("> ");
            commandString = sc.nextLine();
            currentCommand = new Command(commandString);

            switch(currentCommand.getCommandType()) {
                case GOTO:
                    String direction = currentCommand.getCommandArguments()[0];
                    if(!move(direction)) System.out.println("There's nowhere to go there.");
                    else moveEnemy();
                    break;
                case PICKUP:
                    if(!pickupItem(currentCommand.getCommandArguments()[0])) System.out.println("There is no " + currentCommand.getCommandArguments()[0] + "here.");
                    break;
                case PUTDOWN:
                    if(!putdownItem(currentCommand.getCommandArguments()[0])) System.out.println("You don't have a/an " + currentCommand.getCommandArguments()[0] + ".");
                    break;
                case INVENTORY:
                    if(player.getItems().size() > 0) {
                        System.out.println("You are carrying:");
                        printItems(player);
                    } else System.out.println("You are not carrying anything.");
                    break;
                case QUIT:
                    System.out.println("Bye!");
                    finished = true;
                    break;
                case HELP:
                    printHelpMessage();
                    break;
                case UNDEFINED:
                    System.out.println("You can't do that.");
                    break;
            }

            if(currentRoom == enemyRoom) {
                System.out.println("You enter a room to find a monster.");
                if((player.hasItem("knife") || player.hasItem("sword")) && (player.hasItem("shield") || player.hasItem("hauberk"))) {
                    System.out.println("Fortunately, you gathered enough equipment to stand your ground.");
                    System.out.println("You are victorious.");
                } else {
                    System.out.println("You scrabble around desperately for something --- anything --- to defend yourself.");
                    System.out.println("You come up with nothing.");
                    System.out.println("Your mouth opens in a silent scream as he charges toward you.");
                    System.out.println("You died.");
                }
                finished = true;
            }
            
        } while(!finished);

        sc.close();
    }

    private boolean putdownItem(String itemType) {
        Item tempItem = findItemByItemType(player, itemType);
        if(tempItem != null) {
            player.removeItem(tempItem.getItemName());
            currentRoom.addItem(tempItem);
            tempItem.setLocation(currentRoom);
            return true;
        }
        return false;
    }

    private boolean pickupItem(String itemType) {
        Item tempItem = findItemByItemType(currentRoom, itemType);
        if(tempItem != null) {
            player.addItem(tempItem);
            tempItem.setLocation(null);
            currentRoom.removeItem(tempItem.getItemName());
            return true;
        }
        return false;
    }

    private boolean move(String destination) {
        if(currentRoom.getExits().containsKey(destination)) {
            currentRoom = currentRoom.getExits().get(destination);
            return true;
        } else return false;
    }

    private void moveEnemy() {
        Set<String> exits = enemyRoom.getExits().keySet();
        int randomExit = (int) (Math.random() * exits.size());
        int i = 0;
        for(String exit : exits) {
            if(i == randomExit) enemyRoom = enemyRoom.getExits().get(exit);
            i++;
        }
    }

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: java Game /path/to/configuration/file.txt");
            System.exit(0);
        }
        Game g = new Game(args[0]);
        g.play();
    }

    private void printHelpMessage() {
        System.out.println("Possible commands:");
        System.out.println("  go [direction]");
        System.out.println("  pickup [item]");
        System.out.println("  putdown [item]");
        System.out.println("  quit");
    }
}
