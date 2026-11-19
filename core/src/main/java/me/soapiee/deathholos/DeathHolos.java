package me.soapiee.deathholos;

import lombok.Getter;
import me.soapiee.deathholos.choice_system.ChoiceManager;
import me.soapiee.deathholos.choice_system.DataManager;
import me.soapiee.deathholos.commands.AdminCmd;
import me.soapiee.deathholos.commands.PlayerCmd;
import me.soapiee.deathholos.hooks.PlaceHolderAPIHook;
import me.soapiee.deathholos.hooks.VaultHook;
import me.soapiee.deathholos.internals.InternalsManager;
import me.soapiee.deathholos.internals.spawning.HologramHandler;
import me.soapiee.deathholos.listeners.ConnectListener;
import me.soapiee.deathholos.listeners.DeathListener;
import me.soapiee.deathholos.managers.ConfigManager;
import me.soapiee.deathholos.managers.HologramManager;
import me.soapiee.deathholos.managers.MessageManager;
import me.soapiee.deathholos.managers.UpdateManager;
import me.soapiee.deathholos.utils.Choices;
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
    @Getter private ChoiceManager choiceManager;
    @Getter private HologramManager hologramManager;
    @Getter private HologramHandler hologramHandler;
    @Getter private UpdateManager updateManager;

    @Getter private VaultHook vaultHook;
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
        hologramManager.clearAllHolos(hologramHandler);

        if (choiceManager.getChoice() == Choices.SELECTOR) {
            DataManager dataManager = choiceManager.getDataManager();
            savePlayerData(dataManager);
        }
    }

    private void savePlayerData(DataManager dataManager) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            dataManager.savePlayer(player.getUniqueId());
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
            new PlaceHolderAPIHook(this).register();
            placeholderAPIHooked = true;
            if (configManager.isDebugMode()) Utils.consoleMsg(messageManager.get(Message.HOOKEDPLACEHOLDERAPI));
        }

        vaultHook = null;
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            if (!getServer().getPluginManager().getPlugin("Vault").isEnabled()) return;
            vaultHook = new VaultHook(this);
            if (configManager.isDebugMode()) Utils.consoleMsg(messageManager.get(Message.HOOKEDVAULT));
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
        hologramManager = new HologramManager(this);
        internalsManager = new InternalsManager(this);
        hologramHandler = internalsManager.initialiseHologramHandler();
        choiceManager = new ChoiceManager(this, configManager.getChoiceSystem());
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
