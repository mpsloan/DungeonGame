/*
Michael Sloan
1180-02 / 1180L-06
Wallace Neikirk / Luke Holt
November 20, 2021
This is the object class for the DungeonEscape game with various methods allowing for modifications to the player, monsters, and gold.
*/
import java.util.ArrayList;

public class Player {
    //private fields to be called upon through methods
    private String name;
    private int health;
    private int maxDamage;
    private int row;
    private int column;

    //character method assigning variables
    public Player(String name, int health, int maxDamage, int row, int column) {
        this.name = name;
        this.health = health;
        this.maxDamage = maxDamage;
        this.row = row;
        this.column = column;
    }
    //different player method to compensate for gold, as gold doesn't need all the variables that the player does
    public Player(int row, int column) {
        this.row = row;
        this.column = column;
    }
    //method to get row of player
    public int getRow() {
        return this.row;
    }
    //method to get column of player
    public int getColumn() {
        return this.column;
    }
    //method to set row of player
    public void setRow(int r) {
        this.row = r;
    }
    //method to set column of player
    public void setColumn(int c) {
        this.column = c;
    }

    //methdod to check if character is alive
    public boolean isAlive() {
        if (health > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //method to check if character has escaped the dungeon
    public boolean hasEscaped(int dungeonSize) {
        if (this.row == dungeonSize - 1 && this.column == dungeonSize - 1) {
            return true;
        }
        else {
            return false;
        }
    }

    //method to return all of the monsters in the same room as the main character in a list
    public ArrayList<Player> inSameRoom(ArrayList<Player> monsters) {
        ArrayList<Player> returnMonsters = new ArrayList<Player>();
        //for each loop to cycle through locations of all monsters to check if in same room as main character
        for (Player monster: monsters) {
            if (monster.row == this.row && monster.column == this.column) {
                returnMonsters.add(monster);
            }
        }
        return returnMonsters;
    }

    //method to return all of the gold in the same room as the main character in a list
    public ArrayList<Player> goldInSameRoom(ArrayList<Player> goldList) {
        ArrayList<Player> returnGold = new ArrayList<Player>();
        //for each loop to cycle through locations of all the gold to check if in same room as main character
        for (Player gold: goldList) {
            if (gold.row == this.row && gold.column == this.column) {
                returnGold.add(gold);
            }
        }
        return returnGold;
    }

    //method to check if monster is in room next to character
    public boolean inAdjacentRoom(Player other) {
        //checking if monster row and/or column is one less and/or greater than the main character's
        if (other.row == this.row - 1 && other.column == this.column) {
            return true;
        }
        else if (other.row == this.row + 1 && other.column == this.column) {
            return true;
        }
        else if (other.column == this.column - 1 && other.row == this.row) {
            return true;
        }
        else if (other.column == this.column + 1 && other.row == this.row) {
            return true;
        }
        else {
            return false;
        }
    }

    //void method making characters fight each other
    public void fight(Player other) {
        //random hit value between one and the max damage of each character
        int hit = (int) ((Math.random() * maxDamage) + 1);
        System.out.println(this.name+ " hit " +other.name+ " for " +hit);
        //making hit negative so it takes away from health
        other.setHealth(-hit);
    }

    //method to get health of player
    public int getHealth() {
        return this.health;
    }

    //method to set new health with negative amount added to health
    public void setHealth(int amount) {
        this.health += amount;
    }

    //method to see if player is allowed to move on current position
    public boolean move(String direction, int dungeonSize) {
        //making sure the main character is able to move to a new position without escaping the dungeon unfairly
        if (direction.equalsIgnoreCase("north") && this.row > 0) {
            return true;
        }
        else if (direction.equalsIgnoreCase("south") && this.row <= dungeonSize - 1) {
            return true;
        }
        else if (direction.equalsIgnoreCase("west") && this.column > 0) {
            return true;
        }
        else if (direction.equalsIgnoreCase("east") && this.column < dungeonSize - 1) {
            return true;
        }
        //the player is trying to escape dungeon unfairly, move method will return false
        else {
            return false;
        }
    }

    /* method that converts character information to a string to print to console. I put the row first and column second even though that 
    is opposite to a normal coordinate plane because this is how it was listed in the example code on pilot */
    public String toString() {
        return this.name + " at " + this.row + "," + this.column + " with " + this.health + " health";
    }
}