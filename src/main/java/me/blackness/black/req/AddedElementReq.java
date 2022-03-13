package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Element;
import me.blackness.black.Requirement;

/**
 * a requirement which requires an element to be added by dragging.
 *
 * @author personinblack
 * @see Requirement
 * @see Element
 * @since 4.0.0-alpha
 */
public final class AddedElementReq implements Requirement {
    private final Element element;

    /**
     * ctor.
     *
     * @param element the element that needs to be added
     */
    public AddedElementReq(final Element element) {
        this.element = Objects.requireNonNull(element);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryDragEvent) {
            return ((InventoryDragEvent) event).getNewItems().values()
                .stream().anyMatch(element::is);
        }
        return false;
    }
}
