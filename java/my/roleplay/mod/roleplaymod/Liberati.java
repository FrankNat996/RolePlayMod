package my.roleplay.mod.roleplaymod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Liberati implements CommandExecutor {
    private Map<Player, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN = 30000; // Tempo di attesa in millisecondi (30 secondi)

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
        	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("player-only-command"));
            return true;
        }

        Player player = (Player) sender;

        // Verifica il tempo di attesa
        long currentTime = System.currentTimeMillis();
        if (cooldowns.containsKey(player)) {
            long lastExecutionTime = cooldowns.get(player);
            long timeLeft = (COOLDOWN - (currentTime - lastExecutionTime)) / 1000; // Tempo rimanente in secondi
            if (currentTime - lastExecutionTime < COOLDOWN) {
                player.sendMessage(RoleplayMod.getInstance().getMessage("need-wait") + timeLeft + RoleplayMod.getInstance().getMessage("need-wait2"));
                return true;
            }
        }

        // Genera un numero casuale tra 0 e 99
        Random random = new Random();
        int successPercentage = 10; // Percentuale di successo (5% nel tuo esempio)
        int randomNumber = random.nextInt(100);

        /*if (!Lega.isPlayerBound(player)) {
            player.sendMessage("Non sei legato.");
            return true;
        }*/

        if (randomNumber < successPercentage) {
            // Rimuovi il legame del giocatore utilizzando PlayerBinding
            Lega.unbindPlayer(player);
            player.setWalkSpeed(0.2f);
            player.sendMessage(ChatColor.GREEN + RoleplayMod.getInstance().getMessage("free"));
        } else {
        	player.sendMessage(ChatColor.DARK_PURPLE + RoleplayMod.getInstance().getMessage("not-free"));
        }

        cooldowns.put(player, currentTime); // Aggiorna il tempo dell'ultima esecuzione per il giocatore

        return true;
    }
}
