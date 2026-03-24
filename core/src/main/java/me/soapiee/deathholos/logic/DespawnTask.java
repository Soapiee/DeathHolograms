package me.soapiee.deathholos.logic;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.internals.HologramHandler;
import me.soapiee.deathholos.managers.ConfigManager;
import me.soapiee.deathholos.managers.HologramManager;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class DespawnTask extends BukkitRunnable {

    private final HologramHandler hologramHandler;
    private final HologramManager hologramManager;
    private final ConfigManager configManager;
    @Getter private final String keyID;
    @Getter private final Hologram holo;

    public DespawnTask(DeathHolos main, Hologram holo) {
        hologramHandler = main.getInternalsManager().getHologramHandler();
        hologramManager = main.getHoloManager();
        configManager = main.getConfigManager();
        this.holo = holo;
        keyID = holo.getKeyID();

        if (configManager.isDebugMode()) Utils.debugMsg("", ChatColor.BLUE + "Despawn Task created for holo: " + holo.getKeyID());
        runTaskLater(main, main.getConfigManager().getDespawnTimer() * 20L);
    }

    @Override
    public void run() {
        hologramHandler.despawn(holo);
        hologramManager.unregister(keyID);

        if (configManager.isDebugMode()) Utils.debugMsg("", ChatColor.BLUE + "Holo " + holo.getKeyID() + " despawned");
    }
}
