import java.util.ArrayList;
import java.util.Scanner;

public class DungeonEscape {
    //main method to run the program
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("What is your name, heroic adventurer?");
        String charName = scan.nextLine();
        //initializing a breakout variable to use in the while loop, variable never changes to keep loop running
        int breakOut = 0;
        //initializing the gold count to print out to the console
        int goldCount = 0;
        //while loop that continuously runs until game is over
        while (breakOut == 0) {
            System.out.print("How wide of a dungeon do you want to face (5-10)?");
            int dungeonSize = scan.nextInt();
            //conditional to set parameters of allowed dungeon size
            if (dungeonSize >= 5 && dungeonSize <= 10) {
                //list to hold monsters, calling a method
                ArrayList<Player> monstersList = makeMonsters(dungeonSize);
                //main character object created
                Player mainChar = makePlayer(charName);
                //list to hold the gold objects
                ArrayList<Player> goldList = makeGoldList(dungeonSize);
                //while loop that runs the actual game, while the main character is alive
                while (mainChar.isAlive() == true) {
                    int monsterCount = countNearbyMonsters(mainChar, monstersList);
                    //lists to hold monsters or gold that is in the same room as the main character
                    ArrayList<Player> monstersInRoom = mainChar.inSameRoom(monstersList);
                    ArrayList<Player> goldInRoom = mainChar.goldInSameRoom(goldList);
                    System.out.println(mainChar);
                    System.out.println("You smell " +monsterCount+ " monsters nearby.");
                    System.out.println("Which way do you want to go?");
                    String direction = scan.next();
                    /* for each loop to cycle through each of the gold objects (if multiple in same room) 
                    in list that is in the same room as the main character */
                    for (Player gold: goldInRoom) {
                        if (goldInRoom.contains(gold)) {
                            goldCount++;
                            System.out.println("You have collected secret gold!");
                            System.out.println("You have a total gold count of " +goldCount+ ".");
                        }
                    }
                    /* for each loop to cycle through each of the monster objects (if multiple in same room) 
                    in list that is in the same room as the main character */
                    for (Player monster: monstersInRoom) {
                        //while loop to allow main character and monster to fight while both are alive
                        while (monster.isAlive() == true && mainChar.isAlive() == true) {
                            fightMonsters(mainChar, monster);
                            if (mainChar.isAlive() == false) {
                                System.out.println("Sorry, your character has died.");
                                //exit out of program if player dies
                                System.exit(0);
                            }
                        }
                    }
                    //moving the character
                    moveOne(mainChar, dungeonSize, direction);
                    if (mainChar.hasEscaped(dungeonSize) == true) {
                        System.out.println("You have escaped the dungeon!");
                        //exiting out of program if player escapes
                        System.exit(0);
                    }
                }
            }
            else {
                //handling invalid dungeon sizes
                System.out.println("That is not a valid dungeon size!");
            }
        }
    }
    //method to make main character (from player class)
    public static Player makePlayer(String charName) {
        Player player = new Player(charName, 100, 10, 0, 0);
        return player;
    }
    //method to get a random row for the monster and gold objects to appear
    public static int randomRow(int dungeonSize) {
        int randomRow = (int)((Math.random() * dungeonSize) + 1);
        return randomRow;
    }
    //method to get a random column for the monster and gold objects to appear
    public static int randomColumn(int dungeonSize) {
        int randomColumn = (int)((Math.random() * dungeonSize) + 1);
        return randomColumn;
    }
    //method to make a monster object (from player class)
    public static Player makeMonster(String monsterName, int randomRow, int randomColumn) {
        Player monster = new Player(monsterName, 25, 5, randomRow, randomColumn);
        return monster;
    }
    //method to make gold object (from player class)
    public static Player makeGold(int randomRow, int randomColumn) {
        Player gold = new Player(randomRow, randomColumn);
        return gold;
    }
    //storing all the gold in a list
    public static ArrayList<Player> makeGoldList(int dungeonSize) {
        ArrayList<Player> goldList = new ArrayList<>();
        //variable allowing for rare gold occurences throughout dungeon
        int numGold = (int)(Math.pow(dungeonSize, 2)) / 8;
        //loop to add gold to list
        for (int i = 1; i <= numGold; i++) {
            int randomRow = randomRow(dungeonSize);
            int randomColumn = randomColumn(dungeonSize);
            Player gold = makeGold(randomRow, randomColumn);
            goldList.add(gold);
        }
        return goldList;
    }
    //storing all the monsters in a list
    public static ArrayList<Player> makeMonsters(int dungeonSize) {
        ArrayList<Player> monsters = new ArrayList<>();
        //variable to allow for specified number of monsters per dungoen size
        int numMonsters = (int)(Math.pow(dungeonSize, 2)) / 6;
        //loop to add all monsters to list
        for (int i = 1; i <= numMonsters; i++) {
            String monsterName = "Monster " + i;
            int randomRow = randomRow(dungeonSize);
            int randomColumn = randomColumn(dungeonSize);
            Player monster = makeMonster(monsterName, randomRow, randomColumn);
            monsters.add(monster);
        }
        return monsters;
    }
    //method that allows for main character and monsters to fight
    public static void fightMonsters(Player player, Player monster) {
            while (monster.isAlive() == true && player.isAlive() == true) {
                player.fight(monster);
                monster.fight(player);
            }
    }
    //method to count nearby monsters by checking adjacent room method from player class
    public static int countNearbyMonsters(Player player, ArrayList<Player> monsters) {
        int countNearbyMonsters = 0;
        for (Player monster: monsters) {
            if (player.inAdjacentRoom(monster) == true) {
                countNearbyMonsters ++;
            }
        }
        return countNearbyMonsters;
    }
    //method to move the player one position at a time, doesn't allow for player to move outside of dungeon unless character is escaping
    public static void moveOne(Player player, int dungeonSize, String direction) {
        //calling move method from player class to move character
        if (player.move(direction, dungeonSize) == true && direction.equalsIgnoreCase("north")) {
            //calling the setters and getters for the players row, and setting the health -2 each time character moves
            player.setRow(player.getRow() - 1);
            player.setHealth(-2);
        }
        else if (player.move(direction, dungeonSize) == true && direction.equalsIgnoreCase("south")) {
            player.setRow(player.getRow() + 1);
            player.setHealth(-2);
        }
        else if (player.move(direction, dungeonSize) == true && direction.equalsIgnoreCase("east")) {
            player.setColumn(player.getColumn() + 1);
            player.setHealth(-2);
        }
        else if (player.move(direction, dungeonSize) == true && direction.equalsIgnoreCase("west")) {
            player.setColumn(player.getColumn() - 1);
            player.setHealth(-2);
        }
        else {
            //printing if character tries to move outside of dungeon range
            System.out.println("You can't move that way!");
        }
    }
}
