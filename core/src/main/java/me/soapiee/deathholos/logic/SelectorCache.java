package me.soapiee.deathholos.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectorCache {

    private final Map<UUID, HologramGroup> cachedGroup = new HashMap<>();

    public SelectorCache() {}

    public void addPlayer(UUID uuid, HologramGroup hologramGroup) {
        cachedGroup.put(uuid, hologramGroup);
    }

    public HologramGroup getGroup(UUID uuid) {
        return cachedGroup.get(uuid);
    }
}
