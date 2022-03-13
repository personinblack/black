package me.blackness.black;

import org.bukkit.event.inventory.InventoryInteractEvent;

/**
 * target is the type of all the event handlers.
 *
 * @author personinblack
 * @since 4.0.0-alpha
 */
public interface Target {
    /**
     * let the target handle this event.
     *
     * @param event the event to handle
     */
    void handle(InventoryInteractEvent event);
}
