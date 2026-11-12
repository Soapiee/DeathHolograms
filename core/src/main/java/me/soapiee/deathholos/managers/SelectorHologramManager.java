package me.soapiee.deathholos.managers;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.internals.HologramHandler;
import me.soapiee.deathholos.logic.*;
import me.soapiee.deathholos.utils.ConfigPath;
import me.soapiee.deathholos.utils.CustomLogger;
import me.soapiee.deathholos.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.*;

public class SelectorHologramManager implements HologramManager {

    private final DeathHolos main;
    private final CustomLogger customLogger;
    private final MessageManager messageManager;
    private final HologramHandler hologramHandler;
    private final ConfigManager configManager;
    private final GroupFactory groupFactory;

    @Getter private final HologramGroup defaultGroup = null;
    private final Map<String, DespawnTask> activeHolos = new HashMap<>();
//
//    // HoloGroups stored via config string
    private final Map<String, HologramGroup> groups = new HashMap<>();


    public SelectorHologramManager(DeathHolos main, GroupFactory groupFactory) {
        this.main = main;
        customLogger = main.getCustomLogger();
        messageManager = main.getMessageManager();
        hologramHandler = main.getInternalsManager().getHologramHandler();
        configManager = main.getConfigManager();
        this.groupFactory = groupFactory;
        load(Bukkit.getConsoleSender());
    }

    public void reload(CommandSender sender) {
        groups.clear();

        clearAllHolos();
        activeHolos.clear();
        load(sender);
    }

//    private void load(CommandSender sender) {
//        loadGroups(sender);
//        loadDefaultGroup(sender);
//    }

    public void loadDefaultGroup(CommandSender sender) {

    }

    public void loadGroups(CommandSender sender) {
        Set<String> keys = configManager.getGroupKeys();

        if (keys != null)
            for (String key : keys) {
                if (groups.containsKey(key)) {
                    customLogger.logToPlayer(sender, null, messageManager.getWithPlaceholder(Message.GROUPIDDUPE, key, (sender instanceof ConsoleCommandSender)));
                    continue;
                }

//                String priority = configManager.getStringAndReplace(ConfigPath.HOLOGRAM_PRIORITY, key);
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

    public void registerHolo(Hologram holo) {
        DespawnTask despawnTask = new DespawnTask(main, holo);
        activeHolos.put(holo.getKeyID(), despawnTask);
    }

    public void unregister(String keyID) {
        activeHolos.remove(keyID);
    }

    public HologramGroup getDefaultGroup() {
        return groups.getOrDefault(configManager.getDefaultHolo(), null);
    }

    public HologramGroup getGroup(int priority) {
        return groups.get(priority);

    }

    public HologramGroup getGroup(String groupName) {
        return groups.get(groupName);
    }

//    public HologramGroup getGroup(String name) {
//        for (HologramGroup group : groups.values()) {
//            if (group.getName().equalsIgnoreCase(name)) return group;
//        }
//
//        return null;
//    }

    public Collection<HologramGroup> getGroups() {
        return groups.values();
    }

    public boolean exists(String keyID) {
        return activeHolos.containsKey(keyID);
    }

    public void clearAllHolos() {
        for (DespawnTask task : activeHolos.values()) {
            hologramHandler.despawn(task.getHolo());
            task.cancel();
        }
    }
}
