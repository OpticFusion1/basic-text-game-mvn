package game.entities;

import java.util.LinkedList;
import java.util.List;

public class Player implements ItemCarrier {
    private String name;
    private List<Item> items;

    public Player() {}

    public Player(String name) {
        this.name = name;
        items = new LinkedList<Item>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the inventory
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItem(String itemName) {
        for(Item item : items)
            if(item.getItemName().equalsIgnoreCase(itemName)) return item;

        return null;
    }

    public boolean hasItem(String itemType) {
        for(Item item : items)
            if(item.getItemType().equalsIgnoreCase(itemType)) return true;
        return false;
    }

    public Item removeItem(String itemName) {
        for(Item item : items)
            if(item.getItemName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        return null;
    }
}
