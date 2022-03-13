package me.blackness.black;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.InventoryHolder;

import me.blackness.observer.Target;

/**
 * page is the type of all the inventory pages which made from panes and
 * can be displayed to players.
 *
 * @author personinblack
 * @see Pane
 * @see Player
 * @since 1.0.0
 */
public interface Page extends InventoryHolder, Target<Object> {

    /**
     * adds a new pane to the page.
     *
     * @param pane pane to add
     * @param position the position of the new pane
     */
    void add(Pane pane, int position);

    /**
     * removes the pane at the specified position.
     *
     * @param position position
     */
    void remove(int position);

    /**
     * rearranges the panes from top to bottom. this will shift the panes towards bottom
     * when they are about to overlap each other.
     *
     * @param paneIndex index of the pane which will be rearranged
     * @param position the new desired position of the pane
     */
    void rearrange(int paneIndex, int position);

    /**
     * defines a new holder for the page. specifically to be used by page decorators.
     *
     * @param holder new holder
     */
    void defineHolder(Page holder);

    /**
     * shows this page.
     *
     * @param player player to show the page to
     * @see Player
     */
    void showTo(Player player);

    /**
     * this method is being triggered by the inventory close event in order to make this page
     * do not update the player who closed this page anymore.
     *
     * @param event event to handle
     * @see InventoryCloseEvent
     */
    void handleClose(InventoryCloseEvent event);

    /**
     * this method is being triggered by the inventory click event and passes the event to
     * the panes of it.
     *
     * @param event event to pass
     * @see InventoryInteractEvent
     */
    void accept(InventoryInteractEvent event);
}
