package net.pullolo.usefulLodestones.gui;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import net.kyori.adventure.text.Component;
import net.pullolo.usefulLodestones.events.UsefulGui;
import net.pullolo.usefulLodestones.util.CooldownApi;
import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;

import static net.pullolo.usefulLodestones.util.Util.prettify;

public class LodestoneCompassGui extends UsefulGui {

    private final ItemStack compass;
    private final Player owner;

    public LodestoneCompassGui(@Nonnull Player player, ItemStack compass) {
        super(player, "lc", ChatColor.translateAlternateColorCodes('&', "&8Lodestone Compass"), 3);
        this.compass = compass;
        this.owner = player;
    }

    @Override
    public void onOpen(InventoryOpenEvent event){
        fillGui(createFiller());

        addItem(13, createMainItem());
    }

    private Icon createMainItem(){
        Icon icon = new Icon(new ItemStack(Material.LODESTONE, 1));
        icon.setName(ChatColor.translateAlternateColorCodes('&', "&e&lCLICK &fto teleport!"));
        ArrayList<String> lore = new ArrayList<>();

        long x = 0, y = 0, z = 0;
        CompassMeta meta = (CompassMeta) compass.getItemMeta();
        if (meta.getLodestone()!=null){
            x = Math.round(meta.getLodestone().getX());
            y = Math.round(meta.getLodestone().getY());
            z = Math.round(meta.getLodestone().getZ());
        }

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Lodestone at &f" + x + "&7, &f" + y + "&7, &f" + z + " &7in " + getDimension(meta.getLodestone())));
        icon.setLore(lore);

        icon.onClick(click -> {
            click.getClickedInventory().close();
            if (CooldownApi.isOnCooldown("tp", owner)){
                owner.sendMessage(ChatColor.RED + "This action is on Cooldown for " + (float) ((int) CooldownApi.getCooldownForPlayerLong("tp", owner)/100)/10 + "s.");
                return;
            }
            CooldownApi.addCooldown("tp", owner, 5);
            owner.teleport(meta.getLodestone().clone().add(0.5, 1, 0.5));
            owner.getWorld().playSound(owner.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1.2f);
        });

        return icon;
    }

    private String getDimension(Location l){
        String color = switch (l.getWorld().getEnvironment()) {
            case THE_END -> "&9";
            case NETHER -> "&c";
            default -> "&a";
        };

        return color + prettify(switch (l.getWorld().getEnvironment()) {
            case NORMAL -> "overworld";
            default -> l.getWorld().getEnvironment().toString().toLowerCase();
        });
    }
}
