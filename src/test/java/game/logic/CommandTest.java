package game.logic;

import org.junit.Test;
import static org.junit.Assert.*;

public class CommandTest {
    @Test
    public void goEastCommandTest() {
        Command c = new Command("go east");
        assertEquals(CommandType.GOTO, c.getCommandType());
    }

    @Test
    public void goWestCommandTest() {
        Command c = new Command("go west");
        assertEquals(CommandType.GOTO, c.getCommandType());
    }

    @Test
    public void pickupKnifeCommandTest() {
        Command c = new Command("pickup knife");
        assertEquals(CommandType.PICKUP, c.getCommandType());
    }

    @Test
    public void putdownKnifeCommandTest() {
        Command c = new Command("putdown knife");
        assertEquals(CommandType.PUTDOWN, c.getCommandType());
    }

    @Test
    public void inventoryCommandTest() {
        Command c = new Command("inventory");
        assertEquals(CommandType.INVENTORY, c.getCommandType());
    }

    @Test
    public void helpCommandTest() {
        Command c = new Command("help");
        assertEquals(CommandType.HELP, c.getCommandType());
    }

    @Test
    public void quitCommandTest() {
        Command c = new Command("quit");
        assertEquals(CommandType.QUIT, c.getCommandType());
    }

    @Test
    public void undefinedCommandTest() {
        Command c = new Command("some command");
        assertEquals(CommandType.UNDEFINED, c.getCommandType());
    }
}