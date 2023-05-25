package my.roleplay.mod.roleplaymod;

import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CharacterBot {
    private final String displayName;
    private final ArmorStand armorStand;

    public CharacterBot(String displayName, Player player) {
        this.displayName = displayName;

        // Crea un ArmorStand per rappresentare il personaggio
        armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(ChatColor.YELLOW + displayName);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setMarker(true);

        // Imposta l'ArmorStand come passeggero del giocatore per seguirlo
        player.addPassenger(armorStand);

        // Avvia un task per mantenere l'ArmorStand nella posizione del giocatore
        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.teleport(player.getLocation());
            }
        }.runTaskTimer(JavaPlugin.getPlugin(RoleplayMod.class), 0, 1);

        // Avvia un task per distruggere il bot dopo 5 secondi
        new BukkitRunnable() {
            @Override
            public void run() {
                destroy();
            }
        }.runTaskLater(JavaPlugin.getPlugin(RoleplayMod.class), 5 * 20);
    }

    public void destroy() {
        armorStand.remove();
    }

    public void setHelmet(ItemStack helmet) {
        armorStand.getEquipment().setHelmet(helmet);
    }

    public String getDisplayName() {
        return displayName;
    }
}