package me.blackness.black;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import me.blackness.black.listener.InventoryClickListener;
import me.blackness.black.listener.InventoryCloseListener;
import me.blackness.black.listener.InventoryDragListener;
import me.blackness.black.listener.PluginListener;

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
 * object that controls the blackness.
 */
public final class Blackness {
    private static final Listener[] LISTENERS = {
        new PluginListener(new Blackness()),
        new InventoryClickListener(),
        new InventoryDragListener(),
        new InventoryCloseListener(),
    };

    private static final Queue<Plugin> PLUGINQUEUE = new ConcurrentLinkedQueue<>();

    /**
     * prepares the blackness for the specified plugin or adds it to the {@link #LISTENERS}.
     *
     * @param plugin plugin that needs blackness prepared
     * @see Plugin
     */
    public void prepareFor(final Plugin plugin) {
        if (PLUGINQUEUE.isEmpty()) {
            registerEvents(plugin);
        }

        synchronized (this) {
            PLUGINQUEUE.add(plugin);
        }
    }

    /**
     * this method handles every plugin disable event.
     *
     * @param event event to handle
     */
    public void processPluginDisable(final PluginDisableEvent event) {
        if (!PLUGINQUEUE.peek().equals(event.getPlugin())) {
            synchronized (this) {
                PLUGINQUEUE.remove(event.getPlugin());
                return;
            }
        }

        synchronized (this) {
            PLUGINQUEUE.poll();
        }

        final Plugin nextPlugin = PLUGINQUEUE.peek();
        if (nextPlugin != null && nextPlugin.isEnabled()) {
            registerEvents(nextPlugin);
        }
    }

    private void registerEvents(final Plugin plugin) {
        synchronized (this) {
            Arrays.stream(LISTENERS).forEach(listener ->
                Bukkit.getPluginManager().registerEvents(listener, plugin));
        }
    }
}
