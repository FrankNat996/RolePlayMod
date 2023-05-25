package my.roleplay.mod.roleplaymod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class RoleplayMod extends JavaPlugin {
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

        // Carica i dati dell'economia
        economyManager.load();

        //getCommand("me").setExecutor(new Me());
        getCommand("info").setExecutor(new Info());
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(economyManager), this);
        getCommand("saldo").setExecutor(new Saldo(economyManager));
        getCommand("paga").setExecutor(new PaymentCommand(economyManager));
        getCommand("modificasaldo").setExecutor(new ModificaSaldo(economyManager));
        getCommand("visualizzasaldo").setExecutor(new VisualizzaSaldo(economyManager));
        getCommand("lega").setExecutor(new Lega());
        getCommand("slega").setExecutor(new Slega());
        getCommand("liberati").setExecutor(new Liberati());
        getCommand("trascina").setExecutor(new Trascina());
        getCommand("cercainventario").setExecutor(new CercaInventario());
        getCommand("statistiche").setExecutor(new StatsCommand(statsManager));
        getCommand("modificastats").setExecutor(new ModificaStatsCommand(statsManager));
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
}
