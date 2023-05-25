package my.roleplay.mod.roleplaymod;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class StatIncreaseListener implements Listener {
    private final StatsManager statsManager;
    private final Random random;

    public StatIncreaseListener(StatsManager statsManager) {
        this.statsManager = statsManager;
        this.random = new Random();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            Player killer = (Player) event.getEntity().getKiller();

            // Incrementa la statistica "Forza" del giocatore che ha effettuato l'uccisione con una probabilità del 10%
            if (random.nextDouble() < 0.10) {
                String playerName = killer.getName();
                int currentForza = statsManager.getStat(playerName, "forza");
                int newForza = currentForza + 1;
                statsManager.setStat(playerName, "forza", newForza);
                killer.sendMessage("La tua statistica 'Forza' è stata aumentata di 1!");
             // Mostra l'effetto particellare di sfere di esperienza attorno al giocatore per 2 secondi
                killer.getWorld().spawnParticle(Particle.GLOW, killer.getLocation().add(0, 1, 0), 100, 0.2, 0.2, 0.2, 0.05);
                killer.playSound(killer.getLocation(), Sound.ENTITY_WARDEN_ROAR, 1.0f, 1.0f);
            }
        }
    }
}
