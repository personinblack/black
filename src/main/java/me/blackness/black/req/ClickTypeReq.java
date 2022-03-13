package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;

/**
 * a requirement which requires a certain click type. returns {@code false} for a non click event.
 *
 * @author personinblack
 * @see Requirement
 * @see ClickType
 * @since 4.0.0-alpha
 */
public final class ClickTypeReq implements Requirement {
    private final ClickType clickType;

    /**
     * ctor.
     *
     * @param clickType required click type
     */
    public ClickTypeReq(final ClickType clickType) {
        this.clickType = Objects.requireNonNull(clickType);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent) {
            return ((InventoryClickEvent) event).getClick() == clickType;
        } else {
            return false;
        }
    }
}
