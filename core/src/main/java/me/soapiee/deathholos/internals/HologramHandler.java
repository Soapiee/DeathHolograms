package me.soapiee.deathholos.internals;

import me.soapiee.deathholos.logic.Hologram;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

public interface HologramHandler {

    void spawnHologram(Hologram holo);
    void despawn(Hologram holo);

    default void spawn(Hologram holo){
        playSound(holo);
        spawnParticle(holo);
        spawnHologram(holo);
    }

    default void playSound(Hologram holo) {
        Sound sound = holo.getSound();
        if (sound == null) return;

        Location location = holo.getLocation();
        location.getWorld().playSound(location, sound, 1F, 1F);
    }

    default void spawnParticle(Hologram holo) {
        Particle particle = holo.getParticle();
        if (particle == null) return;

        Location location = holo.getLocation();
        location.getWorld().spawnParticle(particle, location, 1);
    }
}
