package me.soapiee.deathholos.internals;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.managers.ConfigManager;
import me.soapiee.deathholos.managers.MessageManager;
import me.soapiee.deathholos.utils.CustomLogger;
import me.soapiee.deathholos.utils.Message;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.ChatColor;

public class InternalsManager {

    @Getter private final HologramHandler hologramHandler;

    private final CustomLogger customLogger;
    private final MessageManager messageManager;
    private final ConfigManager configManager;
    private final boolean decentHologramsHook;
    private final boolean fancyHologramsHook;

    public InternalsManager(DeathHolos main) {
        customLogger = main.getCustomLogger();
        messageManager = main.getMessageManager();
        decentHologramsHook = main.isDecentHologramsHooked();
        fancyHologramsHook = main.isFancyHologramsHooked();
        configManager = main.getConfigManager();

        hologramHandler = initialiseHologramHandler();
    }

    public InternalsManager(HologramHandler hologramHandler, CustomLogger customLogger, MessageManager messageManager, ConfigManager configManager, boolean decentHologramsHook, boolean fancyHologramsHook) {
        this.hologramHandler = hologramHandler;
        this.customLogger = customLogger;
        this.messageManager = messageManager;
        this.configManager = configManager;
        this.decentHologramsHook = decentHologramsHook;
        this.fancyHologramsHook = fancyHologramsHook;
    }

    private HologramHandler initialiseHologramHandler() {
        HologramHandler hologramHandler;

        try {
            String packageName = InternalsManager.class.getPackage().getName();
            String providerName = getProviderName();

            if (configManager.isDebugMode()) Utils.consoleMsg(ChatColor.BLUE + providerName);
            hologramHandler = (HologramHandler) Class.forName(packageName + "." + providerName).newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 ClassCastException | IllegalArgumentException ex) {
            customLogger.logToFile(ex, messageManager.get(Message.LEGACYHOLOGRAMS));
            hologramHandler = new HologramHandler_Legacy();
        }

        return hologramHandler;
    }

    private String getProviderName() {
        int majorVersion = Utils.getMajorVersion();
        int minorVersion = Utils.getMinorVersion();

        if (decentHologramsHook) return "HologramHandler_DecentHolograms";
        if (fancyHologramsHook && isPaper()) return "HologramHandler_FancyHolograms";
        if (majorVersion > 19) return "HologramHandler_1_19_4";
        if (majorVersion == 19 && minorVersion > 3) return "HologramHandler_1_19_4";

        return (majorVersion >= 14) ? "HologramHandler_1_14" : "HologramHandler_Legacy";
    }

    private boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.ClientOption");
            return true;
        } catch (ClassNotFoundException ignored) {
        }

        return false;
    }
}
