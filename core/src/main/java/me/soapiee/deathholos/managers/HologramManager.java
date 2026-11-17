package me.soapiee.deathholos.managers;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.internals.data.DataHandler;
import me.soapiee.deathholos.internals.spawning.HologramHandler;
import me.soapiee.deathholos.logic.DespawnTask;
import me.soapiee.deathholos.logic.GroupFactory;
import me.soapiee.deathholos.logic.Hologram;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.CustomLogger;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class HologramManager {

    protected final DeathHolos main;
    protected final CustomLogger customLogger;
    protected final MessageManager messageManager;
    private final HologramHandler hologramHandler;
    protected final ConfigManager configManager;
    protected final GroupFactory groupFactory;
    protected final DataHandler dataHandler;

    protected final Map<String, DespawnTask> activeHolos = new HashMap<>();

    public HologramManager (DeathHolos main, GroupFactory groupFactory){
        this.main = main;
        customLogger = main.getCustomLogger();
        messageManager = main.getMessageManager();
        hologramHandler = main.getInternalsManager().getHologramHandler();
        configManager = main.getConfigManager();
        this.groupFactory = groupFactory;
        dataHandler = main.getInternalsManager().getDataHandler();
    }

    public abstract void reload(CommandSender sender);
    protected abstract void loadGroups(CommandSender sender);
    public abstract HologramGroup getGroup (String holoName);
    public abstract Collection<HologramGroup> getGroups();

    protected void load(CommandSender sender) {
        loadGroups(sender);
        //TODO:
//        loadDefaultGroup(sender);
    }

    public HologramGroup getGroup (int priority) { return null; }

    public void registerHolo(Hologram holo) {
        DespawnTask despawnTask = new DespawnTask(main, holo);
        activeHolos.put(holo.getKeyID(), despawnTask);
    }

    public void unregister(String keyID) {
        activeHolos.remove(keyID);
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
