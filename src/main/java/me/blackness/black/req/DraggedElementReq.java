package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Element;
import me.blackness.black.Requirement;

/**
 * a requirement which requires a certain element to be dragged.
 *
 * @author personinblack
 * @see Requirement
 * @see Element
 * @since 4.0.0-alpha
 */
public final class DraggedElementReq implements Requirement {
    private final Element element;

    /**
     * ctor.
     *
     * @param element the element that needs to be dragged
     */
    public DraggedElementReq(final Element element) {
        this.element = Objects.requireNonNull(element);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent) {
            return false;
        } else {
            return element.is(((InventoryDragEvent) event).getCursor());
        }
    }
}
