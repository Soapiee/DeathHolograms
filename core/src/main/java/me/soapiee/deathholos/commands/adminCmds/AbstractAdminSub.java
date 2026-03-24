package me.soapiee.deathholos.commands.adminCmds;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.commands.SubCmd;
import me.soapiee.deathholos.internals.HologramHandler;
import me.soapiee.deathholos.managers.ConfigManager;
import me.soapiee.deathholos.managers.HologramManager;
import me.soapiee.deathholos.managers.MessageManager;
import me.soapiee.deathholos.utils.Message;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractAdminSub implements SubCmd {

    protected final DeathHolos main;
    protected final MessageManager messageManager;
    protected final HologramHandler hologramHandler;
    protected final ConfigManager configManager;
    protected final HologramManager hologramManager;

    protected final String PERMISSION;
    protected final int MIN_ARGS;
    protected final int MAX_ARGS;

    public AbstractAdminSub(DeathHolos main, String PERMISSION, int MIN_ARGS, int MAX_ARGS) {
        this.main = main;
        messageManager = main.getMessageManager();
        hologramHandler = main.getInternalsManager().getHologramHandler();
        configManager = main.getConfigManager();
        hologramManager = main.getHoloManager();

        this.PERMISSION = PERMISSION;
        this.MIN_ARGS = MIN_ARGS;
        this.MAX_ARGS = MAX_ARGS;
    }

    public boolean checkRequirements(CommandSender sender, String[] args, String label) {
        if (!checkPermission(sender, PERMISSION)) {
            sendMessage(sender, messageManager.get(Message.NOPERMISSION));
            return false;
        }

        if (!checkArgs(args)) {
            sendMessage(sender, messageManager.getWithPlaceholder(Message.ADMINHELP, label, (sender instanceof ConsoleCommandSender)));
            return false;
        }

        return true;
    }

    protected boolean checkPermission(CommandSender sender, String permission){
        if (permission == null) return true;
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        return player.hasPermission(permission);
    }

    private boolean checkArgs(String[] args){
        if (MIN_ARGS == -1 && MAX_ARGS == -1) return true;

        if (args.length < MIN_ARGS) return false;
        return !(args.length > MAX_ARGS);
    }

    protected void sendMessage(CommandSender sender, String message){
        if (message == null) return;

        if (sender instanceof Player) sender.sendMessage(Utils.addColour(message));
        else Utils.consoleMsg(message);
    }
}
