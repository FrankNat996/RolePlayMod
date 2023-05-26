package my.roleplay.mod.roleplaymod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Slega implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
        	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("player-only-command"));
            return true;
        }

        Player player = (Player) sender;

        // Verifica la sintassi del comando
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("correct-usage-tie"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
        	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("not-online"));
            return true;
        }

        if (target.equals(player) && !player.isOp()) {
        	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("cant-untie-yourself"));
            return true;
        }

        if (player.getLocation().distance(target.getLocation()) > 2 && !player.isOp()) {
        	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("untie-too-far"));
            return true;
        }

        // Rimuovi il legame del giocatore
        if (!Lega.isPlayerBound(target)) {
        	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("not-tie"));
            return true;
        }

        Lega.unbindPlayer(target);
        target.setWalkSpeed(0.2f);
        target.setGameMode(GameMode.SURVIVAL);
        target.setInvulnerable(false);
        target.setCollidable(true);
        target.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
        player.sendMessage(RoleplayMod.getInstance().getMessage("u-untied") + target.getName() + ".");

        return true;
    }
}
