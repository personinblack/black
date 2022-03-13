package me.blackness.black.target;

import java.util.Objects;
import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;
import me.blackness.black.Target;
import me.blackness.black.event.ElementBasicEvent;

/**
 * the most basic click target which can handle all the interact events.
 *
 * @author personinblack
 * @see Target
 * @since 4.0.0-alpha
 */
public final class BasicTarget implements Target {
    private final Consumer<ElementBasicEvent> handler;
    private final Requirement[] reqs;

    /**
     * ctor.
     *
     * @param handler handler of this target
     * @param reqs requirements of this target
     * @see Consumer
     * @see Requirement
     */
    public BasicTarget(final Consumer<ElementBasicEvent> handler, final Requirement... reqs) {
        this.handler = Objects.requireNonNull(handler);
        this.reqs = Objects.requireNonNull(reqs.clone());
    }

    @Override
    public void handle(final InventoryInteractEvent event) {
        for (final Requirement req : reqs) {
            if (!req.control(event)) {
                return;
            }
        }
        handler.accept(new ElementBasicEvent(event));
    }
}
