package net.pullolo.usefulLodestones.events;

import net.pullolo.usefulLodestones.gui.LodestoneCompassGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class LodeEvents implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player p = event.getPlayer();
        if (event.getHand()!=null && !event.getHand().equals(EquipmentSlot.HAND)){
            return;
        }
        if (!p.getInventory().getItemInMainHand().getType().equals(Material.COMPASS)){
            return;
        }
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if ((event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.LODESTONE))){
                return;
            }
        }
        ItemStack compass = p.getInventory().getItemInMainHand();
        ItemMeta compassMeta = compass.getItemMeta();
        if (compassMeta instanceof CompassMeta){
            if (!((CompassMeta) compassMeta).hasLodestone()){
                return;
            }
            new LodestoneCompassGui(p, compass).open();
            event.setCancelled(true);
        }
    }

}
