package my.roleplay.mod.roleplaymod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PaymentCommand implements CommandExecutor {
    private EconomyManager economyManager;

    public PaymentCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("paga")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Questo comando può essere eseguito solo da un giocatore!");
                return true;
            }

            Player player = (Player) sender;

            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Utilizzo corretto: /paga <nome_giocatore> <somma>");
                return true;
            }

            String receiverName = args[0];
            double amount;

            try {
                amount = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "La somma specificata non è valida.");
                return true;
            }

            if (amount <= 0) {
                player.sendMessage(ChatColor.RED + "La somma deve essere maggiore di zero.");
                return true;
            }

            Player receiver = player.getServer().getPlayer(receiverName);

            if (receiver == null) {
                player.sendMessage(ChatColor.RED + "Il giocatore specificato non è online.");
                return true;
            }

            if (!economyManager.hasEnoughBalance(player, amount)) {
                player.sendMessage(ChatColor.RED + "Non hai abbastanza monete per effettuare questo pagamento.");
                return true;
            }

            economyManager.withdraw(player, amount);
            economyManager.deposit(receiver, amount);

            player.sendMessage(ChatColor.GREEN + "Hai pagato " + receiver.getName() + " " + amount + " monete.");
            receiver.sendMessage(ChatColor.GREEN + "Hai ricevuto un pagamento di " + amount + " monete da " + player.getName() + ".");

            return true;
        }

        return false;
    }
}
