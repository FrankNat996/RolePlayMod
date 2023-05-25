package my.roleplay.mod.roleplaymod;

// comando ME in chat
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Me implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (args.length >= 1) {
				String message = String.join(" ", args); // Unisce tutti gli argomenti del comando in un unico messaggio
				String formattedMessage = ChatColor.GREEN + "*" + player.getName() +" " + message +"*"; // Aggiunge il colore verde al messaggio

				player.sendMessage(formattedMessage); // Invia il messaggio al giocatore

				// Ottieni il mondo in cui si trova il giocatore
				World world = player.getWorld();

				// Ottieni la posizione del giocatore
				Location playerLocation = player.getLocation();

				// Definisci la distanza massima per considerare le entità come "vicine"
				double distance = 10; // Puoi modificare questo valore secondo le tue esigenze

				// Invia il messaggio alle entità (giocatori) vicine
				for (Entity entity : world.getNearbyEntities(playerLocation, distance, distance, distance)) {
					if (entity instanceof Player) {
						Player otherPlayer = (Player) entity;
						if (!otherPlayer.equals(player)) {
							otherPlayer.sendMessage(formattedMessage);
						}
					}
				}

				return true;
			} else {
				command.setUsage("/me <testo>");
			}
		}

		return false;
	}
}

/*// Ottieni il chunk in cui si trova il giocatore
Chunk playerChunk = player.getLocation().getChunk();

// Invia il messaggio agli altri giocatori nello stesso chunk
for (org.bukkit.entity.Entity entity : playerChunk.getEntities()) {
    if (entity instanceof Player) {
        Player otherPlayer = (Player) entity;
        if (!otherPlayer.equals(player)) {
            otherPlayer.sendMessage(formattedMessage);
        }
    }
}*/