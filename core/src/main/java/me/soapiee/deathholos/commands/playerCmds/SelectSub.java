package me.soapiee.deathholos.commands.playerCmds;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.commands.adminCmds.AbstractAdminSub;
import me.soapiee.deathholos.logic.HologramGroup;
import me.soapiee.deathholos.utils.Message;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SelectSub extends AbstractAdminSub {

    @Getter private final String IDENTIFIER = "select";

    public SelectSub(DeathHolos main) {
        super(main, null, 2, 2);
    }

    // /dh select [holo_name]
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, Utils.addColour(messageManager.get(Message.MUSTBEPLAYERERROR)));
            return;
        }

        Player player = (Player) sender;

        HologramGroup group = validateGroup(player, args[1]);
        if (group == null) return;

        if (!hasAccess(player, group)) return;

        //TODO: Update the death hologram group to the player
    }

    private HologramGroup validateGroup(CommandSender sender, String groupName) {
        HologramGroup group = hologramManager.getGroup(groupName);

        if (group == null) sendMessage(sender, messageManager.get(Message.INVALIDHOLOGROUP));

        return group;
    }

    private boolean hasAccess(Player player, HologramGroup group) {
        //TODO: If player has access to that group

        sendMessage(player, Utils.addColour(messageManager.get(Message.NOPERMISSIONHOLO)));


        return true;
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        return new ArrayList<>();
    }
}
