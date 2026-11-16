package me.soapiee.deathholos.listeners;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.internals.data.HoloDataHandler;
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
    private final HoloDataHandler holoDataHandler;

    public ConnectListener(DeathHolos main) {
        this.main = main;
        configManager = main.getConfigManager();
        holoDataHandler = main.getInternalsManager().getHoloDataHandler();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        holoDataHandler.setUpPlayer(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(main, () -> {
            if (player.hasPermission("deathholos.admin.updatenotif")) updateNotif(player);
        }, 20);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        holoDataHandler.savePlayer(player.getUniqueId());
    }

    private void updateNotif(Player player) {
        if (configManager.isUpdateNotif()) main.getUpdateManager().updateAlert(player);
    }
}
