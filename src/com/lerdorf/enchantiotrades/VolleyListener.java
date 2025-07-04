package com.lerdorf.enchantiotrades;

import me.youhavetrouble.enchantio.EnchantioConfig;
import me.youhavetrouble.enchantio.enchants.ExecutionerEnchant;
import me.youhavetrouble.enchantio.enchants.VolleyEnchant;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VolleyListener implements Listener {

    //private final Registry<@NotNull Enchantment> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);
    private final Enchantment volley = Registry.ENCHANTMENT.get(NamespacedKey.fromString("enchantio:volley"));

    private final Random random = ThreadLocalRandom.current();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVolley(ProjectileLaunchEvent event) {
    	if (EnchantioTrades.fletcherCompatibility == false) return;
        if (volley == null) return;
        Entity projectileEntity = event.getEntity();
        //if (CreatureSpawnEvent.SpawnReason.ENCHANTMENT.equals(projectileEntity.getEntitySpawnReason())) return;
        if (projectileEntity.getScoreboardTags().contains("volley")) return;
        if (!(event.getEntity().getShooter() instanceof LivingEntity shooter)) return;
        EntityEquipment equipment = shooter.getEquipment();
        if (equipment == null) return;
        ItemStack bow = equipment.getItemInMainHand();
        int level = bow.getEnchantmentLevel(volley);
        if (level <= 0) return;
        //if (!(EnchantioConfig.ENCHANTS.get(ExecutionerEnchant.KEY) instanceof VolleyEnchant volleyEnchant)) return;
        double spread = 0.5f;
        int additionalArrowsPerLevel = 1;
        if (projectileEntity instanceof Arrow arrow) {
            for (int i = 0; i < level * additionalArrowsPerLevel; i++) {
                Vector velocity = arrow.getVelocity();
                double spreadX = (random.nextDouble() - 0.5) * spread;
                double spreadY = (random.nextDouble() - 0.5) * spread;
                double spreadZ = (random.nextDouble() - 0.5) * spread;
                Vector newVelocity = velocity.clone().add(new Vector(spreadX, spreadY, spreadZ));
                Arrow spawnedArrow = shooter.getWorld().spawnArrow(arrow.getLocation(), newVelocity.clone().normalize(), (float)newVelocity.length(), (float)spread);
                
                    spawnedArrow.setVelocity(newVelocity);
                    spawnedArrow.setFireTicks(arrow.getFireTicks());
                    spawnedArrow.setDamage(arrow.getDamage());
                    spawnedArrow.getPersistentDataContainer().set(volley.getKey(), PersistentDataType.BOOLEAN, true);
                    spawnedArrow.setCritical(arrow.isCritical());
                    spawnedArrow.setShooter(arrow.getShooter());
                    //spawnedArrow.setHasLeftShooter(arrow.hasLeftShooter());
                    spawnedArrow.setBasePotionType(arrow.getBasePotionType());
                    spawnedArrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                    arrow.getCustomEffects().forEach(effect -> spawnedArrow.addCustomEffect(effect, false));
                    spawnedArrow.addScoreboardTag("volley");
            }
            return;
        }
        if (projectileEntity instanceof SpectralArrow arrow) {
            arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
            for (int i = 0; i < level; i++) {
                Vector velocity = arrow.getVelocity();
                double spreadX = (random.nextDouble() - 0.5) * spread;
                double spreadY = (random.nextDouble() - 0.5) * spread;
                double spreadZ = (random.nextDouble() - 0.5) * spread;
                Vector newVelocity = velocity.clone().add(new Vector(spreadX, spreadY, spreadZ));
                SpectralArrow spawnedArrow = shooter.getWorld().spawnArrow(arrow.getLocation(), newVelocity.clone().normalize(), (float)newVelocity.length(), (float)spread, SpectralArrow.class);
                
                spawnedArrow.setVelocity(newVelocity);
                spawnedArrow.setFireTicks(arrow.getFireTicks());
                spawnedArrow.setDamage(arrow.getDamage());
                spawnedArrow.getPersistentDataContainer().set(volley.getKey(), PersistentDataType.BOOLEAN, true);
                spawnedArrow.setCritical(arrow.isCritical());
                spawnedArrow.setShooter(arrow.getShooter());
                //spawnedArrow.setHasLeftShooter(arrow.hasLeftShooter());
                //spawnedArrow.setBasePotionType(arrow.getBasePotionType());
                spawnedArrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                //arrow.getCustomEffects().forEach(effect -> spawnedArrow.addCustomEffect(effect, false));
                spawnedArrow.addScoreboardTag("volley");
            }
            return;
        }

    }

}
