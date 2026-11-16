package me.soapiee.deathholos.internals.data;

import me.soapiee.deathholos.logic.HologramGroup;

import java.util.UUID;

public interface DataInterface {

    //TODO:
//    HologramGroup getDefaultGroup();
//    void setDefaultGroup(HologramGroup group);

    HologramGroup getPlayer(UUID uuid);
    void updatePlayer(UUID uuid, HologramGroup hologramGroup);

}
