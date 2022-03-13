package me.blackness.black.pane;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.Element;
import me.blackness.black.Pane;
import me.blackness.observer.Target;

/**
 * thread-safe decorator for any pane.
 *
 * @author personinblack
 * @see Pane
 * @since 3.1.0
 */
public final class TSafePane implements Pane {
    private final Pane basePane;

    /**
     * ctor.
     *
     * @param basePane the pane to make thread-safe
     */
    public TSafePane(final Pane basePane) {
        this.basePane = Objects.requireNonNull(basePane);
    }

    @Override
    public void fill(final Element element) {
        synchronized (basePane) {
            basePane.fill(element);
        }
    }

    @Override
    public void fill(final Element... elements) {
        synchronized (basePane) {
            basePane.fill(elements);
        }
    }

    @Override
    public void clear() {
        synchronized (basePane) {
            basePane.clear();
        }
    }

    @Override
    public boolean add(final Element element) {
        synchronized (basePane) {
            return basePane.add(element);
        }
    }

    @Override
    public Element[] add(final Element... elements) {
        synchronized (basePane) {
            return basePane.add(elements);
        }
    }

    @Override
    public void insert(final Element element, final int locX, final int locY, final boolean shift)
                throws IllegalArgumentException {

        synchronized (basePane) {
            basePane.insert(element, locX, locY, shift);
        }
    }

    @Override
    public void replaceAll(final Element... elements) {
        synchronized (basePane) {
            basePane.replaceAll(elements);
        }
    }

    @Override
    public void remove(final int locX, final int locY) throws IllegalArgumentException {
        synchronized (basePane) {
            basePane.remove(locX, locY);
        }
    }

    @Override
    public void subscribe(final Target<Object> target) {
        synchronized (basePane) {
            basePane.subscribe(target);
        }
    }

    @Override
    public boolean contains(final ItemStack icon) {
        return basePane.contains(icon);
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        basePane.accept(event);
    }

    @Override
    public void displayOn(final Inventory inventory) {
        basePane.displayOn(inventory);
    }
}
