package me.blackness.black.pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.blackness.black.Element;
import me.blackness.black.Pane;
import me.blackness.black.element.BasicElement;
import me.blackness.black.event.ElementClickEvent;
import me.blackness.observer.Source;
import me.blackness.observer.Target;
import me.blackness.observer.source.BasicSource;

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

/**
 * a pane that has all the basic stuff.
 *
 * @see Pane
 */
public final class BasicPane implements Pane {
    private static final String LOCATION_OUTOFBOUNDS =
        "The specified location [%s][%s] is out of bounds";

    private final Source<Object> source;

    private final Element[][] elements;
    private final int locX;
    private final int locY;

    public BasicPane(final int locX, final int locY, final int height, final int length) {
        source = new BasicSource<>();

        this.locX = locX;
        this.locY = locY;
        elements = new Element[height][length];
        clear();
    }

    public BasicPane(final int locX, final int locY, final int height, final int length,
            final Element element) {

        this(locX, locY, height, length);
        fill(element);
    }

    public BasicPane(final int locX, final int locY, final int height, final int length,
            final Element... elements) {

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
            event -> {
            }, "emptyElement"
        );
    }

    @Override
    public void fill(final Element element) {
        Objects.requireNonNull(element);
        for (int y = 0; y < height(); y++) {
            Arrays.fill(elements[y], element);
        }

        this.source.notifyTargets(new Object());
    }

    @Override
    public void clear() {
        fill(emptyElement());
        this.source.notifyTargets(new Object());
    }

    private void validate(final int inventorySize) throws IllegalArgumentException {
        final boolean locXFaulty = locX < 0;
        final boolean locYFaulty = locY < 0;
        final boolean heightFaulty = locY + height() > inventorySize / 9 || height() <= 0;
        final boolean lengthFaulty = locX + length() > 9 || length() <= 0;

        if (locXFaulty || locYFaulty || heightFaulty || lengthFaulty) {
            throw new IllegalArgumentException(
                String.format(
                    "Validation for the newest created Pane failed.%n" +
                        "locX (%s) is faulty: %s, locY (%s) is faulty: %s, " +
                        "height (%s) is faulty: %s, length (%s) is faulty: %s",
                    locX, locXFaulty, locY, locYFaulty, height(), heightFaulty, length(),
                        lengthFaulty
                )
            );
        }
    }

    private boolean isWithinBounds(final int locX, final int locY) {
        return locX < length() && locY < height() && locX >= 0 && locY >= 0;
    }

    private void shiftElementAt(final int locX, final int locY) {
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
    public boolean add(final Element element) {
        final Object argument = new Object();
        Objects.requireNonNull(element);
        for (int y = 0; isWithinBounds(0, y); y++) {
            for (int x = 0; isWithinBounds(x, y); x++) {
                if (elements[y][x].is(emptyElement())) {
                    elements[y][x] = element;
                    this.source.notifyTargets(argument);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Element[] add(final Element... elements) {
        final ArrayList<Element> remainings = new ArrayList<>();

        for (Element element : elements) {
            if (!add(element)) {
                remainings.add(element);
            }
        }

        if (remainings.size() != elements.length) {
            this.source.notifyTargets(new Object());
        }

        return remainings.toArray(new Element[]{});
    }

    @Override
    public void insert(final Element element, final int locX, final int locY,
            final boolean shift) throws IllegalArgumentException {

        Objects.requireNonNull(element);
        if (!isWithinBounds(locX, locY)) {
            throw new IllegalArgumentException(
                String.format(
                    LOCATION_OUTOFBOUNDS,
                    locX, locY
                )
            );
        } else if (!shift || (shift && elements[locY][locX].is(emptyElement()))) {
            elements[locY][locX] = element;
        } else {
            shiftElementAt(locX, locY);
            insert(element, locX, locY, !shift);
        }

        this.source.notifyTargets(new Object());
    }

    @Override
    public void remove(final int locX, final int locY) throws IllegalArgumentException {
        if (!isWithinBounds(locX, locY)) {
            throw new IllegalArgumentException(
                String.format(
                    LOCATION_OUTOFBOUNDS,
                    locX, locY
                )
            );
        } else {
            elements[locY][locX] = emptyElement();
            this.source.notifyTargets(new Object());
        }
    }

    @Override
    public void subscribe(final Target<Object> target) {
        source.subscribe(target);
    }

    @Override
    public boolean contains(final ItemStack icon) {
        Objects.requireNonNull(icon);

        for (int y = 0; isWithinBounds(0, y); y++) {
            for (int x = 0; isWithinBounds(x, y); x++) {
                if (elements[y][x].is(icon)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void accept(final ElementClickEvent event) {
        Objects.requireNonNull(event);
        for (int y = 0; isWithinBounds(0, y); y++) {
            for (int x = 0; isWithinBounds(x, y); x++) {
                if (event.slotIs((locX + x) + (locY + y) * 9)) {
                    elements[y][x].accept(event);
                }
            }
        }
    }

    @Override
    public void displayOn(final Inventory inventory) {
        Objects.requireNonNull(inventory);
        try {
            validate(inventory.getSize());
        } catch (Exception ex) {
            Bukkit.getLogger().severe(ex.toString());
        }
        for (int y = 0; isWithinBounds(0, y); y++) {
            for (int x = 0; isWithinBounds(x, y); x++) {
                final Element element = elements[y][x];
                if (!element.is(emptyElement())) {
                    element.displayOn(inventory, locX + x, locY + y);
                }
            }
        }
    }
}
