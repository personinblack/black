package me.blackness.black.element;

import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.blackness.black.Element;

/**
 * an element which takes some other elements and makes an animation out of them.
 *
 * @author personinblack
 * @see Element
 * @see BasicElement
 * @since 1.0.0
 */
public final class LiveElement implements Element {
    private final Plugin plugin;
    private final int period;
    private final Element[] frames;

    /**
     * ctor.
     *
     * @param plugin plugin for being used on registering bukkit tasks
     * @param period delay between every frame
     * @param frames frames to display in order
     */
    public LiveElement(final Plugin plugin, final int period, final Element... frames) {
        this.plugin = Objects.requireNonNull(plugin);
        this.period = Objects.requireNonNull(period);
        this.frames = Objects.requireNonNull(frames.clone());
    }

    private Element nullElement() {
        return new BasicElement(new ItemStack(Material.PAPER), "nullElement");
    }

    private Element findFrame(final ItemStack icon) {
        for (final Element frame : frames) {
            if (frame.is(icon)) {
                return frame;
            }
        }

        return nullElement();
    }

    private boolean contains(final Element element) {
        for (final Element frame : frames) {
            if (frame.is(element)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        for (final Element frame : frames) {
            frame.accept(event);
        }
    }

    @Override
    public void displayOn(final Inventory inventory, final int locX, final int locY) {
        frames[0].displayOn(inventory, locX, locY);
        new BukkitRunnable() {
            private int iterator;

            @Override
            public void run() {
                if (inventory.getViewers().isEmpty()) {
                    this.cancel();
                } else {
                    nextFrame().displayOn(inventory, locX, locY);
                }
            }

            private Element nextFrame() {
                iterator = iterator + 1 < frames.length
                    ? iterator + 1
                    : 0;

                return frames[iterator];
            }
        }.runTaskTimer(plugin, 1, period);
    }

    @Override
    public boolean is(final ItemStack icon) {
        return findFrame(icon).is(icon);
    }

    @Override
    public boolean is(final Element element) {
        return contains(element);
    }
}
