package my.roleplay.mod.roleplaymod;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModificaStatsCommand implements CommandExecutor {
    private final StatsManager statsManager;

    public ModificaStatsCommand(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("Devi essere un operatore per utilizzare questo comando.");
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage("Utilizzo corretto: /modificastats <nomeGiocatore> <statName> <amount>");
            return true;
        }

        String playerName = args[0];
        String statName = args[1];
        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("L'amount deve essere un numero intero.");
            return true;
        }

        int currentStat = statsManager.getStat(playerName, statName);
        int newStat = currentStat + amount;
        statsManager.setStat(playerName, statName, newStat);

        sender.sendMessage("La statistica " + statName + " del giocatore " + playerName + " è stata modificata. Nuovo valore: " + newStat);

        return true;
    }
}
