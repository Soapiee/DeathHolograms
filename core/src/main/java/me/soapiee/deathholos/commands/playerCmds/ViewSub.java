package me.soapiee.deathholos.commands.playerCmds;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.Message;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ViewSub extends AbstractPlayerSub {

    @Getter private final String IDENTIFIER = "view";

    public ViewSub(DeathHolos main) {
        super(main, "deathholos.player.others", 1, 2);
    }

    // /dh view [player]
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 1 && sender instanceof ConsoleCommandSender) {
            sendMessage(sender, Utils.addColour(messageManager.get(Message.MUSTBEPLAYERERROR)));
            return;
        }

        if (!checkRequirements(sender, args, label)) return;

        Player target = (args.length == 2) ? getTarget(sender, args[1]) : (Player) sender;
        if (target == null) return;

        HologramGroup group = getGroup(sender, target);
        if (group == null) return;

        sendMessage(sender, Utils.addColour(messageManager.getWithPlaceholder(Message.VIEWHOLO, target.getName(), group.getName(), (sender instanceof ConsoleCommandSender))));
    }

    private Player getTarget(CommandSender sender, String string) {
        Player target = Bukkit.getPlayer(string);

        if (target == null) sendMessage(sender, Utils.addColour(messageManager.get(Message.PLAYERNOTFOUND)));

        return target;
    }

    private HologramGroup getGroup(CommandSender sender, Player target) {
        if (configManager.isPrioritySystem()) return getPriorityGroup(target);
        else return getSelectorGroup(sender, target);
    }

    private HologramGroup getPriorityGroup(Player target) {
        HologramGroup group = hologramManager.getDefaultGroup();
        int lowestPriority = group.getPriority();

        for (int i = 0; i < lowestPriority; i++) {
            HologramGroup holoGroup = hologramManager.getGroup(i);
            if (holoGroup == null) continue;

            if (target.hasPermission(holoGroup.getPermission())) return holoGroup;
        }

        return group;
    }

    private HologramGroup getSelectorGroup(CommandSender sender, OfflinePlayer target) {
//        if (!target.isOnline()) {
//            sendMessage(sender, Utils.addColour(messageManager.getWithPlaceholder(Message.OFFLINEVIEWHOLO, target.getName(), (sender instanceof ConsoleCommandSender))));
//            return null;
//        }

        HologramGroup group = selectorCache.getGroup(target.getUniqueId());

        if (group == null)
            sendMessage(sender, Utils.addColour(messageManager.getWithPlaceholder(Message.ERRORVIEWHOLO, target.getName(), (sender instanceof ConsoleCommandSender))));

        return group;
    }


    @Override
    public List<String> getTabCompletions(String[] args) {
        return new ArrayList<>();
    }
}
