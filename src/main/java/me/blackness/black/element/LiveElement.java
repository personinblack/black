package me.blackness.black.element;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.blackness.black.Element;

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
public class LiveElement implements Element {
    private final Plugin plugin;
    private final int period;
    private final Element[] frames;
    private Element currentFrame;

    public LiveElement(Plugin plugin, int period, Element... frames) {
        this.plugin = plugin;
        this.period = period;
        this.frames = frames;
        currentFrame = frames[0];
    }

    @Override
    public void accept(InventoryClickEvent event) {
        currentFrame.accept(event);
    }

    @Override
    public void displayOn(Inventory inventory, int locX, int locY) {
        currentFrame.displayOn(inventory, locX, locY);

        new BukkitRunnable(){
            private int i;

            @Override
            public void run() {
                if (inventory.getViewers().isEmpty()) {
                    this.cancel();
                } else {
                    nextFrame().displayOn(inventory, locX, locY);

                    for (HumanEntity player : inventory.getViewers()) {
                        ((Player) player).updateInventory();
                    }
                }
            }

            private final Element nextFrame() {
                i = i + 1 < frames.length
                    ? i + 1
                    : 0;

                return currentFrame = frames[i];
            }
        }.runTaskTimer(plugin, 1, period);
    }

    @Override
    public boolean equals(ItemStack icon) {
        return currentFrame.equals(icon);
    }

    @Override
    public boolean equals(Element element) {
        return currentFrame.equals(element);
    }
}
