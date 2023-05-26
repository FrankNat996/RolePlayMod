package my.roleplay.mod.roleplaymod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Trascina implements CommandExecutor {

    private final int teleportInterval = 20; // Intervallo in tick (1 tick = 1/20 di secondo)
    private BukkitRunnable teleportTask;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verifica se il comando è stato eseguito da un giocatore
        if (!(sender instanceof Player)) {
        	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("player-only-command"));
            return true;
        }

        // Verifica se il giocatore ha specificato un nome di giocatore da trascinare
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("correct-usage-drag"));
            return true;
        }

        Player trascinatore = (Player) sender;
        Player trascinato = trascinatore.getServer().getPlayer(args[0]);

        // Verifica se il giocatore specificato esiste ed è online
        if (trascinato == null || !trascinato.isOnline()) {
        	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("not-online"));
            return true;
        }

        // Verifica se il trascinatore è abbastanza vicino al trascinato (2 blocchi di distanza)
        if (trascinatore.getLocation().distance(trascinato.getLocation()) > 2) {
        	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("too-far-drag"));
            return true;
        }

        // Verifica se il giocatore è legato
        if (!Lega.isPlayerBound(trascinato)) {
        	sender.sendMessage(ChatColor.RED + RoleplayMod.getInstance().getMessage("too-tie-no-drag"));
            return true;
        }

        // Annulla il task di teletrasporto se era già attivo
        if (teleportTask != null) {
            teleportTask.cancel();
            teleportTask = null;
            sender.sendMessage(RoleplayMod.getInstance().getMessage("stop-drag") + trascinato.getName() + ".");
            return true;
        }

        // Crea un nuovo task di teletrasporto
        teleportTask = new BukkitRunnable() {
            @Override
            public void run() {
                // Verifica se il trascinatore o il trascinato non sono più online o se il trascinato non è più legato
                if (!trascinatore.isOnline() || !trascinato.isOnline() || !Lega.isPlayerBound(trascinato)) {
                    cancel();
                    return;
                }

                // Calcola la nuova posizione del trascinato mantenendo una distanza di 2 blocchi dal trascinatore
                Location trascinatoreLocation = trascinatore.getLocation();
                Location nuovaPosizione = trascinatoreLocation.clone().add(trascinatoreLocation.getDirection().multiply(-1.5));

                // Teletrasporta il trascinato alla nuova posizione
                trascinato.teleport(nuovaPosizione);
            }
        };

        // Avvia il task di teletrasporto in un intervallo di tempo
        teleportTask.runTaskTimer(RoleplayMod.getInstance(), 0, teleportInterval);

        sender.sendMessage(RoleplayMod.getInstance().getMessage("start-drag") + trascinato.getName() + ".");
        return true;
    }
}
