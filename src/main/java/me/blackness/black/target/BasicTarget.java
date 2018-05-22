package me.blackness.black.target;

import java.util.Objects;
import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryInteractEvent;

import me.blackness.black.Requirement;
import me.blackness.black.Target;
import me.blackness.black.event.ElementBasicEvent;

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
 * the most basic click target which can handle all the interact events.
 *
 * @see Target
 */
public final class BasicTarget implements Target {
    private final Consumer<ElementBasicEvent> handler;
    private final Requirement[] reqs;

    /**
     * ctor.
     *
     * @param handler handler of this target
     * @param reqs requirements of this target
     * @see Consumer
     * @see Requirement
     */
    public BasicTarget(final Consumer<ElementBasicEvent> handler, final Requirement... reqs) {
        Objects.requireNonNull(handler);
        this.handler = handler;
        this.reqs = reqs;
    }

    @Override
    public void handle(final InventoryInteractEvent event) {
        for (final Requirement req : reqs) {
            if (!req.control(event)) {
                return;
            }
        }
        handler.accept(new ElementBasicEvent(event));
    }
}
