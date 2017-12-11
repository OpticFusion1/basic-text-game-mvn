package game.entities;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;

public class RoomTest {
    @Test
    public void createRoomTest() {
        Room r = new Room(0, "corridor-0");
        assertEquals(0, r.getRoomID());
        assertEquals("corridor-0", r.getRoomStringID());
        assertEquals("corridor", r.getRoomType());
    }

    @Test
    public void addExitTest() {
        Room r = new Room(0, "corridor-0");
        Room exit = new Room(1, "classroom-0");
        r.addExit("north", exit);
        assertEquals(exit, r.getExits().get("north"));
    }

    @Test
    public void addItemTest() {
        Room r = new Room(0, "corridor-0");
        Item i = new Item(0, "knife-0", "knife", r);
        r.addItem(i);
        assertTrue(r.hasItem(i.getItemName()));
    }

    @Test
    public void removeItemTest() {
        Room r = new Room(0, "corridor-0");
        Item i1 = new Item(0, "knife-0", "knife", r);
        Item i2 = new Item(1, "sword-0", "sword", r);
        Item i3 = new Item(1, "armor-0", "armor", r);
        r.addItem(i1);
        r.addItem(i2);
        r.addItem(i3);
        r.removeItem(i1.getItemName());
        assertFalse(r.hasItem(i1.getItemName()));
    }

    @Test
    public void getItemTest() {
        Room r = new Room(0, "corridor-0");
        Item i1 = new Item(0, "knife-0", "knife", r);
        Item i2 = new Item(1, "sword-0", "sword", r);
        Item i3 = new Item(1, "armor-0", "armor", r);
        r.addItem(i1);
        r.addItem(i2);
        r.addItem(i3);
        assertEquals(i1, r.getItem(i1.getItemName()));
    }

    @Test
    public void getItemsTest() {
        Room r = new Room(0, "corridor-0");
        Item i1 = new Item(0, "knife-0", "knife", r);
        Item i2 = new Item(1, "sword-0", "sword", r);
        Item i3 = new Item(1, "armor-0", "armor", r);
        LinkedList<Item> items = new LinkedList<Item>();

        items.add(i1);
        items.add(i2);
        items.add(i3);

        r.addItem(i1);
        r.addItem(i2);
        r.addItem(i3);
        assertEquals(items, r.getItems());
    }
}