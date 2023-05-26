package my.roleplay.mod.roleplaymod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Saldo implements CommandExecutor {
    private EconomyManager economyManager;

    public Saldo(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("balance")) {
            if (!(sender instanceof Player)) {
            	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("player-only-command"));
                return true;
            }

            Player player = (Player) sender;
            double balance = economyManager.getBalance(player);
            player.sendMessage(ChatColor.GREEN + RoleplayMod.getInstance().getMessage("your-balance") + balance);
            return true;
        }

        return false;
    }
}
