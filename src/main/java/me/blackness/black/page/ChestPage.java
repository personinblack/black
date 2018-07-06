package me.blackness.black.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.blackness.black.Page;
import me.blackness.black.Pane;

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
 * a page that uses an inventory of {@link InventoryType#CHEST}.
 */
public final class ChestPage implements Page {
    private final String title;
    private final int size;
    private final Pane[] panes;
    private final List<Player> viewers;

    /**
     * ctor.
     *
     * @param title title of the page
     * @param size size of the page which has to be multiple of 9
     * @param panes panes of this page to display
     */
    public ChestPage(final String title, final int size, final Pane... panes) {
        this.title = Objects.requireNonNull(title);
        this.size = size < 9 ? 9 : size;
        this.panes = Objects.requireNonNull(panes);
        viewers = new ArrayList<>();

        Arrays.stream(panes).forEach(pane -> pane.subscribe(this));
    }

    @Override
    public void showTo(final Player player) {
        final Inventory inventory = Bukkit.createInventory(this, size, title);

        for (final Pane pane : panes) {
            pane.displayOn(inventory);
        }

        player.openInventory(inventory);
        if (!viewers.contains(player)) {
            viewers.add(player);
        }
    }

    @Override
    public void handleClose(final InventoryCloseEvent event) {
        viewers.remove((Player) event.getPlayer());
    }

    @Override
    public void update(final Object argument) {
        viewers.forEach(viewer -> {
            final Inventory inventory = viewer.getOpenInventory().getTopInventory();
            inventory.clear();
            Arrays.stream(panes).forEach(pane -> {
                pane.displayOn(inventory);
            });
        });
    }

    /**
     * {@inheritDoc}
     * @deprecated because this is against oop.
     */
    @Override @Deprecated
    public Inventory getInventory() {
        return Bukkit.createInventory(null, 9);
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        for (final Pane pane : panes) {
            pane.accept(event);
        }
    }
}
