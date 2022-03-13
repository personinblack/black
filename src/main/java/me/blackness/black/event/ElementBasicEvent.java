package me.blackness.black.event;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.ElementEvent;

/**
 * an event which contains the implementation of the methods of {@link ElementEvent}.
 *
 * @author personinblack
 * @since 4.0.0-alpha
 */
public final class ElementBasicEvent implements ElementEvent {
    private final InventoryInteractEvent baseEvent;

    /**
     * ctor.
     *
     * @param baseEvent the base event
     */
    public ElementBasicEvent(final InventoryInteractEvent baseEvent) {
        this.baseEvent = Objects.requireNonNull(baseEvent);
    }

    @Override
    public Player player() {
        return (Player) baseEvent.getWhoClicked();
    }

    @Override
    public void cancel() {
        baseEvent.setCancelled(true);
    }

    @Override
    public void closeView() {
        Bukkit.getScheduler().runTask(
            baseEvent.getHandlers().getRegisteredListeners()[0].getPlugin(),
            () -> {
                baseEvent.getWhoClicked().closeInventory();
            }
        );
    }
}
