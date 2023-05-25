package my.roleplay.mod.roleplaymod;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    private final StatsManager statsManager;

    public StatsCommand(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Questo comando può essere eseguito solo da un giocatore.");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();

        int forza = statsManager.getStat(playerName, "forza");
        int destrezza = statsManager.getStat(playerName, "destrezza");
        int fortuna = statsManager.getStat(playerName, "fortuna");

        player.sendMessage("Le tue statistiche:");
        player.sendMessage("Forza=" + forza);
        //player.sendMessage("Forza=" + forza + ", Destrezza=" + destrezza + ", Fortuna=" + fortuna);

        return true;
    }
}