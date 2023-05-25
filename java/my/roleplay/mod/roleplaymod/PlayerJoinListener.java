package my.roleplay.mod.roleplaymod;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private EconomyManager economyManager;

    public PlayerJoinListener(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Verifica se il giocatore ha un saldo già salvato
        if (!economyManager.hasBalance(player)) {
            // Saldo non trovato, imposta un saldo di default
            economyManager.setBalance(player, 0.0);
        }
    }
}
