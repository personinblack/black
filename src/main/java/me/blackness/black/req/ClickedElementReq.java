package me.blackness.black.req;

import java.util.Objects;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Element;
import me.blackness.black.Requirement;

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
 * a requirement which requires a certain element to be clicked.
 *
 * @see Requirement
 * @see Element
 */
public final class ClickedElementReq implements Requirement {
    private final Element element;

    /**
     * ctor.
     *
     * @param element the element that needs to be clicked
     */
    public ClickedElementReq(final Element element) {
        this.element = Objects.requireNonNull(element);
    }

    @Override
    public boolean control(final InventoryInteractEvent event) {
        if (event instanceof InventoryClickEvent) {
            return element.is(((InventoryClickEvent) event).getCurrentItem());
        } else {
            return false;
        }
    }
}
