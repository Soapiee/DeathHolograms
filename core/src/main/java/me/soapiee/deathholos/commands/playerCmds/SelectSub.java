package me.soapiee.deathholos.commands.playerCmds;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SelectSub extends AbstractPlayerSub {

    @Getter private final String IDENTIFIER = "select";

    public SelectSub(DeathHolos main) {
        super(main, null, 2, 2);
    }

    // /dh select [holo_name]
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, messageManager.get(Message.MUSTBEPLAYERERROR));
            return;
        }

        if (!checkRequirements(sender, args, label)) return;

        Player player = (Player) sender;
        HologramGroup newGroup = validateGroup(player, args[1]);
        if (newGroup == null) return;

        if (groupAlreadyActive(player, newGroup)) return;

        if (!hasAccess(player, newGroup)) return;

        dataManager.updatePlayer(player.getUniqueId(), newGroup);
        sendMessage(sender, messageManager.getWithPlaceholder(Message.SUCCESSHOLOSELECTED, newGroup.getName(), false));
    }

    private HologramGroup validateGroup(CommandSender sender, String groupName) {
        HologramGroup group = groupManager.getGroup(groupName);

        if (group == null) sendMessage(sender, messageManager.get(Message.INVALIDHOLOGROUP));

        return group;
    }

    private boolean groupAlreadyActive(Player player, HologramGroup newGroup) {
        HologramGroup currentGroup = dataManager.getPlayer(player.getUniqueId());
        if (currentGroup == null) return false;

        if (currentGroup == newGroup) sendMessage(player, messageManager.getWithPlaceholder(Message.ACTIVEHOLOSELECTED, newGroup.getName(), false));

        return currentGroup == newGroup;
    }

    private boolean hasAccess(Player player, HologramGroup group) {
        if (!player.hasPermission(group.getPermission())) {
            sendMessage(player, messageManager.get(Message.NOPERMHOLOSELECTED));
            return false;
        }

        return true;
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        return new ArrayList<>();
    }
}
