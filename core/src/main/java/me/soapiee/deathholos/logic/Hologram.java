package me.soapiee.deathholos.logic;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Sound;

import java.util.List;

public class Hologram {

    @Getter private final String keyID;
    @Getter private final Location location;
    @Getter private final List<String> text;
    @Getter private final Sound sound;

    public Hologram(String keyID, Location location, List<String> text,  HologramGroup group) {
        this.keyID = keyID;
        this.location = location;
        this.location.setY(location.getY() + (0.25 * text.size()));
        this.text = text;
        this.sound = group.getSound();
    }
}
