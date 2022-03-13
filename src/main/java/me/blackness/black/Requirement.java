package me.blackness.black;

import org.bukkit.event.inventory.InventoryInteractEvent;

/**
 * requirement is the type of all the event conditions.
 *
 * @author personinblack
 * @since 4.0.0-alpha
 */
public interface Requirement {
    /**
     * make the requirement control this event to see if it passes and return the result.
     *
     * @param event event to control
     * @return {@code true} if the event passes this requirement or {@code false} otherwise.
     * @see InventoryInteractEvent
     */
    boolean control(InventoryInteractEvent event);
}
