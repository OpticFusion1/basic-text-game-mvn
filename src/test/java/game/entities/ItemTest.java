package game.entities;

import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {
    @Test
    public void itemIDTest() {
        Item i = new Item(0, "knife-0", "knife", new Room());
        assertEquals(0, i.getItemID());
    }

    @Test
    public void itemNameTest() {
        Item i = new Item(0, "knife-0", "knife", new Room());
        assertEquals("knife-0", i.getItemName());
    }

    @Test
    public void itemTypeTest() {
        Item i = new Item(0, "knife-0", "knife", new Room());
        assertEquals("knife", i.getItemType());
    }

    @Test
    public void itemLocationTest() {
        Room r = new Room();
        Item i = new Item(0, "knife-0", "knife", r);
        assertEquals(r, i.getLocation());
    }

    @Test
    public void itemSetLocationTest() {
        Item i = new Item(0, "knife-0", "knife", new Room());
        Room r = new Room();
        i.setLocation(r);
        assertEquals(r, i.getLocation());
    }
}