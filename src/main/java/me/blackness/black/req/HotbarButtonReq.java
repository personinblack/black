package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;

/**
 * a requirement which requires a certain keyboard button to be pressed.
 * returns {@code false} for a non click event.
 *
 * @author personinblack
 * @see Requirement
 * @since 4.0.0-alpha
 */
public final class HotbarButtonReq implements Requirement {
    private final int button;

    /**
     * ctor.
     *
     * @param button the button which expected to be pressed
     */
    public HotbarButtonReq(final int button) {
        this.button = Objects.requireNonNull(button);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent) {
            return ((InventoryClickEvent) event).getHotbarButton() == button;
        } else {
            return false;
        }
    }
}
