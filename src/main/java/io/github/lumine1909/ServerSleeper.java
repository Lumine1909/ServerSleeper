package io.github.lumine1909;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class ServerSleeper extends JavaPlugin {
    public static ServerSleeper instance;
    private final WatchdogHandler watchdogHandler = new WatchdogHandler();
    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage(ChatColor.RED + "Incorrect usage! Please use: /sleep <milliseconds> [killWatchDog: true|false]");
            return true;
        }
        try {
            long millis = Long.parseLong(args[0]);
            if (args.length == 2 && !args[1].equals("true") && !args[1].equals("false")) {
                sender.sendMessage(ChatColor.RED + "Incorrect usage! Please use: /sleep <milliseconds> [killWatchDog: true|false]");
                return true;
            }
            boolean killWatchdog = args.length != 1 && args[1].equals("true");
            sender.sendMessage(ChatColor.GREEN + "Successful! Server will sleep for " + millis + " milliseconds");
            Bukkit.getScheduler().runTask(this, () -> {
                try {
                    if (killWatchdog) {
                        watchdogHandler.disable();
                    }
                    Thread.sleep(millis);
                    if (killWatchdog) {
                        watchdogHandler.enable();
                    }
                    sender.sendMessage(ChatColor.GREEN + "The server just woke up!");
                } catch (InterruptedException ignored) {
                }
            });
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Incorrect usage! Please use: /sleep <milliseconds> [killWatchDog: true|false]");
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return args.length == 2 ? List.of("true", "false") : Collections.emptyList();
    }
}
