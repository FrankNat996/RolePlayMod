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
        if (cmd.getName().equalsIgnoreCase("editbalance")) {
            if (!(sender instanceof Player)) {
            	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("player-only-command"));
                return true;
            }

            Player player = (Player) sender;

            // Controllo se il giocatore è un operatore
            if (!player.isOp()) {
            	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("op-only-command"));
                return true;
            }

            // Controllo se sono stati specificati i parametri corretti
            if (args.length < 2) {
            	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("correct-usage-editbalance"));
                return true;
            }

            String targetPlayerName = args[0];
            double amount;

            try {
                amount = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
            	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("wrong-amount-editbalance"));
                return true;
            }

            // Controllo se il giocatore esiste
            Player targetPlayer = player.getServer().getPlayer(targetPlayerName);
            if (targetPlayer == null) {
            	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("not-online"));
                return true;
            }

            // Aggiungo o rimuovo il saldo
            if (amount >= 0) {
                economyManager.deposit(targetPlayer, amount);
                player.sendMessage(ChatColor.GREEN + RoleplayMod.getInstance().getMessage("added") + amount + RoleplayMod.getInstance().getMessage("added-to") + targetPlayerName + ".");
            } else {
                economyManager.withdraw(targetPlayer, -amount);
                player.sendMessage(ChatColor.GREEN + RoleplayMod.getInstance().getMessage("removed") + (-amount) + RoleplayMod.getInstance().getMessage("removed-to") + targetPlayerName + ".");
            }

            return true;
        }

        return false;
    }
}
