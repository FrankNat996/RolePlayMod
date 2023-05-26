package my.roleplay.mod.roleplaymod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CercaInventario implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                String targetPlayerName = args[0];
                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                if (targetPlayer != null) {
                    Location playerLocation = player.getLocation();
                    Location targetPlayerLocation = targetPlayer.getLocation();

                    if (playerLocation.distance(targetPlayerLocation) <= 2) {
                        // Verifica se il giocatore è legato
                        if (Lega.isPlayerBound(targetPlayer)) {
                            // Apri l'inventario del giocatore
                        	Inventory targetInventory = targetPlayer.getInventory();
                            player.openInventory(targetInventory);
                        } else {
                            player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("not-tie-inventory"));
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("too-far"));
                    }
                } else {
                    player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("not-online"));
                }
            } else {
                player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("correct-usage-searchinventory"));
            }
        } else {
            sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("player-only-command"));
        }

        return true;
    }
}
