package me.blackness.black;

import java.util.Arrays;
import java.util.Objects;
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

/**
 * object that controls the blackness.
 *
 * @author personinblack
 * @since 2.0.0
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
     * prepares the blackness for the specified plugin or adds it to the {@link #PLUGINQUEUE}.
     *
     * @param plugin plugin that needs blackness prepared
     * @see Plugin
     */
    public void prepareFor(final Plugin plugin) {
        Objects.requireNonNull(plugin);
        if (PLUGINQUEUE.isEmpty()) {
            registerListeners(plugin);
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
            registerListeners(nextPlugin);
        }
    }

    /**
     * registers all the listeners in the name of the plugin.
     *
     * @param plugin plugin
     * @see Plugin
     */
    private void registerListeners(final Plugin plugin) {
        synchronized (this) {
            Arrays.stream(LISTENERS).forEach(listener ->
                Bukkit.getPluginManager().registerEvents(listener, plugin));
        }
    }
}
