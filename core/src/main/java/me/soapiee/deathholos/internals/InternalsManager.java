package me.soapiee.deathholos.internals;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.internals.data.HoloDataHandler;
import me.soapiee.deathholos.internals.data.HoloDataHandler_Priority;
import me.soapiee.deathholos.internals.spawning.HologramHandler;
import me.soapiee.deathholos.internals.spawning.HologramHandler_Legacy;
import me.soapiee.deathholos.managers.ConfigManager;
import me.soapiee.deathholos.managers.MessageManager;
import me.soapiee.deathholos.utils.CustomLogger;
import me.soapiee.deathholos.utils.Message;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.ChatColor;

public class InternalsManager {

    @Getter private final HologramHandler hologramHandler;
    @Getter private final HoloDataHandler holoDataHandler;

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
        holoDataHandler = initialiseDataHandler();
    }

    private HologramHandler initialiseHologramHandler() {
        HologramHandler hologramHandler;

        try {
            String packageName = InternalsManager.class.getPackage().getName() + ".spawning";
            String providerName = getHoloProviderName();

            if (configManager.isDebugMode()) Utils.consoleMsg(ChatColor.BLUE + providerName);
            hologramHandler = (HologramHandler) Class.forName(packageName + "." + providerName).newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 ClassCastException | IllegalArgumentException ex) {
            customLogger.logToFile(ex, messageManager.get(Message.LEGACYHOLOGRAMS));
            hologramHandler = new HologramHandler_Legacy();
        }

        return hologramHandler;
    }

    private String getHoloProviderName() {
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

    private HoloDataHandler initialiseDataHandler() {
        HoloDataHandler dataHandler;

        try {
            String packageName = InternalsManager.class.getPackage().getName() + ".data";
            String providerName = getDataProviderName();

            if (configManager.isDebugMode()) Utils.consoleMsg(ChatColor.BLUE + providerName);
            dataHandler = (HoloDataHandler) Class.forName(packageName + "." + providerName).newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 ClassCastException | IllegalArgumentException ex) {
            //TODO:
//            customLogger.logToFile(ex, messageManager.get(Message.UNSUPPORTSELECTOR));
            dataHandler = new HoloDataHandler_Priority();
        }

        return dataHandler;
    }

    private String getDataProviderName() {
        int majorVersion = Utils.getMajorVersion();

        if (configManager.isPrioritySystem()) return "HologramHandler_Priority";

        return (majorVersion >= 14) ? "HoloDataHandler_1_14" : "HoloDataHandler_Priority";
    }
}
