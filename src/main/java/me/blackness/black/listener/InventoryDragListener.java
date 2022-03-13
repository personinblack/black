package me.blackness.black.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.PlayerInventory;

import me.blackness.black.Page;

/**
 * a listener that listen for drags happening on inventories.
 *
 * @author personinblack
 * @since 4.0.0-alpha
 */
public final class InventoryDragListener implements Listener {
    /**
     * the listener that listens for inventory drags and informs the pages associated with them.
     *
     * @param event the event that happened
     * @see InventoryDragEvent
     */
    @EventHandler
    public void listener(final InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof Page &&
                !(event.getInventory() instanceof PlayerInventory)) {

            ((Page) event.getInventory().getHolder()).accept(event);
        }
    }
}
