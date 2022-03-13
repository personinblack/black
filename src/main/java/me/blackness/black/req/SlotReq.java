package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;

/**
 * a requirement which requires a specific slot to be pressed.
 * for drag events, this will check all the affected slots.
 *
 * @author personinblack
 * @see Requirement
 * @since 4.0.0-alpha
 */
public final class SlotReq implements Requirement {
    private final int slot;

    /**
     * ctor.
     *
     * @param slot the slot which required to be pressed
     */
    public SlotReq(final int slot) {
        this.slot = Objects.requireNonNull(slot);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent) {
            return ((InventoryClickEvent) event).getSlot() == slot;
        } else {
            return ((InventoryDragEvent) event).getInventorySlots().contains(slot);
        }
    }
}
