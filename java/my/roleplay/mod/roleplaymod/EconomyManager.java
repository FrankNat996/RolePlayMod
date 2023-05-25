package my.roleplay.mod.roleplaymod;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class EconomyManager {

    private File file;
    private FileConfiguration config;
    private Map<UUID, Double> playerBalances;

    public EconomyManager() {
        playerBalances = new HashMap<>();
    }

    public void load() {
        file = new File(RoleplayMod.getInstance().getDataFolder(), "economy.yml");

        if (!file.exists()) {
            RoleplayMod.getInstance().saveResource("economy.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);

        // Load player balances from the configuration file
        for (String key : config.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            double balance = config.getDouble(key);
            playerBalances.put(uuid, balance);
        }
    }

    public void save() {
        // Save player balances to the configuration file
        config = new YamlConfiguration();

        for (UUID uuid : playerBalances.keySet()) {
            double balance = playerBalances.get(uuid);
            config.set(uuid.toString(), balance);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(Player player) {
        return playerBalances.getOrDefault(player.getUniqueId(), 0.0);
    }

    public void setBalance(Player player, double balance) {
        playerBalances.put(player.getUniqueId(), balance);
    }

    public void deposit(Player player, double amount) {
        double balance = getBalance(player);
        balance += amount;
        setBalance(player, balance);
    }

    public void withdraw(Player player, double amount) {
        double balance = getBalance(player);
        balance -= amount;
        setBalance(player, balance);
    }

    public boolean hasEnoughBalance(Player player, double amount) {
        double balance = getBalance(player);
        return balance >= amount;
    }
    
    public boolean hasBalance(Player player) {
        return playerBalances.containsKey(player.getUniqueId());
    }
}

