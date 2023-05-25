package my.roleplay.mod.roleplaymod;

import org.bukkit.Bukkit;
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
            sender.sendMessage("Questo comando può essere eseguito solo da un giocatore.");
            return true;
        }

        Player player = (Player) sender;

        // Verifica la sintassi del comando
        if (args.length != 1) {
            player.sendMessage("Utilizzo corretto: /lega <nome_player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("Giocatore non trovato.");
            return true;
        }

        if (target.equals(player) && !player.isOp()) {
            player.sendMessage("Non puoi legare te stesso.");
            return true;
        }
        
        if (Lega.isPlayerBound(player)) {
        	player.sendMessage("Non puoi legare qualcuno se sei gia legato, rincoglionito!");
        	return true;
        }

        if (player.getLocation().distance(target.getLocation()) > 2 && !player.isOp()) {
            player.sendMessage("Devi essere a massimo 2 blocchi di distanza per legare il giocatore.");
            return true;
        }
        
        if (Lega.isPlayerBound(target)) {
            player.sendMessage("Il giocatore è gia legato.");
            return true;
        }

        // Esegui il legame del giocatore qui
        boundPlayers.add(target.getUniqueId());
        target.setWalkSpeed(0f);
        target.setGameMode(GameMode.ADVENTURE);
        target.setInvulnerable(true);
        target.setCollidable(false);
        target.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(-50);
        player.sendMessage("Hai legato il giocatore " + target.getName() + ".");
        return true;
    }

    public static boolean isPlayerBound(Player player) {
        return boundPlayers.contains(player.getUniqueId());
    }

    public static void unbindPlayer(Player player) {
        boundPlayers.remove(player.getUniqueId());
    }
}
