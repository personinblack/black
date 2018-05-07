package me.blackness.black.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
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
public final class ElementClickEvent {
    private final InventoryClickEvent baseEvent;

    public ElementClickEvent(InventoryClickEvent baseEvent) {
        this.baseEvent = baseEvent;
    }

    public Player player() {
        return (Player) baseEvent.getWhoClicked();
    }

    public boolean actionIs(InventoryAction action) {
        return baseEvent.getAction() == action;
    }

    public boolean slotIs(int slot) {
        return baseEvent.getSlot() == slot;
    }

    public boolean clickTypeIs(ClickType clickType) {
        return baseEvent.getClick() == clickType;
    }

    public boolean isRightClick() {
        return baseEvent.isRightClick();
    }

    public boolean isLeftClick() {
        return baseEvent.isLeftClick();
    }

    public boolean isShiftClick() {
        return baseEvent.isShiftClick();
    }

    public boolean isCreativeAction() {
        return baseEvent.getClick().isCreativeAction();
    }

    public boolean isKeyboardClick() {
        return baseEvent.getClick().isKeyboardClick();
    }

    public boolean isKeyboardClick(int key) {
        return baseEvent.getHotbarButton() == key;
    }

    public ItemStack itemOnCursor() {
        return baseEvent.getCursor().clone();
    }

    public ItemStack currentItem() {
        return baseEvent.getCurrentItem();
    }

    public void setItemOnCursor(ItemStack item) {
        schedule(() -> {
            baseEvent.getWhoClicked().setItemOnCursor(item);
        });
    }

    public void cancel() {
        baseEvent.setCancelled(true);
    }

    public void closeView() {
        schedule(() -> {
            baseEvent.getWhoClicked().closeInventory();
        });
    }

    private void schedule(Runnable runnable) {
        Bukkit.getScheduler().runTask(
            baseEvent.getHandlers().getRegisteredListeners()[0].getPlugin(),
            runnable
        );
    }

    /**
     * this method can be removed at any time.
     * <p>
     * this breaks the current views listeners, please do edit the pane instead.
     */
    @Deprecated
    public void setCurrentItem(ItemStack item) {
        schedule(() -> {
            baseEvent.setCurrentItem(item);
        });
    }

    /**
     * this method can be removed at any time.
     * <p>
     * clicked inventory is the page which contains this element, use that page.
     */
    @Deprecated
    public Inventory getClickedInventory() {
        return baseEvent.getClickedInventory();
    }

    /**
     * this method can be removed at any time.
     * @see ElementClickEvent#itemOnCursor()
     */
    @Deprecated
    public ItemStack getCursor() {
        return baseEvent.getCursor();
    }

    /**
     * this method can be removed at any time
     * @see ElementClickEvent#currentItem()
     */
    @Deprecated
    public ItemStack getCurrentItem() {
        return baseEvent.getCurrentItem();
    }

    /**
     * this method can be removed at any time.
     * @see ElementClickEvent#slotIs(int)
     */
    @Deprecated
    public int getSlot() {
        return baseEvent.getSlot();
    }

    /**
     * this method can be removed at any time.
     */
    @Deprecated
    public int getRawSlot() {
        return baseEvent.getRawSlot();
    }

    /**
     * this method can be removed at any time.
     * @see ElementClickEvent#isKeyboardClick(int)
     */
    @Deprecated
    public int getHotbarButton() {
        return baseEvent.getHotbarButton();
    }

    /**
     * this method can be removed at any time.
     * @see ElementClickEvent#actionIs(InventoryAction)
     */
    @Deprecated
    public InventoryAction getAction() {
        return baseEvent.getAction();
    }

    /**
     * this method can be removed at any time.
     * @see ElementClickEvent#clickTypeIs(ClickType)
     */
    @Deprecated
    public ClickType getClick() {
        return baseEvent.getClick();
    }

    /**
     * this method can be removed at any time.
     * @see ElementClickEvent#player()
     */
    @Deprecated
    public HumanEntity getWhoClicked() {
        return baseEvent.getWhoClicked();
    }

    /**
     * this method can be removed at any time.
     * @see ElementClickEvent#cancel()
     */
    @Deprecated
    public void setCancelled(Boolean cancel) {
        cancel();
    }
}
