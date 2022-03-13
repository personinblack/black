package me.blackness.black.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.blackness.black.Page;
import me.blackness.black.Pane;

/**
 * a page that uses an inventory of {@link InventoryType#CHEST}.
 *
 * @author personinblack
 * @since 1.0.0
 */
public final class ChestPage implements Page {
    private final String title;
    private final int size;
    private final List<Pane> panes;
    private final List<Player> viewers;
    private Page holder;

    /**
     * ctor.
     *
     * @param title title of the page
     * @param size size of the page which has to be multiple of 9
     * @param panes panes of this page to display
     */
    public ChestPage(final String title, final int size, final Pane... panes) {
        this.title = Objects.requireNonNull(title);
        this.size = size < 9 ? 9 : size;
        this.panes = new ArrayList<>(Arrays.asList(Objects.requireNonNull(panes)));
        this.viewers = new ArrayList<>();
        this.holder = this;

        Arrays.stream(panes).forEach(pane -> pane.subscribe(this));
    }

    @Override
    public void add(final Pane pane, final int position) {
        panes.add(
            position > panes.size()
                ? panes.size()
                : Objects.requireNonNull(position),
            Objects.requireNonNull(pane)
        );
        update(new Object());
    }

    @Override
    public void remove(final int position) {
        panes.remove(position);
        update(new Object());
    }

    @Override
    public void rearrange(final int paneIndex, final int position) {
        final Pane pane = panes.get(paneIndex);
        panes.remove(paneIndex);
        panes.add(
            position > panes.size() ? panes.size() : position,
            pane
        );
        update(new Object());
    }

    @Override
    public void defineHolder(final Page holder) {
        this.holder = Objects.requireNonNull(holder);
    }

    @Override
    public void showTo(final Player player) {
        final Inventory inventory = Bukkit.createInventory(holder, size, title);
        for (final Pane pane : panes) {
            pane.displayOn(inventory);
        }
        player.openInventory(inventory);
        if (!viewers.contains(player)) {
            viewers.add(player);
        }
    }

    @Override
    public void handleClose(final InventoryCloseEvent event) {
        viewers.remove((Player) event.getPlayer());
    }

    @Override
    public void update(final Object argument) {
        Bukkit.getScheduler().runTask(
            Bukkit.getPluginManager().getPlugins()[0], () -> {
                viewers.forEach(viewer -> {
                    final Inventory inventory = viewer.getOpenInventory().getTopInventory();
                    inventory.clear();
                    panes.forEach(pane -> {
                        pane.displayOn(inventory);
                    });
                });
            }
        );
    }

    /**
     * {@inheritDoc}
     * @deprecated because this is against oop and we don't have a single universal inventory.
     */
    @Override @Deprecated
    public Inventory getInventory() {
        return Bukkit.createInventory(null, 9);
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        new ArrayList<>(panes).forEach(pane -> pane.accept(event));
    }
}
