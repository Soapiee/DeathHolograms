package me.soapiee.deathholos.commands.adminCmds;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.utils.Message;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;
import java.util.List;

public class VersionSub extends AbstractAdminSub {

    private final String IDENTIFIER = "version";

    public VersionSub(DeathHolos main) {
        super(main, null, 1, 1);
    }

    // /tf version
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!checkRequirements(sender, args, label)) return;

        sendMessage(sender, messageManager.getWithPlaceholder(Message.ADMINVERSION, Utils.PLUGIN_VERSION, (sender instanceof ConsoleCommandSender)));
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        return new ArrayList<>();
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
