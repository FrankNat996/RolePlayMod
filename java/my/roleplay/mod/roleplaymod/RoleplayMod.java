package my.roleplay.mod.roleplaymod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public final class RoleplayMod extends JavaPlugin {
	private FileConfiguration config;
    private FileConfiguration messagesConfig;

    private String language;
    private static RoleplayMod instance;
    private EconomyManager economyManager;
    private StatsManager statsManager;
    private SwordManager swordManager;
    private Map<Player, CharacterBot> activeBots;

    @Override
    public void onEnable() {
        instance = this;
        economyManager = new EconomyManager();
        statsManager = new StatsManager(this);
        swordManager = new SwordManager(statsManager);
        activeBots = new HashMap<>();
        
     // Carica il file di configurazione delle lingue
        loadLanguageConfig();

        // Carica il file di configurazione dei messaggi
        loadMessagesConfig();

        // Carica i dati dell'economia
        economyManager.load();

        //getCommand("me").setExecutor(new Me());
        getCommand("info").setExecutor(new Info());
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(economyManager), this);
        getCommand("balance").setExecutor(new Saldo(economyManager));
        getCommand("pay").setExecutor(new PaymentCommand(economyManager));
        getCommand("editbalance").setExecutor(new ModificaSaldo(economyManager));
        getCommand("showbalance").setExecutor(new VisualizzaSaldo(economyManager));
        getCommand("tie").setExecutor(new Lega());
        getCommand("untie").setExecutor(new Slega());
        getCommand("free").setExecutor(new Liberati());
        getCommand("drag").setExecutor(new Trascina());
        getCommand("searchinventory").setExecutor(new CercaInventario());
        getCommand("stats").setExecutor(new StatsCommand(statsManager));
        getCommand("editstats").setExecutor(new ModificaStatsCommand(statsManager));
        getCommand("me").setExecutor(new BotCommand());


        // Aggiungi il gestore delle spade come listener degli eventi
        getServer().getPluginManager().registerEvents(swordManager, this);
        getServer().getPluginManager().registerEvents(new StatIncreaseListener(statsManager), this);
    }

    @Override
    public void onDisable() {
        // Salva i dati dell'economia
        economyManager.save();


        // Rimuovi tutti i bot attivi
        for (CharacterBot bot : activeBots.values()) {
            bot.destroy();
        }

        activeBots.clear();
    }

    public static RoleplayMod getInstance() {
        return instance;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public SwordManager getSwordManager() {
        return swordManager;
    }

    public Map<Player, CharacterBot> getActiveBots() {
        return activeBots;
    }
    
    private void loadLanguageConfig() {
        File configFile = new File(getDataFolder(), "languages.yml");

        // Crea il file se non esiste
        if (!configFile.exists()) {
            saveResource("languages.yml", false);
        }

        // Carica la configurazione delle lingue
        config = YamlConfiguration.loadConfiguration(configFile);

        // Ottieni la lingua selezionata
        language = config.getString("language");
    }

    private void loadMessagesConfig() {
        File messagesFile = new File(getDataFolder(), language + ".yml");

        // Crea il file se non esiste
        if (!messagesFile.exists()) {
            saveResource(language + ".yml", false);
        }

        // Carica la configurazione dei messaggi
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public String getMessage(String key) {
        // Leggi la stringa dalla configurazione dei messaggi
        return messagesConfig.getString("messages." + key);
    }
}
