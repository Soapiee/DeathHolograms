package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.internals.Keys;
import me.soapiee.deathholos.logic.HologramGroup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class SelectorDataHandler_1_14 implements SelectorDataHandler {

    @Override
    public String getHoloCache(UUID uuid) {
        Player player =  Bukkit.getPlayer(uuid);
        //TODO:
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "getting cache");

        if (player != null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "player is not null");
            return player.getPersistentDataContainer().get(Keys.CACHEKEY, PersistentDataType.STRING);
        }

        return null;
    }

    @Override
    public void setHoloCache(UUID uuid, HologramGroup group) {
        if (group == null) return;

        Player player =  Bukkit.getPlayer(uuid);
        //TODO:
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "setting cache");

        if (player != null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "player is not null");
            player.getPersistentDataContainer().set(Keys.CACHEKEY, PersistentDataType.STRING, group.getName());
        }
    }
}
