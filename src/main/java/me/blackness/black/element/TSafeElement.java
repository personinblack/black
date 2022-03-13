package me.blackness.black.element;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.Element;

/**
 * thread-safe decorator for any element.
 *
 * @author personinblack
 * @see Element
 * @since 3.1.0
 */
public final class TSafeElement implements Element {
    private final Element baseElement;

    /**
     * ctor.
     *
     * @param baseElement the element to make thread-safe
     */
    public TSafeElement(final Element baseElement) {
        this.baseElement = Objects.requireNonNull(baseElement);
    }

    @Override
    public void displayOn(final Inventory inventory, final int locX, final int locY) {
        synchronized (baseElement) {
            baseElement.displayOn(inventory, locX, locY);
        }
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        baseElement.accept(event);
    }

    @Override
    public boolean is(final ItemStack icon) {
        return baseElement.is(icon);
    }

    @Override
    public boolean is(final Element element) {
        if (baseElement instanceof TSafeElement) {
            return this.baseElement.is(((TSafeElement) element).baseElement);
        } else {
            return baseElement.is(element);
        }
    }
}
