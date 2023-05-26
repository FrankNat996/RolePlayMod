package my.roleplay.mod.roleplaymod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Lega implements CommandExecutor {
    private static final Set<UUID> boundPlayers = new HashSet<>();

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
        	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("cant-tie-yourself"));
            return true;
        }
        
        if (Lega.isPlayerBound(player)) {
        	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("cant-tie-if-u-tied"));
        	return true;
        }

        if (player.getLocation().distance(target.getLocation()) > 2 && !player.isOp()) {
        	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("tie-too-far"));
            return true;
        }
        
        if (Lega.isPlayerBound(target)) {
        	player.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("already-untied"));
            return true;
        }

        // Esegui il legame del giocatore qui
        boundPlayers.add(target.getUniqueId());
        target.setWalkSpeed(0f);
        target.setGameMode(GameMode.ADVENTURE);
        target.setInvulnerable(true);
        target.setCollidable(false);
        target.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(-50);
        player.sendMessage(RoleplayMod.getInstance().getMessage("u-tied") + target.getName() + ".");
        return true;
    }

    public static boolean isPlayerBound(Player player) {
        return boundPlayers.contains(player.getUniqueId());
    }

    public static void unbindPlayer(Player player) {
        boundPlayers.remove(player.getUniqueId());
    }
}
