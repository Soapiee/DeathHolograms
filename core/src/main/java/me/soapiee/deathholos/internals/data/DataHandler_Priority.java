package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.managers.PriorityHologramManager;

import java.util.UUID;

public class DataHandler_Priority extends DataHandler {


    public DataHandler_Priority() {
    }

    @Override
    public HologramGroup getPlayer(UUID uuid) {
        return getFallbackGroup(uuid);
    }

    @Override
    protected HologramGroup getFallbackGroup(UUID uuid) {
        HologramGroup group = null;
        int highestPriority = ((PriorityHologramManager) hologramManager).getHighestPriority();
        int lowestPriority = ((PriorityHologramManager) hologramManager).getLowestPriority();

        for (int i = highestPriority; i < lowestPriority; i++) {
            HologramGroup holoGroup = hologramManager.getGroup(i);
            if (holoGroup == null) continue;

            if (!hasAccess(uuid, holoGroup)) continue;

            group = holoGroup;
            break;
        }

        return group;
    }
}
