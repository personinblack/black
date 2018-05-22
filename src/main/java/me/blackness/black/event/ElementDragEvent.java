package me.blackness.black.event;

import java.util.Map;
import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;
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
 * an event which represents an inventory drag.
 *
 * @see ElementEvent
 */
public final class ElementDragEvent implements ElementEvent {
    private final InventoryDragEvent baseEvent;
    private final ElementBasicEvent baseElementEvent;

    /**
     * ctor.
     *
     * @param baseEvent the base event
     */
    public ElementDragEvent(final InventoryDragEvent baseEvent) {
        this.baseEvent = Objects.requireNonNull(baseEvent);
        baseElementEvent = new ElementBasicEvent(baseEvent);
    }

    /**
     * the cursor before the drag is done.
     *
     * @return cursor before the drag
     * @see ItemStack
     * @see Player#getItemOnCursor() for the cursor *after* the drag is done
     */
    public ItemStack oldCursor() {
        return baseEvent.getOldCursor();
    }

    /**
     * items being changed in this event.
     *
     * @return a map of slots and itemstack changes associated with them
     * @see Map
     * @see ItemStack
     */
    public Map<Integer, ItemStack> items() {
        return baseEvent.getNewItems();
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
