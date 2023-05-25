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
        if (cmd.getName().equalsIgnoreCase("saldo")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Questo comando può essere eseguito solo da un giocatore!");
                return true;
            }

            Player player = (Player) sender;
            double balance = economyManager.getBalance(player);
            player.sendMessage(ChatColor.GREEN + "Il tuo saldo è: " + balance);
            return true;
        }

        return false;
    }
}
