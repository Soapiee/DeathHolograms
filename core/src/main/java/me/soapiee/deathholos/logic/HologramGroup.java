package me.soapiee.deathholos.logic;

import lombok.Getter;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HologramGroup {

    @Getter private final String name;
    @Getter private final int priority;
    @Getter private final String permission;
    @Getter private final List<String> text;
    @Getter private final Sound sound;

    public HologramGroup(String name, int priority, String permission, List<String> text, @Nullable Sound sound) {
        this.name = name;
        this.priority = priority;
        this.permission = permission;
        this.text = text;
        this.sound = sound;
    }

}
