package me.soapiee.deathholos.commands;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.commands.playerCmds.SelectSub;
import me.soapiee.deathholos.commands.playerCmds.ViewSub;
import me.soapiee.deathholos.managers.HologramManager;
import me.soapiee.deathholos.managers.MessageManager;
import me.soapiee.deathholos.utils.Message;
import me.soapiee.deathholos.utils.Utils;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerCmd implements CommandExecutor, TabCompleter {

    private final MessageManager messageManager;
    private final HologramManager hologramManager;

//    private final String PERMISSION = "deathholos.player";
    private final Map<String, SubCmd> subCommands = new HashMap<>();

    public PlayerCmd(DeathHolos main) {
        messageManager = main.getMessageManager();
        hologramManager = main.getHoloManager();

        register(new ViewSub(main));
        if (!main.getConfigManager().isPrioritySystem()) register(new SelectSub(main));
    }

    private void register(SubCmd cmd) {
        subCommands.put(cmd.getIDENTIFIER(), cmd);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof CommandBlock) return true;
//        if (!hasPermission(sender)) return true;

        if (args.length == 0) {
            //TODO:
            sendMessage(sender, messageManager.getWithPlaceholder(Message.PLAYERHELP, "dh", (sender instanceof ConsoleCommandSender)));
            return true;
        }

        SubCmd cmd = subCommands.get(args[0]);
        if (cmd == null) {
            sendMessage(sender, messageManager.getWithPlaceholder(Message.PLAYERHELP, label, (sender instanceof ConsoleCommandSender)));
            return true;
        }

        cmd.execute(sender, label, args);
        return true;
    }

    private void sendMessage(CommandSender sender, String message) {
        if (message == null) return;

        if (sender instanceof Player) sender.sendMessage(Utils.addColour(message));
        else Utils.consoleMsg(message);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        final List<String> results = new ArrayList<>();

        switch (args.length) {
            case 1:
                results.add("help");
                results.add("select");
//                results.add("view");
                break;

            case 2:
                if (args[0].equalsIgnoreCase("select")) {
                    hologramManager.getGroups().forEach(group -> results.add(group.getName()));
                    break;
                }

                break;

        }
        return results.stream()
                .filter(completion -> completion.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}
