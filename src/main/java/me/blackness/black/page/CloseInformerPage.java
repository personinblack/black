package me.blackness.black.page;

import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

import me.blackness.black.Page;
import me.blackness.black.Pane;

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
 * a page decorator which calls a consumer when a player closes the page.
 *
 * @see Page
 * @see InventoryCloseEvent
 */
public final class CloseInformerPage implements Page {
    private final Page basePage;
    private final Consumer<Player> consumer;

    /**
     * ctor.
     *
     * @param basePage the page which will have its close events listened
     * @param consumer the consumer to call when a close event gets handled
     */
    public CloseInformerPage(final Page basePage, final Consumer<Player> consumer) {
        this.basePage = basePage;
        this.consumer = consumer;
    }

    @Override
    public void add(final Pane pane, final int position) {
        this.basePage.add(pane, position);
    }

    @Override
    public void remove(final int position) {
        this.basePage.remove(position);
    }

    @Override
    public void rearrange(final int paneIndex, final int position) {
        this.basePage.rearrange(paneIndex, position);
    }

    @Override
    public void showTo(final Player player) {
        this.basePage.showTo(player);
    }

    @Override
    public void handleClose(final InventoryCloseEvent event) {
        this.basePage.handleClose(event);
        consumer.accept((Player) event.getPlayer());
    }

    @Override
    public void update(final Object argument) {
        basePage.update(argument);
    }

    /**
     * {@inheritDoc}
     * @deprecated because this is against oop and we don't have a single universal inventory.
     */
    @Override @Deprecated
    public Inventory getInventory() {
        return basePage.getInventory();
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        basePage.accept(event);
    }
}
