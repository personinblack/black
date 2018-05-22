package me.blackness.black.pane;

import java.util.Arrays;

import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.blackness.black.Element;
import me.blackness.black.Pane;
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
 * a pane which takes some other panes and makes an animation out of them.
 *
 * @see Pane
 * @see BasicPane
 */
public final class LivePane implements Pane {
    private final Plugin plugin;
    private final int period;
    private final Pane[] frames;

    /**
     * ctor.
     *
     * @param plugin plugin for being used on registering bukkit tasks
     * @param period delay between every frame
     * @param frames frames to display in order
     */
    public LivePane(final Plugin plugin, final int period, final Pane... frames) {
        this.plugin = plugin;
        this.period = period;
        this.frames = frames;
    }

    /**
     * {@inheritDoc}
     *
     * @see #fill(int, Element)
     */
    @Override
    public void fill(final Element element) {
        for (final Pane frame : frames) {
            frame.fill(element);
        }
    }

    /**
     * fills the specified pane with specified elements.
     *
     * @param frame the frame number of the pane to fill
     * @param element the element to fill the pane with
     * @see Element
     */
    public void fill(final int frame, final Element element) {
        frames[frame].fill(element);
    }

    /**
     * {@inheritDoc}
     *
     * @see #fill(int, Element...)
     */
    @Override
    public void fill(final Element... elements) {
        for (final Pane frame : frames) {
            frame.fill(elements);
        }
    }

    /**
     * fills the specified pane with specified elements. this method will reuse the elements,
     * if amount of elements given is not enough to fill the pane.
     *
     * @param frame the frame number of the pane to fill
     * @param elements the elements to fill the pane with
     * @see Element
     */
    public void fill(final int frame, final Element... elements) {
        frames[frame].fill(elements);
    }

    @Override
    public void clear() {
        for (final Pane frame : frames) {
            frame.clear();
        }
    }

    @Override
    public boolean add(final Element element) {
        for (final Pane frame : frames) {
            if (frame.add(element)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Element[] add(final Element... elements) {
        Element[] leftOvers = elements;
        for (final Pane frame : frames) {
            leftOvers = frame.add(elements);

            if (elements.length == 0) {
                break;
            }
        }

        return leftOvers;
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated because you have to specify which frame
     * @see #insert(int, Element, int, int, boolean)
     */
    @Override @Deprecated
    public void insert(final Element element, final int locX, final int locY,
            final boolean shift) throws IllegalArgumentException {

        // this method is useless because you have to specify the frame to insert an element
    }

    /**
     * inserts an element to the specified slot of the specified frame.
     *
     * @param frame the frame which will get the specified element
     * @param element the element to add
     * @param locX x location of the slot
     * @param locY y location of the slot
     * @param shift either shift the element that already exist at the specified location or
     *     replace it with this one
     * @throws IllegalArgumentException if the specified slot is not in the range of the pane
     * @see Element
     */
    public void insert(final int frame, final Element element, final int locX, final int locY,
            final boolean shift) throws IllegalArgumentException {

        frames[frame].insert(element, locX, locY, shift);
    }

    /**
     * {@inheritDoc}
     *
     * @see #replaceAll(int, Element...)
     */
    @Override
    public void replaceAll(final Element... elements) {
        for (final Pane frame : frames) {
            frame.replaceAll(elements);
        }
    }

    /**
     * replaces all the elements of the said frame with the given ones. this method will reuse
     * the elements, if the amount of elements given is smaller then the amount of existing ones.
     *
     * @param frame the frame number of the pane to replace all
     * @param elements elements to fill the pane with
     * @see Element
     */
    public void replaceAll(final int frame, final Element... elements) {
        frames[frame].replaceAll(elements);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated because you have to specify which frame
     * @see #remove(int, int, int)
     */
    @Override @Deprecated
    public void remove(final int locX, final int locY) throws IllegalArgumentException {
        // this method is useless because you have to specify the frame to remove an element
    }

    /**
     * removes the element at the specified slot from the specified frame.
     *
     * @param frame the frame which will get the specified slot of it cleared
     * @param locX x location of the slot
     * @param locY y location of the slot
     * @throws IllegalArgumentException if the specified slot is not in the range of the pane
     */
    public void remove(final int frame, final int locX, final int locY)
                throws IllegalArgumentException {

        frames[frame].remove(locX, locY);
    }

    @Override
    public void subscribe(final Target<Object> target) {
        Arrays.stream(frames).forEach(frame -> frame.subscribe(target));
    }

    @Override
    public boolean contains(final ItemStack icon) {
        for (final Pane frame : frames) {
            if (frame.contains(icon)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        for (final Pane frame : frames) {
            frame.accept(event);
        }
    }

    @Override
    public void displayOn(final Inventory inventory) {
        frames[0].displayOn(inventory);

        new BukkitRunnable() {
            private int iterator;

            @Override
            public void run() {
                if (inventory.getViewers().isEmpty()) {
                    this.cancel();
                } else {
                    nextFrame().displayOn(inventory);
                }
            }

            private Pane nextFrame() {
                iterator = iterator + 1 < frames.length
                    ? iterator + 1
                    : 0;

                return frames[iterator];
            }
        }.runTaskTimer(plugin, 1, period);
    }
}
