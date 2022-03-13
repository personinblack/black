package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;

/**
 * a requirement which requires a certain drag type. returns {@code false} for a non drag event.
 *
 * @author personinblack
 * @see Requirement
 * @see DragType
 * @since 4.0.0-alpha
 */
public final class DragTypeReq implements Requirement {
    private final DragType dragType;

    /**
     * ctor.
     *
     * @param dragType the required drag type
     */
    public DragTypeReq(final DragType dragType) {
        this.dragType = Objects.requireNonNull(dragType);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryDragEvent) {
            return ((InventoryDragEvent) event).getType() == dragType;
        } else {
            return false;
        }
    }
}
