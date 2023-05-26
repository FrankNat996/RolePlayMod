package my.roleplay.mod.roleplaymod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Info implements CommandExecutor {

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("info")) { // Comando /info
            if (!(sender instanceof Player)) {
            	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("player-only-command"));
                return true;
            }

            Player player = (Player) sender;
            player.sendMessage(ChatColor.GREEN + "RolePlayMod.");
            player.sendMessage(ChatColor.GREEN + "Plugin by FrankNat.");
            player.sendMessage(ChatColor.YELLOW + "Version 1.0");
            return true;
        }

        return false;
    }

}