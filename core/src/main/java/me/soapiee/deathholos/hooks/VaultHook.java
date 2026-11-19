package me.soapiee.deathholos.hooks;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.managers.MessageManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private static Permission permissions = null;
    private static MessageManager messageManager;

    public VaultHook(DeathHolos main) {
        messageManager = main.getMessageManager();

        setupPermissions();
    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);

        if (rsp != null)
            permissions = rsp.getProvider();
    }

    public boolean hasPermissionPlugin() {
        return permissions != null;
    }

    public Boolean setPermission(OfflinePlayer target, String permission) {
        if (!hasPermissionPlugin()) return false;

        return permissions.playerAdd(null, target, permission);
    }
}
