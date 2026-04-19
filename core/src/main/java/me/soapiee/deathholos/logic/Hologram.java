package me.soapiee.deathholos.logic;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.List;

public class Hologram {

    @Getter private final String keyID;
    @Getter private final Location location;
    @Getter private final List<String> text;
    @Getter private final Sound sound;

    public Hologram(String keyID, Location location, List<String> text,  HologramGroup group) {
        this.location = location;
        this.location.setY(location.getY() + (0.25 * text.size()));
        this.keyID = keyID;
        this.text = text;
        this.sound = group.getSound();
    }
}
