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

public class PriorityHologramManager extends HologramManager {

    // HoloGroups stored via priority
    private final Map<Integer, HologramGroup> groups = new HashMap<>();

    public PriorityHologramManager(DeathHolos main, GroupFactory groupFactory) {
        super(main, groupFactory);
        load(Bukkit.getConsoleSender());
    }

    public void reload(CommandSender sender) {
        groups.clear();
        clearAllHolos();
        activeHolos.clear();
        load(sender);
    }

    protected void loadDefaultGroup(CommandSender sender) {
        int i = 0;

        for (int priority : groups.keySet()) {
            if (priority > i) i = priority;
        }

        setDefaultGroup(groups.get(i));
    }

    protected void loadGroups(CommandSender sender) {
        Set<String> keys = configManager.getGroupKeys();

        if (keys != null)
            for (String key : keys) {
                String priority = configManager.getStringAndReplace(ConfigPath.HOLOGRAM_PRIORITY, key);
                String permission = configManager.getStringAndReplace(ConfigPath.HOLOGRAM_PERMISSION, key);
                List<String> list = configManager.getListAndReplace(ConfigPath.HOLOGRAM_DESIGN, key);
                String sound = configManager.getStringAndReplace(ConfigPath.HOLOGRAM_SOUND, key);
                HologramGroup group = groupFactory.create(sender, key, priority, permission, list, sound);

                if (group == null) continue;

                if (groups.containsKey(group.getPriority())) {
                    customLogger.logToPlayer(sender, null, messageManager.getWithPlaceholder(Message.GROUPPRIORITYDUPE, group.getName(), (sender instanceof ConsoleCommandSender)));
                    continue;
                }

                groups.put(group.getPriority(), group);

                if (configManager.isDebugMode())
                    customLogger.logToPlayer(sender, null, messageManager.getWithPlaceholder(Message.GROUPCREATED, group.getName(), (sender instanceof ConsoleCommandSender)));
            }
    }

    @Override
    public HologramGroup getGroup(int priority) {
        return groups.get(priority);
    }

    public HologramGroup getGroup(String name) {
        for (HologramGroup group : groups.values()) {
            if (group.getName().equalsIgnoreCase(name)) return group;
        }

        return null;
    }

    public Collection<HologramGroup> getGroups() {
        return groups.values();
    }
}
