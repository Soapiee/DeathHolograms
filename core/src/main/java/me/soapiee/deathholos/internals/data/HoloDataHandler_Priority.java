package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.managers.PriorityHologramManager;

import java.util.UUID;

public class HoloDataHandler_Priority extends HoloDataHandler {


    public HoloDataHandler_Priority() {
    }

    @Override
    public void setUpPlayer(UUID uuid) {
        //TODO:
//        HologramGroup group = defaultGroup;
//        int lowestPriority = group.getPriority();
//
//        for (int i = 0; i < lowestPriority; i++) {
//            HologramGroup holoGroup = hologramManager.getGroup(i);
//            if (holoGroup == null) continue;
//
//            if (!hasAccess(uuid, holoGroup)) continue;
//
//            group = holoGroup;
//            break;
//        }

        updatePlayer(uuid, getFallbackGroup(uuid));
    }

    @Override
    public void savePlayer(UUID uuid) {
        if (hasAccess(uuid, getPlayer(uuid))) return;

        updatePlayer(uuid, getFallbackGroup(uuid));
    }

    @Override
    protected HologramGroup getFallbackGroup(UUID uuid) {
        //TODO:
//        HologramGroup  group = defaultGroup;
        HologramGroup group = null;
        int lowestPriority = ((PriorityHologramManager) hologramManager).getLowestPriority();

        for (int i = 0; i < lowestPriority; i++) {
            HologramGroup holoGroup = hologramManager.getGroup(i);
            if (holoGroup == null) continue;

            if (!hasAccess(uuid, holoGroup)) continue;

            group = holoGroup;
            break;
        }

        return group;
    }
}
