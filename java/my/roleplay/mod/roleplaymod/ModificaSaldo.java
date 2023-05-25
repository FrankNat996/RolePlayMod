package my.roleplay.mod.roleplaymod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModificaSaldo implements CommandExecutor {

    private EconomyManager economyManager;

    public ModificaSaldo(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("modificasaldo")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Questo comando può essere eseguito solo da un giocatore.");
                return true;
            }

            Player player = (Player) sender;

            // Controllo se il giocatore è un operatore
            if (!player.isOp()) {
                player.sendMessage(ChatColor.RED + "Devi essere un operatore per utilizzare questo comando.");
                return true;
            }

            // Controllo se sono stati specificati i parametri corretti
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Utilizzo corretto: /modificasaldo <nome_giocatore> <quantità>");
                return true;
            }

            String targetPlayerName = args[0];
            double amount;

            try {
                amount = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "La quantità specificata non è valida.");
                return true;
            }

            // Controllo se il giocatore esiste
            Player targetPlayer = player.getServer().getPlayer(targetPlayerName);
            if (targetPlayer == null) {
                player.sendMessage(ChatColor.RED + "Giocatore non trovato.");
                return true;
            }

            // Aggiungo o rimuovo il saldo
            if (amount >= 0) {
                economyManager.deposit(targetPlayer, amount);
                player.sendMessage(ChatColor.GREEN + "Aggiunto " + amount + " al saldo di " + targetPlayerName + ".");
            } else {
                economyManager.withdraw(targetPlayer, -amount);
                player.sendMessage(ChatColor.GREEN + "Rimosso " + (-amount) + " dal saldo di " + targetPlayerName + ".");
            }

            return true;
        }

        return false;
    }
}
