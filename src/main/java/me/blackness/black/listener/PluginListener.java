package me.blackness.black.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

import me.blackness.black.Blackness;

/**
 * a listener that listen for plugins getting disabled.
 *
 * @author personinblack
 * @version 2.0.0
 */
public final class PluginListener implements Listener {
    private final Blackness blackness;

    /**
     * ctor.
     *
     * @param blackness blackness to inform
     */
    public PluginListener(final Blackness blackness) {
        this.blackness = blackness;
    }

    /**
     * the listener that listens for plugin disables and informs blackness.
     *
     * @param event the event that happened
     */
    @EventHandler
    public void listener(final PluginDisableEvent event) {
        blackness.processPluginDisable(event);
    }
}
