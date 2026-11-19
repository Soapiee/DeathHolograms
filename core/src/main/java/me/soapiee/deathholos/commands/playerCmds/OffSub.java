package me.soapiee.deathholos.commands.playerCmds;

import lombok.Getter;
import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.utils.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OffSub extends AbstractPlayerSub {

    @Getter private final String IDENTIFIER = "off";

    public OffSub(DeathHolos main) {
        super(main, null, 1, 1);
    }

    // /dh off
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, messageManager.get(Message.MUSTBEPLAYERERROR));
            return;
        }

        if (!checkRequirements(sender, args, label)) return;

        Player player = (Player) sender;

        dataManager.updatePlayer(player.getUniqueId(), null);
        sendMessage(sender, messageManager.get(Message.HOLOOFF));
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        return new ArrayList<>();
    }
}
