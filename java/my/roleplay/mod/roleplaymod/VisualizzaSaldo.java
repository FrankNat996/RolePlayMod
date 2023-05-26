package my.roleplay.mod.roleplaymod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VisualizzaSaldo implements CommandExecutor {
    private EconomyManager economyManager;

    public VisualizzaSaldo(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("showbalance")) {
            if (!sender.isOp()) {
                sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("op-only-command"));
                return true;
            }

            if (args.length != 1) {
            	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("correct-usage-show-player-balance"));
                return true;
            }

            String playerName = args[0];
            Player targetPlayer = sender.getServer().getPlayer(playerName);

            if (targetPlayer == null) {
            	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("not-online"));
                return true;
            }

            double balance = economyManager.getBalance(targetPlayer);
            sender.sendMessage(ChatColor.YELLOW + RoleplayMod.getInstance().getMessage("player-balance") + targetPlayer.getName() + RoleplayMod.getInstance().getMessage("is") + balance + RoleplayMod.getInstance().getMessage("money"));

            return true;
        }

        return false;
    }
}
