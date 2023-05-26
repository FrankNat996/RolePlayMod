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
        if (cmd.getName().equalsIgnoreCase("pay")) {
            if (!(sender instanceof Player)) {
            	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("player-only-command"));
                return true;
            }

            Player player = (Player) sender;

            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("correct-usage-pay"));
                return true;
            }

            String receiverName = args[0];
            double amount;

            try {
                amount = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
            	 player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("wrong-pay-amount"));
                return true;
            }

            if (amount <= 0) {
            	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("paymorezero"));
                return true;
            }

            Player receiver = player.getServer().getPlayer(receiverName);

            if (receiver == null) {
            	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("not-online"));
                return true;
            }

            if (!economyManager.hasEnoughBalance(player, amount)) {
            	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("not-enough-money"));
                return true;
            }

            economyManager.withdraw(player, amount);
            economyManager.deposit(receiver, amount);

            player.sendMessage(ChatColor.GREEN + RoleplayMod.getInstance().getMessage("have-payed") + receiver.getName() + " " + amount + " " + RoleplayMod.getInstance().getMessage("money"));
            receiver.sendMessage(ChatColor.GREEN + RoleplayMod.getInstance().getMessage("received-pay") + amount + RoleplayMod.getInstance().getMessage("money-from") + player.getName() + ".");

            return true;
        }

        return false;
    }
}
