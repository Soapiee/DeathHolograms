package me.soapiee.deathholos.logic;

import lombok.Getter;
import org.bukkit.Sound;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HologramGroup {

    @Getter private final String name;
    @Getter private int priority;
    @Getter private String permission;
    @Getter private final List<String> text;
    @Getter private final Sound sound;
    @Getter private boolean isDefault;

    public HologramGroup(String name, int priority, String permission, List<String> text, @Nullable Sound sound) {
        this.name = name;
        this.priority = priority;
        this.permission = permission;
        this.text = text;
        this.sound = sound;
        isDefault = false;
    }

    public HologramGroup(String name, String permission, List<String> text, @Nullable Sound sound) {
        this(name, -1, permission, text, sound);
    }

    public void setAsDefault() {
        priority = 0;
        permission = "";
        isDefault = true;
    }

}
