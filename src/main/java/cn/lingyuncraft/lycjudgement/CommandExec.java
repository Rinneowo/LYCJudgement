package cn.lingyuncraft.lycjudgement;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandExec implements CommandExecutor {

    Map<String, Integer> counter = new HashMap<>();
    Map<String, String> reason = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("judgement")) {
            if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("kick")) {
                    if (args.length == 3) {
                        Player player = Bukkit.getPlayer(args[1]);
                        if (player != null) {
                            if (counter.containsKey(player.getName())) {
                                sender.sendMessage(LYCJudgement.player_is_already_being_voted);
                            } else {
                                counter.put(player.getName(), 0);
                                reason.put(player.getName(), args[2]);
                                Bukkit.broadcastMessage(LYCJudgement.broadcast.replace("{player}", args[1]).replace("{reason}", args[2]));
                            }
                            return true;
                        } else {
                            sender.sendMessage(LYCJudgement.player_is_invalid);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("vote")) {
                    if (args.length == 2) {
                        if (counter.containsKey(args[1])) {
                            counter.put(args[1], counter.get(args[1]) + 1);
                            sender.sendMessage(LYCJudgement.vote_success.replace("{current}", String.valueOf(counter.get(args[1]))).replace("{max}", String.valueOf(Bukkit.getOnlinePlayers().size())));
                            if (counter.get(args[1]) >= Bukkit.getOnlinePlayers().size() * LYCJudgement.vote_up_player_percent) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), LYCJudgement.ban_command.replace("{player}", args[1]).replace("{reason}", reason.get(args[1])));
                                counter.remove(args[1]);
                                reason.remove(args[1]);
                                Bukkit.broadcastMessage(LYCJudgement.ban_success.replace("{player}", args[1]));
                            }
                        } else {
                            sender.sendMessage(LYCJudgement.player_is_not_being_voted);
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }
}