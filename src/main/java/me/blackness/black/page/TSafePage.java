package me.blackness.black.page;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

import me.blackness.black.Page;
import me.blackness.black.Pane;

/**
 * thread-safe decorator for any page.
 *
 * @author personinblack
 * @see Page
 * @since 3.1.0
 */
public final class TSafePage implements Page {
    private final Page basePage;

    /**
     * ctor.
     *
     * @param basePage the page to make thread-safe
     */
    public TSafePage(final Page basePage) {
        this.basePage = Objects.requireNonNull(basePage);
    }

    @Override
    public void add(final Pane pane, final int position) {
        synchronized (basePage) {
            basePage.add(pane, position);
        }
    }

    @Override
    public void remove(final int position) {
        synchronized (basePage) {
            basePage.remove(position);
        }
    }

    @Override
    public void rearrange(final int paneIndex, final int position) {
        synchronized (basePage) {
            basePage.rearrange(paneIndex, position);
        }
    }

    @Override
    public void defineHolder(final Page holder) {
        basePage.defineHolder(holder);
    }

    @Override
    public void showTo(final Player player) {
        synchronized (basePage) {
            basePage.showTo(player);
        }
    }

    @Override
    public void handleClose(final InventoryCloseEvent event) {
        synchronized (basePage) {
            basePage.handleClose(event);
        }
    }

    @Override
    public void update(final Object argument) {
        synchronized (basePage) {
            basePage.update(argument);
        }
    }

    /**
     * {@inheritDoc}
     * @deprecated because this is against oop and we don't have a single universal inventory.
     */
    @Override @Deprecated
    public Inventory getInventory() {
        return basePage.getInventory();
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        synchronized (basePage) {
            basePage.accept(event);
        }
    }
}
