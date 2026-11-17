package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.managers.HologramManager;
import me.soapiee.deathholos.utils.CustomLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class DataHandler implements DataInterface {

    protected CustomLogger customLogger;
    protected boolean debugMode;
    protected HologramManager hologramManager;

    public DataHandler() {}

    public void reload() {}

    public void savePlayer(UUID uuid) {}

    protected abstract HologramGroup getFallbackGroup(UUID uuid);

    public void injectManagers(CustomLogger logger,  HologramManager hologramManager, boolean debugMode) {
        customLogger = logger;
        this.debugMode = debugMode;
        this.hologramManager =  hologramManager;
    }

    public abstract HologramGroup getPlayer(UUID uuid);

    public void updatePlayer(UUID uuid, HologramGroup hologramGroup) {}

    protected boolean hasAccess(UUID uuid, HologramGroup group) {
        if (group == null) return true;

        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return false;

        return player.hasPermission(group.getPermission());
    }

}
