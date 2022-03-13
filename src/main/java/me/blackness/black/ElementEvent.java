package me.blackness.black;

import org.bukkit.entity.Player;

/**
 * represents an element event.
 *
 * @author personinblack
 * @see Element
 * @since 4.0.0-alpha
 */
public interface ElementEvent {
    /**
     * the player involved in this event.
     *
     * @return the player who triggered this event
     * @see Player
     */
    Player player();

    /**
     * cancels the action the player has done.
     */
    void cancel();

    /**
     * closes all the open inventories the player has at the moment.
     * (i don't know why i wrote this like a player can have multiple inventories open
     * at the same time but whatever...)
     */
    void closeView();
}
