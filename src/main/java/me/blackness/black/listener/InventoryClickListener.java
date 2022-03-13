package me.blackness.black.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.PlayerInventory;

import me.blackness.black.Page;

/**
 * a listener that listen for clicks happening on inventories.
 *
 * @author personinblack
 * @since 2.0.0
 */
public final class InventoryClickListener implements Listener {
    /**
     * the listener that listens for inventory clicks and informs the pages associated with them.
     *
     * @param event the event that happened
     * @see InventoryClickEvent
     */
    @EventHandler
    public void listener(final InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Page &&
                !(event.getClickedInventory() instanceof PlayerInventory)) {

            ((Page) event.getInventory().getHolder()).accept(event);
        }
    }
}
