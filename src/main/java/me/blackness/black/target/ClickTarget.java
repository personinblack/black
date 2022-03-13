package me.blackness.black.target;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;
import me.blackness.black.Target;
import me.blackness.black.event.ElementClickEvent;

/**
 * the most basic click target.
 *
 * @author personinblack
 * @see Target
 * @since 4.0.0-alpha
 */
public final class ClickTarget implements Target {
    private final Consumer<ElementClickEvent> handler;
    private final Requirement[] reqs;

    /**
     * ctor.
     *
     * @param handler handler of this target
     * @param reqs requirements of this target
     * @see Consumer
     * @see Requirement
     */
    public ClickTarget(final Consumer<ElementClickEvent> handler, final Requirement... reqs) {
        this.handler = Objects.requireNonNull(handler);
        this.reqs = Objects.requireNonNull(reqs.clone());
    }

    @Override
    public void handle(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent &&
                Arrays.stream(reqs).allMatch(req -> req.control(event))) {

            handler.accept(new ElementClickEvent((InventoryClickEvent) event));
        }
    }
}
