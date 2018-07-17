package me.blackness.black;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.blackness.observer.Target;

/*
       .                                                    .
    .$"                                    $o.      $o.  _o"
   .o$$o.    .o$o.    .o$o.    .o$o.   .o$$$$$  .o$$$$$ $$P `4$$$$P'   .o$o.
  .$$| $$$  $$' $$$  $$' $$$  $$' $$$ $$$| $$$ $$$| $$$ ($o $$$: $$$  $$' $$$
  """  """ """  """ """  """ """  """ """  """ """  """  "  """  """ """  """
.oOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo.
  ooo_ ooo ooo. ... ooo. ... ooo.  .. `4ooo.  .`4ooo.   ooo.ooo. ooo ooo.  ..
  $$$"$$$$ $$$| ... $$$| ... $$$$$$ ..    "$$o     "$$o $$$|$$$| $$$ $$$|   .
  $$$| $$$ $$$|     $$$|     $$$|     $$$: $$$ $$$: $$$ $$$|$$$| $$$ $$$|
  $$$| $$$ $$$| $o. $$$| $o. $$$| $o. $$$| $$$ $$$| $$$ $$$|$$$| $$$ $$$| $.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$|$$$| $$$ $$$| $o.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$|$$$| $$$ $$$| $$$
  $$$| $$$  $$. $$$  $$. $$$  $$. $$$ $$$| $$$ $$$| $$$ $$$|$$$| $$$  $$. $$$
  $$$: $P'  `4$$$Ü'__`4$$$Ü'  `4$$$Ü' $$$$$P'  $$$$$P'  $$$|$$$: $P' __`4$$$Ü'
 _ _______/∖______/  ∖______/∖______________/|________ "$P' _______/  ∖_____ _
                                                        i"  personinblack
                                                        |
 */

/**
 * page is the type of all the inventory pages which made from panes and
 * can be displayed to players.
 *
 * @see Pane
 * @see Player
 */
public interface Page extends InventoryHolder, Target<Object> {
    /**
     * shows this page.
     *
     * @param player player to show the page to
     * @see Player
     */
    void showTo(Player player);

    /**
     * this method is being triggered by the inventory close event in order to make this page
     * do not update the player who closed this page anymore.
     *
     * @param event event to handle
     * @see InventoryCloseEvent
     */
    void handleClose(InventoryCloseEvent event);

    /**
     * this method is being triggered by the inventory click event and passes the event to
     * the panes of it.
     *
     * @param event event to pass
     * @see InventoryInteractEvent
     */
    void accept(InventoryInteractEvent event);

    /**
     * rearranging the panes from top to bottom. this will shift the panes towards bottom
     * when they are about to overlap each other.
     *
     * @param arrangements map of arrangements. the key is the pane to arrange and the value is
     *        the new position
     * @see Map
     */
    void rearrange(Map<Integer, Integer> arrangements);

    /**
     * {@inheritDoc}
     *
     * @deprecated because this is against oop and we don't have a single universal inventory.
     * @return an empty (null) inventory
     * @see Inventory
     */
    @Override @Deprecated
    Inventory getInventory();
}
