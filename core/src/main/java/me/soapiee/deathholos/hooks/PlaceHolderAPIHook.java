package me.soapiee.deathholos.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.choice_system.DataManager;
import me.soapiee.deathholos.logic.HologramGroup;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceHolderAPIHook extends PlaceholderExpansion {

    private final DeathHolos main;
    private final DataManager dataManager;

    public PlaceHolderAPIHook(DeathHolos main) {
        this.main = main;
        dataManager = main.getChoiceManager().getDataManager();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "deathholograms";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Soapiee";
    }

    @Override
    public @NotNull String getVersion() {
        return main.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String identifier) {
        if (offlinePlayer != null && offlinePlayer.isOnline()) {
            Player player = offlinePlayer.getPlayer();

            switch (identifier.toLowerCase()) {
                case "active":
                    return getActiveDeathHolo(player);
            }
        }
        return null;
    }

    private String getActiveDeathHolo(Player player) {
        HologramGroup group = dataManager.getPlayer(player.getUniqueId());
        return (group == null) ? "null" : group.getName();
    }
}
