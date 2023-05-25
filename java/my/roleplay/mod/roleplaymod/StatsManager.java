package my.roleplay.mod.roleplaymod;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class StatsManager {
    private final RoleplayMod plugin;
    private FileConfiguration statsConfig;
    private File statsFile;

    public StatsManager(RoleplayMod plugin) {
        this.plugin = plugin;
        statsFile = new File(plugin.getDataFolder(), "stats.yml");

        // Verifica se il file stats.yml esiste, altrimenti crealo
        if (!statsFile.exists()) {
            try {
                statsFile.createNewFile();
                statsConfig = YamlConfiguration.loadConfiguration(statsFile);
                createDefaultStats(); // Aggiungi questa riga per creare le statistiche predefinite
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            statsConfig = YamlConfiguration.loadConfiguration(statsFile);
        }
    }
    
    private void createDefaultStats() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            createPlayerStats(player.getName());
        }
    }

    public void saveStats() {
        try {
            statsConfig.save(statsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStat(String playerName, String statName, int value) {
        statsConfig.set(playerName + "." + statName, value);
        saveStats();
        reloadStats();
    }
    
    public void reloadStats() {
        statsConfig = YamlConfiguration.loadConfiguration(statsFile);
    }


    public int getStat(String playerName, String statName) {
        return statsConfig.getInt(playerName + "." + statName);
    }

    public boolean hasStat(String playerName, String statName) {
        return statsConfig.contains(playerName + "." + statName);
    }

    public void createPlayerStats(String playerName) {
        if (!statsConfig.contains(playerName)) {
            statsConfig.set(playerName + ".forza", 1); //forza
            statsConfig.set(playerName + ".destrezza", 1); //destrezza
            statsConfig.set(playerName + ".fortuna", 1); //fortuna
            saveStats();
        }
    }
}