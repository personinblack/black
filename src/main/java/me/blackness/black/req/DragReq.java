package me.blackness.black.req;

import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;

/**
 * a requirement which requires a drag.
 *
 * @author personinblack
 * @see Requirement
 * @since 4.0.0-alpha
 */
public final class DragReq implements Requirement {
    @Override
    public boolean control(final InventoryInteractEvent event) {
        return event instanceof InventoryDragEvent;
    }
}
