package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.managers.HologramManager;
import me.soapiee.deathholos.utils.CustomLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class HoloDataHandler implements DataInterface {

    protected CustomLogger customLogger;
    protected boolean debugMode;
    protected HologramManager hologramManager;
//    @Getter @Setter protected HologramGroup defaultGroup = null;

    protected final Map<UUID, HologramGroup> cachedGroup = new HashMap<>();

    public HoloDataHandler() {
    }

    public void reload() {
        cachedGroup.clear();
    }

    public abstract void setUpPlayer(UUID uuid);

    public abstract void savePlayer(UUID uuid);

    protected abstract HologramGroup getFallbackGroup(UUID uuid);

    public void injectManagers(DeathHolos main) {
        customLogger = main.getCustomLogger();
        debugMode = main.getConfigManager().isDebugMode();
        hologramManager = main.getHoloManager();
    }

    @Override
    public HologramGroup getPlayer(UUID uuid) {
        HologramGroup group = cachedGroup.get(uuid);
        if (hasAccess(uuid, group)) return group;

        group = getFallbackGroup(uuid);
        updatePlayer(uuid, group);

        return group;
    }

    @Override
    public void updatePlayer(UUID uuid, HologramGroup hologramGroup) {
        cachedGroup.put(uuid, hologramGroup);
    }

    protected boolean hasAccess(UUID uuid, HologramGroup group) {
        if (group == null) return true;
//        if (group.isDefault()) return true;

        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return false;

        return player.hasPermission(group.getPermission());
    }

}
