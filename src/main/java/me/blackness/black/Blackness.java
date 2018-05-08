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
public final class Blackness {
    private final static Listener[] listeners  = {
        new PluginListener(),
        new InventoryClickListener(),
        new InventoryCloseListener()
    };
    private final static Queue<Plugin> pluginQueue = new ConcurrentLinkedQueue<>();

    public synchronized void prepareFor(Plugin plugin) {
        pluginQueue.add(plugin);

        if (pluginQueue.size() == 1) {
            registerEvents(plugin);
        }
    }

    public synchronized void processPluginDisable(PluginDisableEvent event) {
        if (!pluginQueue.peek().equals(event.getPlugin())) {
            pluginQueue.remove(event.getPlugin());
            return;
        }

        pluginQueue.poll();

        final Plugin nextPlugin = pluginQueue.peek();
        if (nextPlugin != null && nextPlugin.isEnabled()) {
            registerEvents(nextPlugin);
        }
    }

    private void registerEvents(Plugin plugin) {
        Arrays.stream(listeners).forEach(listener ->
            Bukkit.getPluginManager().registerEvents(listener, plugin));
    }
}
