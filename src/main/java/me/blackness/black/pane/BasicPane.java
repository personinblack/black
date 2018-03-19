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
        elements = new Element[height][length];
        clear();
        try {
            validate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public BasicPane(int locX, int locY, int height, int length, Element element) {
        this(locX, locY, height, length);
        fill(element);
    }

    public BasicPane(int locX, int locY, int height, int length, Element... elements) {
        this(locX, locY, height, length);
        Arrays.stream(elements).forEach(this::add);
    }

    private int length() {
        return elements[0].length;
    }

    private int height() {
        return elements.length;
    }

    private Element emptyElement() {
        return new BasicElement(
            new ItemStack(Material.TNT),
            (event) -> {}, "emptyElement"
        );
    }

    @Override
    public void fill(Element element) {
        Objects.requireNonNull(element);
        for (int y = 0; y < height(); y++) {
            Arrays.fill(elements[y], element);
        }
    }

    @Override
    public void clear() {
        fill(emptyElement());
    }

    private void validate() throws Exception {
        final boolean locXFaulty = locX < 0;
        final boolean locYFaulty = locY < 0;
        final boolean heightFaulty = locY + height() > 6;
        final boolean lengthFaulty = locX + length() > 9;

        if (locXFaulty || locYFaulty || heightFaulty || lengthFaulty) {
            throw new Exception(
                String.format(
                    "Validation for the newest created Pane failed.\n" +
                        "locX (%s) is faulty: %s, locY (%s) is faulty: %s, " +
                        "height (%s) is faulty: %s, length (%s) is faulty: %s",
                    locX, locXFaulty, locY, locYFaulty, height(), heightFaulty, length(),
                        lengthFaulty
                )
            );
        }
    }

    private boolean isWithinBounds(int locX, int locY) {
        return locX < length() && locY < height() && locX >= 0 && locY >= 0;
    }

    private void shiftElementAt(int locX, int locY) {
        for (int y = elements.length - 1; y >= locY; y--) {
            for (int x = elements[y].length - 1; x >= locX; x--) {
                if (y + 1 < elements.length) {
                    elements[y + 1][x] = elements[y][x];
                } else if (x + 1 < elements[y].length) {
                    elements[0][x + 1] = elements[y][x];
                }
            }
        }
        elements[locY][locX] = emptyElement();
    }

    @Override
    public boolean add(Element element) {
        Objects.requireNonNull(element);
        for (int y = 0; isWithinBounds(0, y); y++) {
            for (int x = 0; isWithinBounds(x, y); x++) {
                if (elements[y][x].equals(emptyElement())) {
                    elements[y][x] = element;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Element[] add(Element... elements) {
        final ArrayList<Element> remainings = new ArrayList<>();

        for (Element element : elements) {
            if (!add(element)) {
                remainings.add(element);
            }
        }

        return remainings.toArray(new Element[]{});
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
        } else if (!shift || (shift && elements[locY][locX].equals(emptyElement()))) {
            elements[locY][locX] = element;
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
            elements[locY][locX] = emptyElement();
        }
    }

    @Override
    public boolean contains(ItemStack icon) {
        Objects.requireNonNull(icon);

        for (int y = 0; isWithinBounds(0, y); y++) {
            for (int x = 0; isWithinBounds(x, y); x++) {
                if (elements[y][x].equals(icon)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void accept(InventoryClickEvent event) {
        Objects.requireNonNull(event);
        for (int y = 0; isWithinBounds(0, y); y++) {
            for (int x = 0; isWithinBounds(x, y); x++) {
                if ((locX + x) + (locY + y) * 9 == event.getSlot()) {
                    elements[y][x].accept(event);
                }
            }
        }
    }

    @Override
    public void displayOn(Inventory inventory) {
        Objects.requireNonNull(inventory);
        for (int y = 0; isWithinBounds(0, y); y++) {
            for (int x = 0; isWithinBounds(x, y); x++) {
                final Element element = elements[y][x];
                if (!element.equals(emptyElement())) {
                    element.displayOn(inventory, locX + x, locY + y);
                }
            }
        }
    }
}
