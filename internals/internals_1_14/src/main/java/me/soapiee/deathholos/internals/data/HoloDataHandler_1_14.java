package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.internals.Keys;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class HoloDataHandler_1_14 extends HoloDataHandler {

    public HoloDataHandler_1_14() {
    }

    @Override
    public void setUpPlayer(UUID uuid) {
        String rawCache = readCache(uuid);

        HologramGroup group = hologramManager.getGroup(rawCache);
        if (!hasAccess(uuid, group)) group = getFallbackGroup(uuid);

        //TODO: May not be necessary
        if (group == null) updatePlayer(uuid, null);

        updatePlayer(uuid, group);
    }

    @Override
    public void savePlayer(UUID uuid) {
        HologramGroup group = getPlayer(uuid);
        if (!hasAccess(uuid, getPlayer(uuid))) group = getFallbackGroup(uuid);

        try {
            writeCache(uuid, group);
        } catch (Exception e) {
            //TODO:
            customLogger.logToFile(e, "Cannot find player to save cache to");
        }
    }

    @Override
    protected HologramGroup getFallbackGroup(UUID uuid) {
//        HologramGroup group = defaultGroup;
        HologramGroup group = null;

        for (HologramGroup holoGroup : hologramManager.getGroups()) {
            if (!hasAccess(uuid, holoGroup)) continue;

            group = holoGroup;
            break;
        }

        return group;
    }

    private String readCache(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);

        if (player != null) {
            String rawCache = player.getPersistentDataContainer().get(Keys.CACHEKEY, PersistentDataType.STRING);

            if (debugMode) Utils.consoleMsg(ChatColor.GOLD + "Reading cache: " + rawCache);
            clearCache(player);
            return rawCache;
        }

        return null;
    }

    private void writeCache(UUID uuid, HologramGroup group) throws NullPointerException {
        Player player = Bukkit.getPlayer(uuid);

        if (player == null) throw new NullPointerException();

        String holoName = (group == null) ? "null" : group.getName();
        player.getPersistentDataContainer().set(Keys.CACHEKEY, PersistentDataType.STRING, holoName);
        if (debugMode) Utils.consoleMsg(ChatColor.GREEN + "Saved cache as: " + holoName);
    }

    private void clearCache(Player player){
        player.getPersistentDataContainer().set(Keys.CACHEKEY, PersistentDataType.STRING, "null");
        if (debugMode) Utils.consoleMsg(ChatColor.GREEN + "cleared cache");
    }
}
