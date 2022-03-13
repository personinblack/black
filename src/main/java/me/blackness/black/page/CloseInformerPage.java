package me.blackness.black.page;

import java.util.Objects;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

import me.blackness.black.Page;
import me.blackness.black.Pane;

/**
 * a page decorator which calls a consumer when a player closes the page.
 *
 * @author personinblack
 * @see Page
 * @see InventoryCloseEvent
 * @since 4.3.0
 */
public final class CloseInformerPage implements Page {
    private final Page basePage;
    private final Consumer<Player> consumer;

    /**
     * ctor.
     *
     * @param basePage the page which will have its close events listened
     * @param consumer the consumer to call when a close event gets handled
     */
    public CloseInformerPage(final Page basePage, final Consumer<Player> consumer) {
        this.basePage = Objects.requireNonNull(basePage);
        this.consumer = Objects.requireNonNull(consumer);
        defineHolder(this);
    }

    @Override
    public void add(final Pane pane, final int position) {
        this.basePage.add(pane, position);
    }

    @Override
    public void remove(final int position) {
        this.basePage.remove(position);
    }

    @Override
    public void rearrange(final int paneIndex, final int position) {
        this.basePage.rearrange(paneIndex, position);
    }

    @Override
    public void defineHolder(final Page holder) {
        basePage.defineHolder(holder);
    }

    @Override
    public void showTo(final Player player) {
        this.basePage.showTo(player);
    }

    @Override
    public void handleClose(final InventoryCloseEvent event) {
        this.basePage.handleClose(event);
        consumer.accept((Player) event.getPlayer());
    }

    @Override
    public void update(final Object argument) {
        basePage.update(argument);
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
        basePage.accept(event);
    }
}
