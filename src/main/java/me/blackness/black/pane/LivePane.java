package me.blackness.black.pane;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.blackness.black.Element;
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
public final class LivePane implements Pane {
    private final Plugin plugin;
    private final int period;
    private final Pane[] frames;

    public LivePane(Plugin plugin, int period, Pane... frames) {
        this.plugin = plugin;
        this.period = period;
        this.frames = frames;
    }

    private Pane emptyPane() {
        return new BasicPane(0, 0, 0, 1);
    }

    private Pane findFrame(ItemStack icon) {
        for (Pane frame : frames) {
            if (frame.contains(icon)) {
                return frame;
            }
        }

        return emptyPane();
    }

    @Override
    public void fill(Element element) {
        for (Pane frame : frames) {
            frame.fill(element);
        }
    }

    @Override
    public boolean add(Element element) {
        for (Pane frame : frames) {
            if (frame.add(element)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Element[] add(Element... elements) {
        for (Pane frame : frames) {
            elements = frame.add(elements);

            if (elements.length == 0) {
                return elements;
            }
        }

        return elements;
    }

    @Override @Deprecated
    public void insert(Element element, int locX, int locY, boolean shift) throws Exception {
    }

    public void insert(int frame, Element element, int locX, int locY, boolean shift)
            throws Exception {
        frames[frame].insert(element, locX, locY, shift);
    }

    @Override @Deprecated
    public void remove(int locX, int locY) throws Exception {
    }

    public void remove(int frame, int locX, int locY) throws Exception {
        frames[frame].remove(locX, locY);
    }

    @Override
    public boolean contains(ItemStack icon) {
        for (Pane frame : frames) {
            if (frame.contains(icon)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void accept(InventoryClickEvent event) {
        findFrame(event.getCurrentItem()).accept(event);
    }

    @Override
    public void displayOn(Inventory inventory) {
        frames[0].displayOn(inventory);

        new BukkitRunnable(){
            private int iterator;

            @Override
            public void run() {
                if (inventory.getViewers().isEmpty()) {
                    this.cancel();
                } else {
                    nextFrame().displayOn(inventory);
                }
            }

            private final Pane nextFrame() {
                iterator = iterator + 1 < frames.length
                    ? iterator + 1
                    : 0;

                return frames[iterator];
            }
        }.runTaskTimer(plugin, 1, period);
    }
}
