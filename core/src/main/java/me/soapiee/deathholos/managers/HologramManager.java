package me.soapiee.deathholos.managers;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.internals.spawning.HologramHandler;
import me.soapiee.deathholos.logic.DespawnTask;
import me.soapiee.deathholos.logic.Hologram;

import java.util.HashMap;
import java.util.Map;

public class HologramManager {

    protected final DeathHolos main;

    protected final Map<String, DespawnTask> activeHolos = new HashMap<>();

    public HologramManager (DeathHolos main){
        this.main = main;
    }

    public void reload(HologramHandler hologramHandler) {
        clearAllHolos(hologramHandler);
        activeHolos.clear();
    }

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

    public void clearAllHolos (HologramHandler hologramHandler) {
        for (DespawnTask task : activeHolos.values()) {
            hologramHandler.despawn(task.getHolo());
            task.cancel();
        }
    }
}
