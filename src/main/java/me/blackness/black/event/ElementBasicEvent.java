package me.blackness.black.event;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.ElementEvent;

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
 * an event which contains the implementation of the methods of {@link ElementEvent}.
 */
public final class ElementBasicEvent implements ElementEvent {
    private final InventoryInteractEvent baseEvent;

    /**
     * ctor.
     *
     * @param baseEvent the base event
     */
    public ElementBasicEvent(final InventoryInteractEvent baseEvent) {
        this.baseEvent = Objects.requireNonNull(baseEvent);
    }

    @Override
    public Player player() {
        return (Player) baseEvent.getWhoClicked();
    }

    @Override
    public void cancel() {
        baseEvent.setCancelled(true);
    }

    @Override
    public void closeView() {
        Bukkit.getScheduler().runTask(
            baseEvent.getHandlers().getRegisteredListeners()[0].getPlugin(),
            () -> {
                baseEvent.getWhoClicked().closeInventory();
            }
        );
    }
}
