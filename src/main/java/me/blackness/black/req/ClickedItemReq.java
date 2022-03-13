package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.Requirement;

/**
 * a requirement which requires a certain item to be clicked on.
 *
 * @author personinblack
 * @see Requirement
 * @see ItemStack
 * @since 4.0.0-alpha
 */
public final class ClickedItemReq implements Requirement {
    private final ItemStack item;

    /**
     * ctor.
     *
     * @param item itemstack to be clicked on
     */
    public ClickedItemReq(final ItemStack item) {
        this.item = Objects.requireNonNull(item);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent) {
            return ((InventoryClickEvent) event).getCurrentItem().equals(item);
        } else {
            return false;
        }
    }
}
