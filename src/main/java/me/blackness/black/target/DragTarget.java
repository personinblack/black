package me.blackness.black.target;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;
import me.blackness.black.Target;
import me.blackness.black.event.ElementDragEvent;

/**
 * the most basic drag target.
 *
 * @author personinblack
 * @see Target
 * @since 4.0.0-alpha
 */
public final class DragTarget implements Target {
    private final Consumer<ElementDragEvent> handler;
    private final Requirement[] reqs;

    /**
     * ctor.
     *
     * @param handler handler of this target
     * @param reqs requirements of this target
     * @see Consumer
     * @see Requirement
     */
    public DragTarget(final Consumer<ElementDragEvent> handler, final Requirement... reqs) {
        this.handler = Objects.requireNonNull(handler);
        this.reqs = Objects.requireNonNull(reqs.clone());
    }

    @Override
    public void handle(final InventoryInteractEvent event) {
        if (event instanceof InventoryDragEvent &&
                Arrays.stream(reqs).allMatch(req -> req.control(event))) {

            handler.accept(new ElementDragEvent((InventoryDragEvent) event));
        }
    }
}
