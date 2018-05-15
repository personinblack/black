package me.blackness.black.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
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
 * an event that gets sended to elements when they get clicked.
 *
 * @see Event
 * @see Element
 */
public final class ElementClickEvent {
    private final InventoryClickEvent baseEvent;

    public ElementClickEvent(final InventoryClickEvent baseEvent) {
        this.baseEvent = baseEvent;
    }

    /**
     * @return the player who triggered this event
     * @see Player
     */
    public Player player() {
        return (Player) baseEvent.getWhoClicked();
    }

    /**
     * compares the event's action with the given action.
     *
     * @param action the action to compare
     * @return {@code true} if they are the same or {@code false} otherwise
     * @see InventoryAction
     */
    public boolean actionIs(final InventoryAction action) {
        return baseEvent.getAction() == action;
    }

    /**
     * compares the event's slot with the given slot.
     *
     * @param slot the slot to compare
     * @return {@code true} if the clicked slot is the same slot as the given slot or
     * {@code false} otherwise
     * @see #rawSlotIs
     */
    public boolean slotIs(final int slot) {
        return baseEvent.getSlot() == slot;
    }

    /**
     * compares the event's raw slot with the given raw slot.
     *
     * @param rawSlot the raw slot to compare
     * @return {@code true} if the clicked raw slot is the same raw slot as the given raw slot or
     * {@code false} otherwise
     * @see #slotIs
     */
    public boolean rawSlotIs(final int rawSlot) {
        return baseEvent.getRawSlot() == rawSlot;
    }

    /**
     * compares the event's clicktype with the given clicktype.
     *
     * @param clickType the clicktype to compare
     * @return {@code true} if they are the same or {@code false} otherwise
     * @see ClickType
     */
    public boolean clickTypeIs(final ClickType clickType) {
        return baseEvent.getClick() == clickType;
    }

    /**
     * @return {@code true} if the click is a right click or {@code false} otherwise
     */
    public boolean isRightClick() {
        return baseEvent.isRightClick();
    }

    /**
     * @return {@code true} if the click is a left click or {@code false} otherwise
     */
    public boolean isLeftClick() {
        return baseEvent.isLeftClick();
    }

    /**
     * @return {@code true} if the click is a shift click or {@code false} otherwise
     */
    public boolean isShiftClick() {
        return baseEvent.isShiftClick();
    }

    /**
     * @return {@code true} if the action is a creative action or {@code false} otherwise
     * @see InventoryAction
     */
    public boolean isCreativeAction() {
        return baseEvent.getClick().isCreativeAction();
    }

    /**
     * @return {@code true} if the click is a keyboard click or {@code false} otherwise
     */
    public boolean isKeyboardClick() {
        return baseEvent.getClick().isKeyboardClick();
    }

    /**
     * compares the clicked keyboard key with the key given
     * (the key is {@code -1} if the click is not a keyboard click).
     *
     * @param key the key to compare
     * @return {@code true} if the clicked keyboard key is the same as the given or
     * {@code false} otherwise
     * @see #isKeyboardClick()
     */
    public boolean keyboardClickIs(final int key) {
        return baseEvent.getHotbarButton() == key;
    }

    /**
     * @return the itemstack that is on the player's cursor.
     * @see ItemStack
     * @see Player
     */
    public ItemStack itemOnCursor() {
        return baseEvent.getCursor().clone();
    }

    /**
     * @return the itemstack that the player has clicked on.
     * @see ItemStack
     * @see Player
     */
    public ItemStack currentItem() {
        return baseEvent.getCurrentItem().clone();
    }

    /**
     * replaces the item on the player's cursor with the given one.
     *
     * @param item itemstack to set
     * @see Player
     * @see ItemStack
     */
    public void setItemOnCursor(final ItemStack item) {
        schedule(() -> {
            baseEvent.getWhoClicked().setItemOnCursor(item);
        });
    }

    /**
     * cancels the action the player has done.
     */
    public void cancel() {
        baseEvent.setCancelled(true);
    }

    /**
     * closes all the open inventories the player has at the moment.
     */
    public void closeView() {
        schedule(() -> {
            baseEvent.getWhoClicked().closeInventory();
        });
    }

    private void schedule(final Runnable runnable) {
        Bukkit.getScheduler().runTask(
            baseEvent.getHandlers().getRegisteredListeners()[0].getPlugin(),
            runnable
        );
    }

    /**
     * this method can be removed at any time.
     * @deprecated this breaks the current view's listeners, please do edit the pane instead.
     */
    @Deprecated
    public void setCurrentItem(final ItemStack item) {
        schedule(() -> {
            baseEvent.setCurrentItem(item);
        });
    }

    /**
     * this method can be removed at any time.
     * @deprecated clicked inventory is the page which contains this element, use that page.
     */
    @Deprecated
    public Inventory getClickedInventory() {
        return baseEvent.getClickedInventory();
    }

    /**
     * this method can be removed at any time.
     * @deprecated because this is mutable
     * @see ElementClickEvent#itemOnCursor()
     */
    @Deprecated
    public ItemStack getCursor() {
        return baseEvent.getCursor();
    }

    /**
     * this method can be removed at any time.
     * @deprecated because this is mutable
     * @see ElementClickEvent#currentItem()
     */
    @Deprecated
    public ItemStack getCurrentItem() {
        return baseEvent.getCurrentItem();
    }

    /**
     * this method can be removed at any time.
     * @deprecated because the object should do the check itself
     * @see ElementClickEvent#slotIs(int)
     */
    @Deprecated
    public int getSlot() {
        return baseEvent.getSlot();
    }

    /**
     * this method can be removed at any time.
     * @deprecated because the object should do the check itself
     * @see ElementClickEvent#rawSlotIs(int)
     */
    @Deprecated
    public int getRawSlot() {
        return baseEvent.getRawSlot();
    }

    /**
     * this method can be removed at any time.
     * @deprecated because the object should do the check itself
     * @see ElementClickEvent#keyboardClickIs(int)
     */
    @Deprecated
    public int getHotbarButton() {
        return baseEvent.getHotbarButton();
    }

    /**
     * this method can be removed at any time.
     * @deprecated because the object should do the check itself
     * @see ElementClickEvent#actionIs(InventoryAction)
     */
    @Deprecated
    public InventoryAction getAction() {
        return baseEvent.getAction();
    }

    /**
     * this method can be removed at any time.
     * @deprecated because the object should do the check itself
     * @see ElementClickEvent#clickTypeIs(ClickType)
     */
    @Deprecated
    public ClickType getClick() {
        return baseEvent.getClick();
    }

    /**
     * this method can be removed at any time.
     * @deprecated because i dont like the name "getWhoClicked" and returning a "HumanEntity"
     * @see ElementClickEvent#player()
     */
    @Deprecated
    public HumanEntity getWhoClicked() {
        return baseEvent.getWhoClicked();
    }

    /**
     * this method can be removed at any time.
     * @deprecated because why someone would try to uncancel the event
     * while it can't be cancelled before
     * @see ElementClickEvent#cancel()
     */
    @Deprecated
    public void setCancelled(final Boolean cancel) {
        cancel();
    }
}
