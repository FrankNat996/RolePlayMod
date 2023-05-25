package my.roleplay.mod.roleplaymod;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SwordManager implements Listener {
    private final StatsManager statsManager;

    public SwordManager(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Controlla solo le spade
        if (!isSword(item.getType())) {
            return;
        }

        String playerName = player.getName();
        int forza = statsManager.getStat(playerName, "forza");

        //SPADE ##########################################################
        if (forza < 1 && (item.getType() == Material.WOODEN_SWORD || item.getType() == Material.STONE_SWORD)) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
            return;
        }

        if (forza < 5 && (item.getType() == Material.IRON_SWORD)) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
            return;
        }
        
        if (forza < 5 && (item.getType() == Material.GOLDEN_SWORD)) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
            return;
        }

        if (forza < 10 && item.getType() == Material.DIAMOND_SWORD) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
            return;
        }

        if (forza < 15 && item.getType() == Material.NETHERITE_SWORD) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Controlla solo le spade
        if (!isSword(item.getType())) {
            return;
        }

        String playerName = player.getName();
        int forza = statsManager.getStat(playerName, "forza");
        //SPADE MOB ##################################################################
        if (forza < 1 && (item.getType() == Material.WOODEN_SWORD || item.getType() == Material.STONE_SWORD)) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
            return;
        }

        if (forza < 5 && (item.getType() == Material.IRON_SWORD)) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
            return;
        }
        
        if (forza < 5 && (item.getType() == Material.GOLDEN_SWORD)) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
            return;
        }

        if (forza < 10 && item.getType() == Material.DIAMOND_SWORD) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
            return;
        }

        if (forza < 15 && item.getType() == Material.NETHERITE_SWORD) {
            event.setCancelled(true);
            player.sendMessage("Non hai abbastanza Forza per utilizzare questa spada.");
        }
    }

    private boolean isSword(Material material) {
        return material == Material.WOODEN_SWORD ||
                material == Material.STONE_SWORD ||
                material == Material.IRON_SWORD ||
                material == Material.GOLDEN_SWORD ||
                material == Material.DIAMOND_SWORD ||
                material == Material.NETHERITE_SWORD;
    }
}
