package me.soapiee.deathholos.commands.playerCmds;

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

public class ViewSub extends AbstractPlayerSub {

    @Getter private final String IDENTIFIER = "view";

    public ViewSub(DeathHolos main) {
        super(main, null, 1, 2);
    }

    // /dh view [player]
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 1 && sender instanceof ConsoleCommandSender) {
            sendMessage(sender, messageManager.get(Message.MUSTBEPLAYERERROR));
            return;
        }

        if (args.length == 2 && sender instanceof Player) {
            if (!checkPermission(sender, "deathholos.player.others")) {
                sendMessage(sender, messageManager.get(Message.NOPERMISSION));
                return;
            }
        }

        if (!checkRequirements(sender, args, label)) return;

        Player target = (args.length == 2) ? getTarget(sender, args[1]) : (Player) sender;
        if (target == null) return;

        HologramGroup group = getGroup(sender, target);
        if (group == null) return;

        if (sender instanceof Player && (target.getName().equalsIgnoreCase(sender.getName())))
            sendMessage(sender, messageManager.getWithPlaceholder(Message.VIEWHOLO, group.getName(), (sender instanceof ConsoleCommandSender)));
        else
            sendMessage(sender, messageManager.getWithPlaceholder(
                    Message.VIEWHOLOOTHER, target.getName(), group.getName(), (sender instanceof ConsoleCommandSender)));
    }

    private Player getTarget(CommandSender sender, String string) {
        //TODO: Change to my own player cache
        Player target = Bukkit.getPlayer(string);

        if (target == null) sendMessage(sender, messageManager.get(Message.PLAYERNOTFOUND));

        return target;
    }

    private HologramGroup getGroup(CommandSender sender, Player target) {
        HologramGroup group = dataManager.getPlayer(target.getUniqueId());

        if (group == null) {
            if (sender instanceof Player && (target.getName().equalsIgnoreCase(sender.getName())))
                sendMessage(sender, messageManager.get(Message.ERRORVIEWHOLO));
            else
                sendMessage(sender, messageManager.getWithPlaceholder(Message.ERRORVIEWHOLOOTHER, target.getName(), (sender instanceof ConsoleCommandSender)));
        }

        return group;
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        return new ArrayList<>();
    }
}
