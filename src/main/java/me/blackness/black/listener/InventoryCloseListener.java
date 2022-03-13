package me.blackness.black.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.blackness.black.Page;

/**
 * a listener that listen for players closing inventories.
 *
 * @author personinblack
 * @since 2.0.0
 */
public final class InventoryCloseListener implements Listener {
    /**
     * the listener that listens for inventory closes and informs the pages associated with them.
     *
     * @param event the event that happened
     */
    @EventHandler
    public void closeListener(final InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof Page) {
            ((Page) event.getInventory().getHolder()).handleClose(event);
        }
    }
}
