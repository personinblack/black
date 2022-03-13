package me.blackness.black.req;

import me.blackness.black.Element;
import me.blackness.black.Requirement;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import java.util.Objects;

/**
 * a requirement which requires holding a certain element on cursor.
 *
 * @author Draww
 * @see Requirement
 * @see Element
 * @since 4.1.0-alpha
 */
public final class ClickedCursorElementReq implements Requirement {
    private final Element element;

    /**
     * ctor.
     *
     * @param element the element which has to be on player's cursor
     */
    public ClickedCursorElementReq(final Element element) {
        this.element = Objects.requireNonNull(element);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent) {
            return element.is(((InventoryClickEvent) event).getCursor());
        } else {
            return false;
        }
    }
}
