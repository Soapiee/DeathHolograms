package me.soapiee.deathholos.listeners;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.internals.data.SelectorDataHandler;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.logic.SelectorCache;
import me.soapiee.deathholos.managers.ConfigManager;
import me.soapiee.deathholos.managers.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ConnectListener implements Listener {

    private final DeathHolos main;
    private final ConfigManager configManager;
    private final HologramManager hologramManager;
    private final SelectorDataHandler selectorDataHandler;
    private final SelectorCache selectorCache;

    public ConnectListener(DeathHolos main) {
        this.main = main;
        configManager = main.getConfigManager();
        hologramManager = main.getHoloManager();
        selectorDataHandler = main.getInternalsManager().getSelectorDataHandler();
        selectorCache = main.getSelectorCache();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!configManager.isPrioritySystem()) setupHoloCache(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(main, () -> {
            if (player.hasPermission("deathholos.admin.updatenotif")) updateNotif(player);
        }, 20);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        saveHoloCache(player.getUniqueId());
    }

    private void updateNotif(Player player) {
        if (configManager.isUpdateNotif()) main.getUpdateManager().updateAlert(player);
    }

    private void setupHoloCache(UUID uuid) {
        HologramGroup group = main.getHoloManager().getGroup(readHoloCache(uuid));


        //TODO:
        if (group == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "group string is null");
            group = hologramManager.getDefaultGroup();}

        selectorCache.addPlayer(uuid, group);
    }

    private String readHoloCache(UUID uuid){
        return selectorDataHandler.getHoloCache(uuid);
    }

    private void saveHoloCache(UUID uuid){
        selectorDataHandler.setHoloCache(uuid, selectorCache.getGroup(uuid));
    }
}
