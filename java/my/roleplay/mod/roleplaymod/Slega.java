package my.roleplay.mod.roleplaymod;

import org.bukkit.Bukkit;
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
            sender.sendMessage("Questo comando può essere eseguito solo da un giocatore.");
            return true;
        }

        Player player = (Player) sender;

        // Verifica la sintassi del comando
        if (args.length != 1) {
            player.sendMessage("Utilizzo corretto: /slega <nome_player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("Giocatore non trovato.");
            return true;
        }

        if (target.equals(player) && !player.isOp()) {
            player.sendMessage("Non puoi slegare te stesso.");
            return true;
        }

        if (player.getLocation().distance(target.getLocation()) > 2 && !player.isOp()) {
            player.sendMessage("Devi essere a massimo 2 blocchi di distanza per slegare il giocatore.");
            return true;
        }

        // Rimuovi il legame del giocatore
        if (!Lega.isPlayerBound(target)) {
            player.sendMessage("Il giocatore non è legato.");
            return true;
        }

        Lega.unbindPlayer(target);
        target.setWalkSpeed(0.2f);
        target.setGameMode(GameMode.SURVIVAL);
        target.setInvulnerable(false);
        target.setCollidable(true);
        target.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
        player.sendMessage("Hai slegato il giocatore " + target.getName() + ".");

        return true;
    }
}
