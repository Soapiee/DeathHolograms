package me.soapiee.deathholos.choice_system.priority;

import me.soapiee.deathholos.choice_system.DataManager;
import me.soapiee.deathholos.logic.HologramGroup;

import java.util.UUID;

public class PriorityDataManager implements DataManager {

    protected PriorityGroupManager groupManager;

    public PriorityDataManager(PriorityGroupManager groupManager) {
        this.groupManager = groupManager;
    }

    @Override
    public void reload() {
    }

    @Override
    public HologramGroup getPlayer(UUID uuid) {
        HologramGroup group = null;
        int highestPriority = groupManager.getHighestPriority();
        int lowestPriority = groupManager.getLowestPriority();

        for (int i = highestPriority; i < lowestPriority; i++) {
            HologramGroup holoGroup = groupManager.getGroup(i);
            if (holoGroup == null) continue;

            if (!hasAccess(uuid, holoGroup)) continue;

            group = holoGroup;
            break;
        }

        return group;
    }

    @Override
    public void updatePlayer(UUID uuid, HologramGroup hologramGroup) {
    }

    @Override
    public void savePlayer(UUID uuid) {
    }

}
