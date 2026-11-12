package me.soapiee.deathholos.managers;

import me.soapiee.deathholos.logic.HologramGroup;
import org.bukkit.command.CommandSender;

public interface HologramManager {

    void reload(CommandSender sender) ;

    default void load(CommandSender sender) {
        loadGroups(sender);
        loadDefaultGroup(sender);
    }

    HologramGroup getDefaultGroup();
    void loadGroups(CommandSender sender);
    void loadDefaultGroup(CommandSender sender);

    void clearAllHolos();
}
