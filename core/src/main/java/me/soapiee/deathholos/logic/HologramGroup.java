package me.soapiee.deathholos.logic;

import lombok.Getter;

import java.util.List;

public class HologramGroup {

    @Getter private final String name;
    @Getter private final int priority;
    @Getter private final String permission;
    @Getter private final List<String> text;

    public HologramGroup(String name, int priority, String permission, List<String> text) {
        this.name = name;
        this.priority = priority;
        this.permission = permission;
        this.text = text;
    }

}
