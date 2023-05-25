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
                sender.sendMessage(ChatColor.RED + "Questo comando può essere eseguito solo da un giocatore!");
                return true;
            }

            Player player = (Player) sender;
            player.sendMessage(ChatColor.GREEN + "RolePlayMod.");
            player.sendMessage(ChatColor.GREEN + "Plugin di FrankNat in fase di sviluppo.");
            player.sendMessage(ChatColor.YELLOW + "Versione 1.0");
            return true;
        }

        return false;
    }

}



/*public class Me implements CommandExecutor {

@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
        Player player = (Player) sender;
        
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("testo")) {
                String message = ChatColor.translateAlternateColorCodes('&', args[1]); // Color the message
                
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendTitle("", message); // Display the message on players' heads
                }
                
                player.sendMessage("Il testo è stato visualizzato sulla testa dei personaggi.");
                return true;
            }
        }
    }
    
    return false;
}
}*/
