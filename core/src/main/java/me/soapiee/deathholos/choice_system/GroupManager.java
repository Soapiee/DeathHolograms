package me.soapiee.deathholos.choice_system;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.logic.GroupFactory;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.managers.ConfigManager;
import me.soapiee.deathholos.managers.MessageManager;
import me.soapiee.deathholos.utils.CustomLogger;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public abstract class GroupManager {

    protected final CustomLogger customLogger;
    protected final MessageManager messageManager;
    protected final ConfigManager configManager;
    protected final GroupFactory groupFactory;

    public GroupManager (DeathHolos main, GroupFactory groupFactory){
        customLogger = main.getCustomLogger();
        messageManager = main.getMessageManager();
        configManager = main.getConfigManager();
        this.groupFactory = groupFactory;
    }

    public abstract void reload(CommandSender sender);
    public abstract HologramGroup getGroup (String holoName);
    public abstract Collection<HologramGroup> getGroups();

    public HologramGroup getGroup (int priority) { return null; }
}
