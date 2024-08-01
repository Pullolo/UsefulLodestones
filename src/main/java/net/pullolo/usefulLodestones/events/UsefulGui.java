package net.pullolo.usefulLodestones.events;

import mc.obliviate.inventory.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class UsefulGui extends Gui {
    public UsefulGui(@NotNull Player player, @NotNull String id, String title, int rows) {
        super(player, id, title, rows);
    }

    protected ItemStack createFiller(){
        ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = i.getItemMeta();
        meta.itemName(Component.text(""));
        i.setItemMeta(meta);
        return i;
    }
}
