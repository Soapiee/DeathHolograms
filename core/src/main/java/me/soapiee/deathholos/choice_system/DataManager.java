package me.soapiee.deathholos.choice_system;

import me.soapiee.deathholos.logic.HologramGroup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface DataManager {

    void reload();
    HologramGroup getPlayer(UUID uuid);
    void updatePlayer(UUID uuid, HologramGroup hologramGroup);
    void savePlayer(UUID uuid);

    default boolean hasAccess(UUID uuid, HologramGroup group) {
        if (group == null) return true;

        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return false;

        return player.hasPermission(group.getPermission());
    }

}
