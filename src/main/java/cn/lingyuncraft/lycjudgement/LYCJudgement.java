package cn.xfcraft.judgement;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class XFCraftJudgement extends JavaPlugin {

    private static XFCraftJudgement instance;

    public static String ban_command;
    public static double vote_up_player_percent;
    public static String player_is_invalid;
    public static String broadcast;
    public static String ban_success;
    public static String vote_success;
    public static String player_is_not_being_voted;
    public static String player_is_already_being_voted;
    public static String already_voted;
    public static long vote_timed_out;

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        ban_command = getConfig().getString("ban-command");
        vote_up_player_percent = getConfig().getDouble("vote-up-player-percent");
        player_is_invalid = getConfig().getString("lang.player-is-invalid");
        broadcast = getConfig().getString("lang.broadcast");
        ban_success = getConfig().getString("lang.ban-success");
        vote_success = getConfig().getString("lang.vote-success");
        player_is_not_being_voted = getConfig().getString("lang.player-is-not-being-voted");
        player_is_already_being_voted = getConfig().getString("lang.player-is-already-being-voted");
        already_voted = getConfig().getString("lang.already-voted");
        vote_timed_out = getConfig().getLong("vote-timed-out");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        instance = this;
        reloadConfig();
        final PluginCommand command = Bukkit.getPluginCommand("judgement");
        assert command != null;
        command.setExecutor(new CommandExec());
    }

    public static XFCraftJudgement getInstance() {
        return instance;
    }
}
