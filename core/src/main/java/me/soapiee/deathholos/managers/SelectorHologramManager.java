package me.soapiee.deathholos.managers;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.logic.GroupFactory;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.ConfigPath;
import me.soapiee.deathholos.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.*;

public class SelectorHologramManager extends HologramManager {

    // HoloGroups stored via config string
    private final Map<String, HologramGroup> groups = new HashMap<>();

    public SelectorHologramManager(DeathHolos main, GroupFactory groupFactory) {
        super(main, groupFactory);
        load(Bukkit.getConsoleSender());
    }

    @Override
    public void reload(CommandSender sender) {
        groups.clear();
        clearAllHolos();
        activeHolos.clear();
        load(sender);
    }

    //TODO:
//    @Override
//    protected void loadDefaultGroup(CommandSender sender) {
//        HologramGroup defaultGroup = groups.getOrDefault(configManager.getDefaultHolo(), null);
//        defaultGroup.setAsDefault();
//
//        holoDataHandler.setDefaultGroup(defaultGroup);
//
//        if (configManager.isDebugMode())
//            customLogger.logToPlayer(sender, null, messageManager.getWithPlaceholder(Message.DEFAULTGROUP, defaultGroup.getName(), (sender instanceof ConsoleCommandSender)));
//    }

    @Override
    protected void loadGroups(CommandSender sender) {
        Set<String> keys = configManager.getGroupKeys();

        if (keys != null)
            for (String key : keys) {
                if (groups.containsKey(key)) {
                    customLogger.logToPlayer(sender, null, messageManager.getWithPlaceholder(Message.GROUPIDDUPE, key, (sender instanceof ConsoleCommandSender)));
                    continue;
                }

                String permission = configManager.getStringAndReplace(ConfigPath.HOLOGRAM_PERMISSION, key);
                List<String> list = configManager.getListAndReplace(ConfigPath.HOLOGRAM_DESIGN, key);
                String sound = configManager.getStringAndReplace(ConfigPath.HOLOGRAM_SOUND, key);
                HologramGroup group = groupFactory.create(sender, key, permission, list, sound);

                if (group == null) continue;

                groups.put(key, group);

                if (configManager.isDebugMode())
                    customLogger.logToPlayer(sender, null, messageManager.getWithPlaceholder(Message.GROUPCREATED, group.getName(), (sender instanceof ConsoleCommandSender)));
            }
    }

    @Override public HologramGroup getGroup(String groupName) {
        return groups.get(groupName);
    }

    @Override public Collection<HologramGroup> getGroups() {
        return groups.values();
    }
}
