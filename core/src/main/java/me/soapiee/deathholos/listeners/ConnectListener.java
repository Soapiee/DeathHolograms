package me.soapiee.deathholos.listeners;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.choice_system.DataManager;
import me.soapiee.deathholos.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private final DeathHolos main;
    private final ConfigManager configManager;
    private final DataManager dataManager;

    public ConnectListener(DeathHolos main) {
        this.main = main;
        configManager = main.getConfigManager();
        dataManager = main.getChoiceManager().getDataManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        dataManager.getPlayer(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(main, () -> {
            if (player.hasPermission("deathholos.admin.updatenotif")) updateNotif(player);
        }, 20);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        dataManager.savePlayer(player.getUniqueId());
    }

    private void updateNotif(Player player) {
        if (configManager.isUpdateNotif()) main.getUpdateManager().updateAlert(player);
    }
}
