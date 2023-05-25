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
        if (cmd.getName().equalsIgnoreCase("visualizzasaldo")) {
            if (!sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "Questo comando può essere eseguito solo dagli operatori!");
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Utilizzo corretto: /visualizzasaldo <nome_giocatore>");
                return true;
            }

            String playerName = args[0];
            Player targetPlayer = sender.getServer().getPlayer(playerName);

            if (targetPlayer == null) {
                sender.sendMessage(ChatColor.RED + "Il giocatore specificato non è online.");
                return true;
            }

            double balance = economyManager.getBalance(targetPlayer);
            sender.sendMessage(ChatColor.YELLOW + "Il saldo di " + targetPlayer.getName() + " è: " + balance + " monete.");

            return true;
        }

        return false;
    }
}
