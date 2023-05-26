package my.roleplay.mod.roleplaymod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModificaStatsCommand implements CommandExecutor {
    private final StatsManager statsManager;

    public ModificaStatsCommand(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
        	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("op-only-command"));
            return true;
        }

        if (args.length != 3) {
        	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("correct-usage-editstats"));
            return true;
        }

        String playerName = args[0];
        String statName = args[1];
        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
        	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("int-amount"));
            return true;
        }

        int currentStat = statsManager.getStat(playerName, statName);
        int newStat = currentStat + amount;
        statsManager.setStat(playerName, statName, newStat);

        sender.sendMessage(RoleplayMod.getInstance().getMessage("stats-string") + statName + RoleplayMod.getInstance().getMessage("stats-string2") + playerName + RoleplayMod.getInstance().getMessage("stats-string3") + newStat);

        return true;
    }
}
