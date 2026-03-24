package me.soapiee.deathholos.logic;

import lombok.Getter;
import org.bukkit.Location;

import java.util.List;

public class Hologram {

    @Getter private final String keyID;
    @Getter private final Location location;
    @Getter private final List<String> text;

    public Hologram(String keyID, Location location, List<String> text) {
        this.location = location;
        this.location.setY(location.getY() + (0.25 * text.size())); // Adds height depending on how many total lines)
        this.keyID = keyID;
        this.text = text;
    }
}
