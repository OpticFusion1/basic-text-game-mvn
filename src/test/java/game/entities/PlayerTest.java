package game.entities;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;

public class PlayerTest {
    @Test
    public void playerNameTest() {
        Player p = new Player("Kei");
        assertEquals("Kei", p.getName());
    }

    @Test
    public void playerAddItemTest() {
        Player p = new Player("Kei");
        Item i = new Item(0, "knife-0", "knife", new Room());
        p.addItem(i);
        assertTrue(p.hasItem(i.getItemType()));
    }

    @Test
    public void playerGetItemTest() {
        Player p = new Player("Kei");
        Item i = new Item(0, "knife-0", "knife", new Room());
        p.addItem(i);
        assertEquals(i, p.getItem(i.getItemName()));
    }

    @Test
    public void playerGetItemsTest() {
        Player p = new Player("Kei");
        Room r = new Room();
        Item i1 = new Item(0, "knife-0", "knife", r);
        Item i2 = new Item(1, "sword-0", "sword", r);
        Item i3 = new Item(2, "armor-0", "armor", r);
        LinkedList<Item> items = new LinkedList<Item>();
        items.add(i1);
        items.add(i2);
        items.add(i3);
        p.addItem(i1);
        p.addItem(i2);
        p.addItem(i3);
        assertEquals(items, p.getItems());
    }

    @Test
    public void playerRemoveItemsTest() {
        Player p = new Player("Kei");
        Room r = new Room();
        Item i1 = new Item(0, "knife-0", "knife", r);
        Item i2 = new Item(1, "sword-0", "sword", r);
        Item i3 = new Item(2, "armor-0", "armor", r);
        
        p.addItem(i1);
        p.addItem(i2);
        p.addItem(i3);

        p.removeItem(i1.getItemName());
        assertFalse(p.hasItem(i1.getItemType()));
    }
}