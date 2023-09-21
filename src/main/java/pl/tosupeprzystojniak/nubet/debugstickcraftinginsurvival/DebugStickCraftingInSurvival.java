package pl.tosupeprzystojniak.nubet.debugstickcraftinginsurvival;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class DebugStickCraftingInSurvival extends JavaPlugin {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.options().copyDefaults(true);
        saveConfig();
        getLogger().info(config.getString("excluded_blocks"));
        getLogger().info("DebugStickCraftingInSurvival 已开始工作！");
        Bukkit.addRecipe(new Crafting(this));
        getServer().getPluginManager().registerEvents(new PlayerInteract(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("DebugStickCraftingInSurvival 已停止工作。");
    }
}



