package me.soapiee.deathholos.choice_system.selector;

import me.soapiee.deathholos.choice_system.DataManager;
import me.soapiee.deathholos.internals.data.DataHandler;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.CustomLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectorDataManager implements DataManager {

    private final CustomLogger customLogger;
    private final SelectorGroupManager groupManager;
    private final DataHandler dataHandler;
    private final Map<UUID, HologramGroup> cachedGroup = new HashMap<>();

    public SelectorDataManager(CustomLogger customLogger, SelectorGroupManager groupManager, DataHandler dataHandler) {
        this.customLogger = customLogger;
        this.groupManager = groupManager;
        this.dataHandler = dataHandler;
    }

    @Override
    public HologramGroup getPlayer(UUID uuid) {
        if (!cachedGroup.containsKey(uuid)) {
            setUpPlayer(uuid);

            return cachedGroup.get(uuid);
        }

        HologramGroup group = cachedGroup.get(uuid);
        if (group != null && hasAccess(uuid, group)) return group;

        group = getFallbackGroup(uuid);
        updatePlayer(uuid, group);

        return getFallbackGroup(uuid);
    }

    @Override
    public void updatePlayer(UUID uuid, HologramGroup hologramGroup) {
        cachedGroup.put(uuid, hologramGroup);
    }

    @Override
    public void reload() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            savePlayer(player.getUniqueId());
        }

        cachedGroup.clear();
    }

    private HologramGroup getFallbackGroup(UUID uuid) {
        HologramGroup group = null;

        for (HologramGroup holoGroup : groupManager.getGroups()) {
            if (!hasAccess(uuid, holoGroup)) continue;

            group = holoGroup;
            break;
        }

        return group;
    }

    private void setUpPlayer(UUID uuid) {
        String rawCache = dataHandler.read(uuid);

        HologramGroup group = groupManager.getGroup(rawCache);

        if (group == null || !hasAccess(uuid, group)) group = getFallbackGroup(uuid);

        updatePlayer(uuid, group);
    }

    @Override
    public void savePlayer(UUID uuid) {
        HologramGroup group = getPlayer(uuid);
        if (!hasAccess(uuid, getPlayer(uuid))) group = getFallbackGroup(uuid);

        try {
            dataHandler.write(uuid, group);
        } catch (Exception e) {
            customLogger.logToFile(e, "Cannot find player to save cache to");
        }
    }

}
