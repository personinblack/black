package me.blackness.black.event;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.ElementEvent;

/**
 * an event which represents an inventory click.
 *
 * @author personinblack
 * @see ElementEvent
 * @since 4.0.0-alpha
 */
public final class ElementClickEvent implements ElementEvent {
    private final InventoryClickEvent baseEvent;
    private final ElementBasicEvent baseElementEvent;

    /**
     * ctor.
     *
     * @param baseEvent the base event
     */
    public ElementClickEvent(final InventoryClickEvent baseEvent) {
        this.baseEvent = Objects.requireNonNull(baseEvent);
        this.baseElementEvent = new ElementBasicEvent(baseEvent);
    }

    /**
     * the itemstack the player has clicked on.
     *
     * @return the itemstack that the player has clicked on
     * @see ItemStack
     * @see Player
     */
    public ItemStack currentItem() {
        return baseEvent.getCurrentItem().clone();
    }

    /**
     * x location of the clicked slot.
     *
     * @return x
     */
    public int clickedX() {
        return baseEvent.getSlot() % 9;
    }

    /**
     * y location of the clicked slot.
     *
     * @return y
     */
    public int clickedY() {
        return baseEvent.getSlot() / 9;
    }

    @Override
    public Player player() {
        return baseElementEvent.player();
    }

    @Override
    public void cancel() {
        baseElementEvent.cancel();
    }

    @Override
    public void closeView() {
        baseElementEvent.closeView();
    }
}
