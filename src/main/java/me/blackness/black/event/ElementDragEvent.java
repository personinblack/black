package me.blackness.black.event;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

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
 * an event which represents an inventory drag.
 */
public final class ElementDragEvent {
    private final InventoryDragEvent baseEvent;

    /**
     * ctor.
     *
     * @param baseEvent the base event
     */
    public ElementDragEvent(final InventoryDragEvent baseEvent) {
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
     * the cursor after the drag is done.
     *
     * @return cursor after the drag
     * @see ItemStack
     */
    public ItemStack cursor() {
        return baseEvent.getCursor();
    }

    /**
     * the cursor before the drag is done.
     *
     * @return cursor before the drag
     * @see ItemStack
     */
    public ItemStack oldCursor() {
        return baseEvent.getOldCursor();
    }

    /**
     * items being added to the pane after this event.
     *
     * @return a map of slots and itemstack changes associated with them
     * @see Map
     * @see ItemStack
     */
    public Map<Integer, ItemStack> items() {
        return baseEvent.getNewItems();
    }
}
