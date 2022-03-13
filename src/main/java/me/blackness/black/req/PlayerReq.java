package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;

/**
 * a requirement which requires a player.
 *
 * @author personinblack
 * @see Requirement
 * @see Player
 * @since 4.0.0-alpha
 */
public final class PlayerReq implements Requirement {
    private final Player player;

    /**
     * ctor.
     *
     * @param player the required player
     */
    public PlayerReq(final Player player) {
        this.player = Objects.requireNonNull(player);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        return event.getWhoClicked().getUniqueId().equals(player.getUniqueId());
    }
}
