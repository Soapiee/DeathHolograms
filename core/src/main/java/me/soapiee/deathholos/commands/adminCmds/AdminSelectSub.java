package me.soapiee.deathholos.commands.adminCmds;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminSelectSub extends AbstractAdminSub {

    @Getter private final String IDENTIFIER = "select";

    public AdminSelectSub(DeathHolos main) {
        super(main, null, 3, 3);
    }

    // /dh select [player] [holo_name]
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!checkRequirements(sender, args, label)) return;

        Player target = getTarget(sender, args[1]);
        if (target == null) return;

        HologramGroup newGroup = validateGroup(target, args[2]);
        if (newGroup == null) return;

        if (groupAlreadyActive(sender, target, newGroup)) return;

        if (!hasAccess(sender, target, newGroup)) return;

        dataManager.updatePlayer(target.getUniqueId(), newGroup);
        sendMessage(sender, messageManager.getWithPlaceholder(Message.ADMINSUCCESSHOLOSELECTED, target.getName(), newGroup.getName(), (sender instanceof ConsoleCommandSender)));
        sendMessage(target, messageManager.getWithPlaceholder(Message.ADMINHOLOCHANGE, newGroup.getName(), false));
    }

    private Player getTarget(CommandSender sender, String string) {
        //TODO: Change to my own player cache
        Player target = Bukkit.getPlayer(string);

        if (target == null) sendMessage(sender, messageManager.get(Message.PLAYERNOTFOUND));

        return target;
    }

    private HologramGroup validateGroup(CommandSender sender, String groupName) {
        HologramGroup group = groupManager.getGroup(groupName);

        if (group == null) sendMessage(sender, messageManager.get(Message.INVALIDHOLOGROUP));

        return group;
    }

    private boolean groupAlreadyActive(CommandSender sender, Player target, HologramGroup newGroup) {
        HologramGroup currentGroup = dataManager.getPlayer(target.getUniqueId());
        if (currentGroup == null) return false;

        if (currentGroup == newGroup) sendMessage(sender, messageManager.getWithPlaceholder(Message.ADMINACTIVEHOLOSELECTED, target.getName(), newGroup.getName(), false));

        return currentGroup == newGroup;
    }

    private boolean hasAccess(CommandSender sender, Player target, HologramGroup group) {
        if (!target.hasPermission(group.getPermission())) {
            sendMessage(sender, messageManager.getWithPlaceholder(Message.ADMINERRORHOLOSELECTED, target.getName(), group.getName(), (sender instanceof ConsoleCommandSender)));
            return false;
        }

        return true;
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        return new ArrayList<>();
    }
}
