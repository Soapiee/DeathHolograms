package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.logic.HologramGroup;

import java.util.UUID;

public class SelectorDataHandler_Unsupported implements SelectorDataHandler {

    @Override
    public String getHoloCache(UUID uuid) {
        return null;
    }

    @Override
    public void setHoloCache(UUID uuid, HologramGroup group){ }
}
