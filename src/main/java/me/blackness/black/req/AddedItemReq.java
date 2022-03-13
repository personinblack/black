package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.Requirement;

/**
 * a requirement which requires an item to be added by dragging.
 *
 * @author personinblack
 * @see Requirement
 * @see ItemStack
 * @since 4.0.0-alpha
 */
public final class AddedItemReq implements Requirement {
    private final ItemStack item;

    /**
     * ctor.
     *
     * @param item the item that needs to be added
     */
    public AddedItemReq(final ItemStack item) {
        this.item = Objects.requireNonNull(item);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryDragEvent) {
            return ((InventoryDragEvent) event).getNewItems().values()
                .stream().anyMatch(this.item::equals);
        }
        return false;
    }
}
