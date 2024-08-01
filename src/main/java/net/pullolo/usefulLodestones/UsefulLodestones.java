package net.pullolo.usefulLodestones;

import mc.obliviate.inventory.InventoryAPI;
import net.pullolo.usefulLodestones.events.LodeEvents;
import net.pullolo.usefulLodestones.util.CooldownApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class UsefulLodestones extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private InventoryAPI inventoryAPI;

    @Override
    public void onEnable() {
        logInfo("Initializing GUI library...");
        try {
            inventoryAPI = new InventoryAPI(this);
            inventoryAPI.init();
            logInfo("GUI library initialized!");
        } catch (Exception e){
            logWarning("GUI library failed to initialize!");
        }

        logInfo("Initializing Events...");
        getServer().getPluginManager().registerEvents(new LodeEvents(), this);
        logInfo("Events initialized!");

        createCooldowns();
    }

    @Override
    public void onDisable() {
        disableGuis();
    }

    private void createCooldowns(){
        CooldownApi.createCooldown("tp", 5);
    }

    private void disableGuis(){
        for (Player p : Bukkit.getOnlinePlayers()){
            try {
                inventoryAPI.getPlayersCurrentGui(p).getInventory().close();
            } catch (Exception e){
                continue;
            }
        }
        if (inventoryAPI!=null) inventoryAPI.unload();
    }

    public static void logInfo(String s){
        log.info(s);
    }

    public static void logWarning(String s){
        log.warning(s);
    }
}
