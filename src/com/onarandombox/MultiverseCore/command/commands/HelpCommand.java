// This file is no longer licensed under that silly CC license. I have blanked it out and will start implementaiton of my own in a few days. For now there is no help.
package com.onarandombox.MultiverseCore.command.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.pneumaticraft.commandhandler.Command;

public class HelpCommand extends Command {
    private static final int CMDS_PER_PAGE = 10;

    public HelpCommand(JavaPlugin plugin) {
        super(plugin);
        this.commandName = "Get Help with Multiverse";
        this.commandDesc = "Displays a nice help menu";
        this.commandUsage = "/mv " + ChatColor.GOLD + "[PAGE #]";
        this.minimumArgLength = 0;
        this.maximumArgLength = 1;
        this.commandKeys.add("mv");
        this.commandKeys.add("mvhelp");
        this.commandKeys.add("mv help");
        this.permission = "multiverse.help";
        this.opRequired = false;
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        int page = 1;
        if (args.size() == 1) {
            try {
                page = Integer.parseInt(args.get(0));
            } catch (NumberFormatException e) {
            }
        }

        List<Command> availableCommands = ((MultiverseCore) this.plugin).getCommandHandler().getCommands(sender);
        int totalPages = (int) Math.ceil(availableCommands.size() / ( CMDS_PER_PAGE + 0.0));

        if (page > totalPages) {
            page = totalPages;
        }

        sender.sendMessage(ChatColor.AQUA + "====[ Multiverse Help ]====");
        sender.sendMessage(ChatColor.AQUA + " Page " + page + " of " + totalPages);
        this.showPage(page, sender, availableCommands);

    }

    private void showPage(int page, CommandSender sender, List<Command> cmds) {
        int start = (page - 1) * CMDS_PER_PAGE;
        int end = start + CMDS_PER_PAGE;
        for (int i = start; i < cmds.size() && i < end; i++) {
            sender.sendMessage(ChatColor.AQUA + cmds.get(i).getCommandUsage());
        }
    }

}
