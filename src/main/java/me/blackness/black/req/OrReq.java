package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;

/**
 * a requirement which takes multiple other requirements and matches either one of them.
 *
 * @author personinblack
 * @see Requirement
 * @since 4.0.0-alpha
 */
public final class OrReq implements Requirement {
    private final Requirement[] reqs;

    /**
     * ctor.
     *
     * @param reqs requirements to do the *or* check
     */
    public OrReq(final Requirement... reqs) {
        this.reqs = Objects.requireNonNull(reqs.clone());
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        for (final Requirement req : reqs) {
            if (req.control(event)) {
                return true;
            }
        }
        return false;
    }
}
