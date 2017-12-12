package me.blackness.black.pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.Element;
import me.blackness.black.Pane;
import me.blackness.black.element.BasicElement;

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
                                                        x"  personinblack
                                                        |
 */
public final class BasicPane implements Pane {
    private final Element[][] elements;
    private final int locX;
    private final int locY;

    public BasicPane(int locX, int locY, int height, int length) {
        this.locX = locX;
        this.locY = locY;
        elements = new Element[length][height];
        fill(emptyElement());
        try {
            validate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public BasicPane(int locX, int locY, int height, int length, Element... elements) {
        this(locX, locY, height, length);

        for (Element element : elements) {
            add(element);
        }
    }

    private int length() {
        return elements.length;
    }

    private int height() {
        return elements[0].length;
    }

    private Element emptyElement() {
        return new BasicElement(
            new ItemStack(Material.AIR),
            (event) -> event.setCancelled(true), "emptyElement"
        );
    }

    @Override
    public void fill(Element element) {
        Objects.requireNonNull(element);
        for (int x = 0; x < length(); x++) {
            Arrays.fill(elements[x], element);
        }
    }

    private void validate() throws Exception {
        if (locY + height() > 6 || locX + length() > 9
                || elements.length == 0
                || elements[0].length == 0) {

            throw new Exception(
                String.format(
                    "The BasicPane created, failed the validation.\n" +
                    "locX %s, locY %s, height %s, length %s",
                    locX, locY, height(), length()
                )
            );
        }
    }

    private boolean isWithinBounds(int locX, int locY) {
        return locX < length() && locY < height() && locX >= 0 && locY >= 0;
    }

    private void shiftElementAt(int locX, int locY) {
        for (int x = elements.length - 1; x >= locX; x--) {
            for (int y = elements[x].length - 1; y >= locY; y--) {
                if (x + 1 < elements.length) {
                    elements[x + 1][y] = elements[x][y];
                } else if (y + 1 < elements[x].length) {
                    elements[0][y + 1] = elements[x][y];
                }
            }
        }
        elements[locX][locY] = emptyElement();
    }

    @Override
    public boolean add(Element element) {
        Objects.requireNonNull(element);
        for (int x = 0; isWithinBounds(x, 0); x++) {
            for (int y = 0; isWithinBounds(x, y); y++) {
                if (elements[x][y].equals(emptyElement())) {
                    elements[x][y] = element;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Element[] add(Element... elements) {
        final ArrayList<Element> s = new ArrayList<>();

        for (Element element : elements) {
            if (!add(element)) {
                s.add(element);
            }
        }

        return s.toArray(new Element[]{});
    }

    @Override
    public void insert(Element element, int locX, int locY, boolean shift) throws Exception {
        Objects.requireNonNull(element);
        if (!isWithinBounds(locX, locY)) {
            throw new Exception(
                String.format(
                    "The specified location [%s][%s] is out of bounds",
                    locX, locY
                )
            );
        } else if (!shift || (shift && elements[locX][locY].equals(emptyElement()))) {
            elements[locX][locY] = element;
        } else {
            shiftElementAt(locX, locY);
            insert(element, locX, locY, !shift);
        }
    }

    @Override
    public void remove(int locX, int locY) throws Exception {
        if (!isWithinBounds(locX, locY)) {
            throw new Exception(
                String.format(
                    "The specified location [%s][%s] is out of bounds",
                    locX, locY
                )
            );
        } else {
            elements[locX][locY] = emptyElement();
        }
    }

    @Override
    public boolean contains(ItemStack icon) {
        Objects.requireNonNull(icon);

        for (int x = 0; isWithinBounds(x, 0); x++) {
            for (int y = 0; isWithinBounds(x, y); y++) {
                if (elements[x][y].equals(icon)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void accept(InventoryClickEvent event) {
        Objects.requireNonNull(event);
        for (int x = 0; isWithinBounds(x, 0); x++) {
            for (int y = 0; isWithinBounds(x, y); y++) {
                if ((locX + x) + (locY + y) * 9 == event.getSlot()) {
                    elements[x][y].accept(event);
                }
            }
        }
    }

    @Override
    public void displayOn(Inventory inventory) {
        Objects.requireNonNull(inventory);
        for (int x = 0; isWithinBounds(x, 0); x++) {
            for (int y = 0; isWithinBounds(x, y); y++) {
                elements[x][y].displayOn(inventory, locX + x, locY + y);
            }
        }
    }
}
