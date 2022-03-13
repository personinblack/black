package me.blackness.black;

import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * element is the type of all the buttons inside a pane which does stuff when you click on it.
 *
 * @author personinblack
 * @see Pane
 * @since 1.0.0
 */
public interface Element {
    /**
     * display the element on an inventory. this method is being used by the pane
     * which contains this element.
     *
     * @param inventory inventory to display the element on
     * @param locX x location of the slot
     * @param locY y location of the slot
     * @see Pane
     * @see Inventory
     */
    void displayOn(Inventory inventory, int locX, int locY);

    /**
     * accept the element click event and pass it to the framework user's handler.
     *
     * @param event event to pass
     * @see InventoryInteractEvent
     */
    void accept(InventoryInteractEvent event);

    /**
     * compares the specified element with this one.
     *
     * @param element the element to compare
     * @return {@code true} if they are the same or {@code false} if they are not
     */
    boolean is(Element element);

    /**
     * compares the specified itemstack with this element's icon.
     *
     * @param icon the itemstack to compare
     * @return {@code true} if they are the same or {@code false} if they are not
     * @see ItemStack
     */
    boolean is(ItemStack icon);
}
