package me.blackness.black.event;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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
 * an event which represents an inventory click.
 *
 * @see ElementEvent
 */
public final class ElementClickEvent implements ElementEvent {
    private final InventoryClickEvent baseEvent;
    private final ElementBasicEvent baseElementEvent;

    /**
     * ctor.
     *
     * @param baseEvent the base event
     */
    public ElementClickEvent(final InventoryClickEvent baseEvent) {
        this.baseEvent = Objects.requireNonNull(baseEvent);
        baseElementEvent = new ElementBasicEvent(baseEvent);
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

    @Override
    public Player player() {
        return baseElementEvent.player();
    }

    @Override
    public void cancel() {
        baseElementEvent.cancel();
    }

    @Override
    public void closeView() {
        baseElementEvent.closeView();
    }
}
