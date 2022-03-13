package me.blackness.black.req;

import me.blackness.black.Requirement;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * a requirement which requires holding a certain item on cursor.
 *
 * @author Draww
 * @see Requirement
 * @see ItemStack
 * @since 4.1.0-alpha
 */
public final class ClickedCursorItemReq implements Requirement {
    private final ItemStack item;

    /**
     * ctor.
     *
     * @param item the itemstack which has to be on player's cursor
     */
    public ClickedCursorItemReq(final ItemStack item) {
        this.item = Objects.requireNonNull(item);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent) {
            return ((InventoryClickEvent) event).getCursor().equals(item);
        } else {
            return false;
        }
    }
}
