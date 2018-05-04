package me.blackness.black.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
public class ElementClickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final InventoryClickEvent baseEvent;

    public ElementClickEvent(InventoryClickEvent baseEvent) {
        this.baseEvent = baseEvent;
    }

    @Deprecated
    public Inventory getClickedInventory() {
        return baseEvent.getClickedInventory();
    }

    @Deprecated
    public ItemStack getCursor() {
        return baseEvent.getCursor();
    }

    @Deprecated
    public ItemStack getCurrentItem() {
        return baseEvent.getCurrentItem();
    }

    @Deprecated
    public boolean isRightClick() {
        return baseEvent.isRightClick();
    }

    @Deprecated
    public boolean isLeftClick() {
        return baseEvent.isLeftClick();
    }

    @Deprecated
    public boolean isShiftClick() {
        return baseEvent.isShiftClick();
    }

    @Deprecated
    public void setCurrentItem(ItemStack item) {
        baseEvent.setCurrentItem(item);
    }

    @Deprecated
    public int getSlot() {
        return baseEvent.getSlot();
    }

    @Deprecated
    public int getRawSlot() {
        return baseEvent.getRawSlot();
    }

    @Deprecated
    public int getHotbarButton() {
        return baseEvent.getHotbarButton();
    }

    @Deprecated
    public InventoryAction getAction() {
        return baseEvent.getAction();
    }

    @Deprecated
    public ClickType getClick() {
        return baseEvent.getClick();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
