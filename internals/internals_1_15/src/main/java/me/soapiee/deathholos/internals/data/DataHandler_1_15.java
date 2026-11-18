package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.internals.Keys;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class DataHandler_1_15 extends DataHandler {

    public DataHandler_1_15() {
    }

    @Override
    public String read(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);

        if (player != null) {
            String rawCache = player.getPersistentDataContainer().get(Keys.CACHEKEY, PersistentDataType.STRING);

            if (debugMode) Utils.consoleMsg(ChatColor.GOLD + "Reading cache: " + rawCache);
            clear(player);
            return rawCache;
        }

        return null;
    }

    @Override
    public void write(UUID uuid, HologramGroup group) throws NullPointerException {
        Player player = Bukkit.getPlayer(uuid);

        if (player == null) throw new NullPointerException();

        String holoName = (group == null) ? "null" : group.getName();
        player.getPersistentDataContainer().set(Keys.CACHEKEY, PersistentDataType.STRING, holoName);
        if (debugMode) Utils.consoleMsg(ChatColor.GREEN + "Saved cache as: " + holoName);
    }

    @Override
    public void clear(Player player){
        player.getPersistentDataContainer().set(Keys.CACHEKEY, PersistentDataType.STRING, "null");
        if (debugMode) Utils.consoleMsg(ChatColor.GREEN + "cleared cache");
    }
}
