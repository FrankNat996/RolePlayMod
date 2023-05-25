package my.roleplay.mod.roleplaymod;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BotCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (!(sender instanceof Player)) {
	        sender.sendMessage("Questo comando può essere eseguito solo da un giocatore.");
	        return true;
	    }

	    Player player = (Player) sender;

	    if (args.length == 0) {
	        player.sendMessage("Utilizzo corretto: /bot <testo>");
	        return true;
	    }

	    String botText = String.join(" ", args); // Unisci tutti gli argomenti del comando in un'unica stringa
	    RoleplayMod plugin = RoleplayMod.getInstance();

	    /*if (plugin.getActiveBots().containsKey(player)) {
	        player.sendMessage("Hai già un bot attivo.");
	        return true;
	    }*/

	    CharacterBot bot = new CharacterBot(botText, player);
	    plugin.getActiveBots().put(player, bot);

	    //player.sendMessage("Il bot è stato attivato con il testo: " + botText);

	    return true;
	}

}
