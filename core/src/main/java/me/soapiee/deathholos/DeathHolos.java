package me.soapiee.deathholos;

import lombok.Getter;
import me.soapiee.deathholos.commands.AdminCmd;
import me.soapiee.deathholos.commands.PlayerCmd;
import me.soapiee.deathholos.internals.InternalsManager;
import me.soapiee.deathholos.internals.data.DataHandler;
import me.soapiee.deathholos.listeners.ConnectListener;
import me.soapiee.deathholos.listeners.DeathListener;
import me.soapiee.deathholos.logic.GroupFactory;
import me.soapiee.deathholos.managers.*;
import me.soapiee.deathholos.utils.CustomLogger;
import me.soapiee.deathholos.utils.Message;
import me.soapiee.deathholos.utils.Utils;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathHolos extends JavaPlugin {

    @Getter private MessageManager messageManager;
    @Getter private CustomLogger customLogger;
    @Getter private InternalsManager internalsManager;
    @Getter private ConfigManager configManager;
    @Getter private HologramManager holoManager;
    @Getter private UpdateManager updateManager;

    @Getter private boolean placeholderAPIHooked;
    @Getter private boolean decentHologramsHooked;
    @Getter private boolean fancyHologramsHooked;

    @Override
    public void onEnable() {
        registerUtils();
        registerManagers();
        registerListeners();
        registerCommands();
        registerUpdates();
    }

    @Override
    public void onDisable() {
        holoManager.clearAllHolos();

        if (!configManager.isPrioritySystem()) {
            DataHandler dataHandler = internalsManager.getDataHandler();
            savePlayerData(dataHandler);
        }
    }

    private void savePlayerData(DataHandler dataHandler) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            dataHandler.savePlayer(player.getUniqueId());
        }
    }

    private void registerUtils() {
        saveDefaultConfig();
        messageManager = new MessageManager(this);
        customLogger = new CustomLogger(this);
        new Metrics(this, 30432);
    }

    private void registerHooks() {
        placeholderAPIHooked = false;
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            if (!getServer().getPluginManager().getPlugin("PlaceholderAPI").isEnabled()) return;
            placeholderAPIHooked = true;
            if (configManager.isDebugMode()) Utils.consoleMsg(messageManager.get(Message.HOOKEDPLACEHOLDERAPI));
        }

        decentHologramsHooked = false;
        if (getServer().getPluginManager().getPlugin("DecentHolograms") != null) {
            if (!getServer().getPluginManager().getPlugin("DecentHolograms").isEnabled()) return;
            if (!configManager.isPrioritiseHook()) return;
            decentHologramsHooked = true;
            Utils.consoleMsg(messageManager.get(Message.HOOKEDDECENTHOLOGRAMS));
        }

        fancyHologramsHooked = false;
        if (getServer().getPluginManager().getPlugin("FancyHolograms") != null) {
            if (!getServer().getPluginManager().getPlugin("FancyHolograms").isEnabled()) return;
            if (!configManager.isPrioritiseHook()) return;
            fancyHologramsHooked = true;
            Utils.consoleMsg(messageManager.get(Message.HOOKEDFANCYHOLOGRAMS));
        }
    }

    private void registerManagers() {
        configManager = new ConfigManager(this);
        registerHooks();
        internalsManager = new InternalsManager(this);
        GroupFactory groupFactory = new GroupFactory(this);
        holoManager = initialiseHologramManager(groupFactory);
        internalsManager.getDataHandler().injectManagers(customLogger, holoManager, configManager.isDebugMode());
    }

    private HologramManager initialiseHologramManager(GroupFactory groupFactory) {
        HologramManager manager;

        if (Utils.getMajorVersion() < 15) {
            manager = new PriorityHologramManager(this, groupFactory);
            if (!configManager.isPrioritySystem()) configManager.setPrioritySystem(true);
        }
        else if (configManager.isPrioritySystem()) manager = new PriorityHologramManager(this, groupFactory);
        else manager = new SelectorHologramManager(this, groupFactory);

        return manager;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ConnectListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
    }

    private void registerCommands() {
        getCommand("adeathholograms").setExecutor(new AdminCmd(this));
        getCommand("deathholograms").setExecutor(new PlayerCmd(this));
    }

    private void registerUpdates() {
        updateManager = new UpdateManager(this, 133766);
        updateManager.updateAlert(Bukkit.getConsoleSender());
    }
}
