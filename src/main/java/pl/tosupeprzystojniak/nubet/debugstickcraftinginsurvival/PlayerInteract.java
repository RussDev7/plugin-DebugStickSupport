package pl.tosupeprzystojniak.nubet.debugstickcraftinginsurvival;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class PlayerInteract implements Listener {
    private final DebugStickCraftingInSurvival plugin;

    public PlayerInteract(DebugStickCraftingInSurvival plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        List<String> excluded_blocks = plugin.getConfig().getStringList("excluded_blocks");

        if (player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK)) {
            Block clickedblock = event.getClickedBlock();
            if (clickedblock != null && action == Action.RIGHT_CLICK_BLOCK) {
                if (excluded_blocks.contains(clickedblock.getType().toString())) {
                    event.setCancelled(true);
                    player.sendMessage("你不能对这种类型的方块使用调试棒！");
                }
            }
        }

    }
}

