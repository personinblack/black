package me.blackness.black.element;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.Element;

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
 * thread-safe decorator for any element.
 *
 * @see Element
 */
public class TSafeElement implements Element {
    private final Element baseElement;

    /**
     * ctor.
     *
     * @param baseElement the element to make thread-safe
     */
    public TSafeElement(final Element baseElement) {
        this.baseElement = Objects.requireNonNull(baseElement);
    }

    @Override
    public void displayOn(final Inventory inventory, final int locX, final int locY) {
        synchronized (baseElement) {
            baseElement.displayOn(inventory, locX, locY);
        }
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        baseElement.accept(event);
    }

    @Override
    public boolean is(final ItemStack icon) {
        return baseElement.is(Objects.requireNonNull(icon));
    }

    @Override
    public boolean is(final Element element) {
        Objects.requireNonNull(element);
        if (baseElement instanceof TSafeElement) {
            return this.baseElement.is(((TSafeElement) element).baseElement);
        } else {
            return baseElement.is(element);
        }
    }
}
