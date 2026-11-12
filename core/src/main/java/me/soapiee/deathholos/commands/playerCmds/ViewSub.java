package me.soapiee.deathholos.commands.playerCmds;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.commands.adminCmds.AbstractAdminSub;
import me.soapiee.deathholos.utils.Message;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ViewSub extends AbstractAdminSub {

    @Getter private final String IDENTIFIER = "view";

    public ViewSub(DeathHolos main) {
        super(main, null, 1, 2);
    }

    // /dh view [player]
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, Utils.addColour(messageManager.get(Message.MUSTBEPLAYERERROR)));
            return;
        }

        if (!checkRequirements(sender, args, label)) return;

        Player target = (args.length == 2) ? getTarget(sender, args[1]) : (Player) sender;
        if (target == null) return;


        //TODO: Add logic to find out the players hologram

    }

    private Player getTarget(CommandSender sender, String string) {
        Player target = Bukkit.getPlayer(string);

        if (target == null) {
            sendMessage(sender, Utils.addColour(messageManager.get(Message.PLAYERNOTFOUND)));

        }

        return target;
    }




    @Override
    public List<String> getTabCompletions(String[] args) {
        return new ArrayList<>();
    }
}
