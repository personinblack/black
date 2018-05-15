package me.blackness.black;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.event.ElementClickEvent;
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
 * pane is the type of all the sections of a page which can contain elements and
 * display those elements on a page.
 *
 * @see Page
 * @see Element
 */
public interface Pane {
    /**
     * fills the pane.
     *
     * @param element the element to fill the pane with
     * @see Element
     */
    void fill(Element element);

    /**
     * clears the pane.
     */
    void clear();

    /**
     * adds a new element.
     *
     * @param element the element to add
     * @return {@code true} if the element has been added or
     * {@code false} if there was no space for it
     * @see Element
     */
    boolean add(Element element);

    /**
     * adds new elements.
     *
     * @param elements the elements to add
     * @return an array that contains the elements which couldn't be added because of fullness or
     * an empty array if all the elements were added
     * @see Element
     */
    Element[] add(Element... elements);

    /**
     * inserts an element to the specified slot.
     *
     * @param element the element to add
     * @param locX x location of the slot
     * @param locY y location of the slot
     * @param shift either shift the element that already exist at the specified location or
     * replace it with this one
     * @throws IllegalArgumentException if the specified slot is not in the range of the pane
     * @see Element
     */
    void insert(Element element, int locX, int locY, boolean shift) throws IllegalArgumentException;

    /**
     * removes the element at the specified slot.
     *
     * @param locX x location of the slot
     * @param locY y location of the slot
     * @throws IllegalArgumentException if the specified slot is not in the range of the pane
     */
    void remove(int locX, int locY) throws IllegalArgumentException;

    /**
     * subscribe to the pane to get updated when it is updated.
     *
     * @param target the target that wants to subscribe
     * @see Target
     */
    void subscribe(Target<Object> target);

    /**
     * compares specified icon with icons of this pane's elements'.
     *
     * @param icon the itemstack to compare
     * @return {@code true} if this pane contains an element with specified icon or
     * {@code false} otherwise
     */
    boolean contains(ItemStack icon);
    void accept(ElementClickEvent event);
    void displayOn(Inventory inventory);
}
