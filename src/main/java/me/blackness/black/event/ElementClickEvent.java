package me.blackness.black.event;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.Element;

/*
       .                                                    .
    .$"                                    $o.      $o.  _o"
   .o$$o.    .o$o.    .o$o.    .o$o.   .o$$$$$  .o$$$$$ $$P  `4$$$$P'   .o$o.
  .$$| $$$  $$' $$$  $$' $$$  $$' $$$ $$$| $$$ $$$| $$$ ($o  $$$: $$$  $$' $$$
  """  """ """  """ """  """ """  """ """  """ """  """  "   """  """ """  """
.oOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo.
  ooo_ ooo ooo. ... ooo. ... ooo.  .. `4ooo.  .`4ooo.   ooo. ooo. ooo ooo.  ..
  $$$"$$$$ $$$| ... $$$| ... $$$$$$ ..    "$$o     "$$o $$$| $$$| $$$ $$$|   .
  $$$| $$$ $$$|     $$$|     $$$|     $$$: $$$ $$$: $$$ $$$| $$$| $$$ $$$|
  $$$| $$$ $$$| $o. $$$| $o. $$$| $o. $$$| $$$ $$$| $$$ $$$| $$$| $$$ $$$| $.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$| $$$ $$$| $o.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$| $$$ $$$| $$$
  $$$| $$$  $$. $$$  $$. $$$  $$. $$$ $$$| $$$ $$$| $$$ $$$| $$$| $$$  $$. $$$
  $$$: $P'  `4$$$Ü'__`4$$$Ü'  `4$$$Ü' $$$$$P'  $$$$$P'  $$$| $$$: $P' __`4$$$Ü'
 _ _______/∖______/  ∖______/∖______________/|________ "$P' _______/  ∖_____ _
                                                        i"  personinblack
                                                        |
 */

/**
 * an event which represents an inventory click.
 *
 * @see Event
 * @see Element
 */
public final class ElementClickEvent {
    private final InventoryClickEvent baseEvent;

    /**
     * ctor.
     *
     * @param baseEvent the base event
     */
    public ElementClickEvent(final InventoryClickEvent baseEvent) {
        Objects.requireNonNull(baseEvent);
        this.baseEvent = baseEvent;
    }

    /**
     * the player involved in this event.
     *
     * @return the player who triggered this event
     * @see Player
     */
    public Player player() {
        return (Player) baseEvent.getWhoClicked();
    }

    /**
     * the itemstack the player has clicked on.
     *
     * @return the itemstack that the player has clicked on
     * @see ItemStack
     * @see Player
     */
    public ItemStack currentItem() {
        return baseEvent.getCurrentItem().clone();
    }

    /**
     * cancels the action the player has done.
     */
    public void cancel() {
        baseEvent.setCancelled(true);
    }

    /**
     * closes all the open inventories the player has at the moment.
     * (i don't know why i wrote this like a player can have multiple inventories open
     * at the same time but whatever...)
     */
    public void closeView() {
        Bukkit.getScheduler().runTask(
            baseEvent.getHandlers().getRegisteredListeners()[0].getPlugin(),
            () -> {
                baseEvent.getWhoClicked().closeInventory();
            }
        );
    }
}
