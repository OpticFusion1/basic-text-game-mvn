package game.entities;

import java.util.List;

public interface ItemCarrier {
    public List<Item> getItems();
    public Item getItem(String itemName);
    public boolean hasItem(String itemName);
    public void addItem(Item item);
    public Item removeItem(String itemName);
}