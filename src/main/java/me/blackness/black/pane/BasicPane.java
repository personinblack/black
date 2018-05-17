package me.blackness.black.pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

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
    private static final String LOC_OUT =
        "The specified location [%s][%s] is out of bounds";

    private final Source<Object> source;

    private final Element[][] paneElements;
    private final int locX;
    private final int locY;

    /**
     * ctor.
     *
     * @param locX the x location of the top left corner of this pane
     * @param locY the y location of the top left corner of this pane
     * @param height height of this pane
     * @param length length of this pane
     */
    public BasicPane(final int locX, final int locY, final int height, final int length) {
        source = new BasicSource<>();
        this.locX = locX;
        this.locY = locY;
        paneElements = new Element[height][length];
        clear();
    }

    /**
     * ctor.
     *
     * @param locX the x location of the top left corner of this pane
     * @param locY the y location of the top left corner of this pane
     * @param height height of this pane
     * @param length length of this pane
     * @param element element to fill the pane with
     *
     * @see #fill(Element)
     */
    public BasicPane(final int locX, final int locY, final int height, final int length,
            final Element element) {

        this(locX, locY, height, length);
        fill(element);
    }

    /**
     * ctor.
     *
     * @param locX the x location of the top left corner of this pane
     * @param locY the y location of the top left corner of this pane
     * @param height height of this pane
     * @param length length of this pane
     * @param elements elements to fill the pane with
     *
     * @see #fill(Element...)
     */
    public BasicPane(final int locX, final int locY, final int height, final int length,
            final Element... elements) {

        this(locX, locY, height, length);
        Arrays.stream(elements).forEach(this::add);
    }

    private int length() {
        return paneElements[0].length;
    }

    private int height() {
        return paneElements.length;
    }

    private Element emptyElement() {
        return new BasicElement(
            new ItemStack(Material.TNT),
            event -> {
            }, "emptyElement"
        );
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

    private boolean isWithinBounds(final int xToCheck, final int yToCheck) {
        return xToCheck < length() && yToCheck < height() && xToCheck >= 0 && yToCheck >= 0;
    }

    private void shiftElementAt(final int xToShift, final int yToShift) {
        for (int y = height() - 1; y >= 0; y--) {
            for (int x = length() - 1; x >= 0; x--) {
                if (y < yToShift || (y == yToShift && x < xToShift)) {
                    continue;
                } else if (x + 1 < length()) {
                    paneElements[y][x + 1] = paneElements[y][x];
                } else if (y + 1 < height()) {
                    paneElements[y + 1][0] = paneElements[y][x];
                }
            }
        }

        paneElements[yToShift][xToShift] = emptyElement();
    }

    private boolean forEachSlot(final BiFunction<Integer, Integer, Boolean> action) {
        for (int y = 0; isWithinBounds(0, y); y++) {
            for (int x = 0; isWithinBounds(x, y); x++) {
                if (action.apply(y, x)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void forEachSlot(final BiConsumer<Integer, Integer> action) {
        forEachSlot((y, x) -> {
            action.accept(y, x);
            return false;
        });
    }

    @Override
    public void fill(final Element element) {
        Objects.requireNonNull(element);
        for (int y = 0; y < height(); y++) {
            Arrays.fill(paneElements[y], element);
        }
        this.source.notifyTargets(new Object());
    }

    @Override
    public void fill(final Element... elements) {
        Objects.requireNonNull(elements);
        final Queue<Element> queue = new LinkedList<>(Arrays.asList(elements));
        forEachSlot((y, x) -> {
            if (queue.isEmpty()) {
                queue.addAll(Arrays.asList(elements));
            }
            if (paneElements[y][x].is(emptyElement())) {
                paneElements[y][x] = queue.poll();
            }
        });
        this.source.notifyTargets(new Object());
    }

    @Override
    public void clear() {
        fill(emptyElement());
    }

    @Override
    public boolean add(final Element element) {
        Objects.requireNonNull(element);
        return forEachSlot((y, x) -> {
            if (paneElements[y][x].is(emptyElement())) {
                paneElements[y][x] = element;
                this.source.notifyTargets(new Object());
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public Element[] add(final Element... elements) {
        final ArrayList<Element> remainings = new ArrayList<>();
        for (final Element element : elements) {
            if (!add(element)) {
                remainings.add(element);
            }
        }
        return remainings.toArray(new Element[]{});
    }

    @Override
    public void insert(final Element element, final int locX, final int locY,
            final boolean shift) throws IllegalArgumentException {

        Objects.requireNonNull(element);
        if (isWithinBounds(locX, locY)) {
            if (shift && !paneElements[locY][locX].is(emptyElement())) {
                shiftElementAt(locX, locY);
            }
            paneElements[locY][locX] = element;
        } else {
            throw new IllegalArgumentException(
                String.format(
                    LOC_OUT,
                    locX, locY
                )
            );
        }
        this.source.notifyTargets(new Object());
    }

    @Override
    public void replaceAll(final Element... elements) {
        Objects.requireNonNull(elements);
        final Queue<Element> queue = new LinkedList<>(Arrays.asList(elements));
        forEachSlot((y, x) -> {
            if (!queue.isEmpty()) {
                paneElements[y][x] = queue.poll();
            }
        });
        this.source.notifyTargets(new Object());
    }

    @Override
    public void remove(final int locX, final int locY) throws IllegalArgumentException {
        if (isWithinBounds(locX, locY)) {
            paneElements[locY][locX] = emptyElement();
            this.source.notifyTargets(new Object());
        } else {
            throw new IllegalArgumentException(
                String.format(
                    LOC_OUT,
                    locX, locY
                )
            );
        }
    }

    @Override
    public void subscribe(final Target<Object> target) {
        Objects.requireNonNull(target);
        source.subscribe(target);
    }

    @Override
    public boolean contains(final ItemStack icon) {
        Objects.requireNonNull(icon);
        return forEachSlot((y, x) -> {
            return paneElements[y][x].is(icon);
        });
    }

    @Override
    public void accept(final ElementClickEvent event) {
        Objects.requireNonNull(event);
        forEachSlot((y, x) -> {
            if (event.slotIs(locX + x + (locY + y) * 9)) {
                paneElements[y][x].accept(event);
            }
        });
    }

    @Override
    public void displayOn(final Inventory inventory) {
        Objects.requireNonNull(inventory);
        try {
            validate(inventory.getSize());
        } catch (IllegalArgumentException ex) {
            Bukkit.getLogger().severe(ex.toString());
            return;
        }
        forEachSlot((y, x) -> {
            final Element element = paneElements[y][x];
            if (!element.is(emptyElement())) {
                element.displayOn(inventory, locX + x, locY + y);
            }
        });
    }
}
