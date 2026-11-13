package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.logic.HologramGroup;

import java.util.UUID;

public interface SelectorDataHandler {

    String getHoloCache(UUID uuid);
    void setHoloCache(UUID uuid, HologramGroup group);
}
