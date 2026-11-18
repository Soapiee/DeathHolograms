package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.CustomLogger;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class DataHandler {

    protected CustomLogger customLogger;
    protected boolean debugMode;

    public DataHandler() {
    }

    public void injectManagers(CustomLogger logger, boolean debugMode) {
        customLogger = logger;
        this.debugMode = debugMode;
    }

    public abstract String read(UUID uuid);

    public abstract void write(UUID uuid, HologramGroup group) throws NullPointerException;

    public abstract void clear(Player player);

}
