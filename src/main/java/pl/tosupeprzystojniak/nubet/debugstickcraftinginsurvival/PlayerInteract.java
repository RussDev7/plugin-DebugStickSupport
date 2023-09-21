package pl.tosupeprzystojniak.nubet.debugstickcraftinginsurvival;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
        GameMode playerGameMode = player.getGameMode();
        Action action = event.getAction();
        List<String> excluded_blocks = plugin.getConfig().getStringList("excluded_blocks");

        if (player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK) ||
                player.getInventory().getItemInOffHand().getType().equals(Material.DEBUG_STICK)
        ) {
            if (!playerGameMode.equals(GameMode.SURVIVAL)) {
                return;
            }

            //  调试棒会触发BlockPlaceEvent，所以就算不通知，对应的保护插件也会通知。
            if (event.useItemInHand() == Event.Result.DENY) {
                player.sendMessage(Component.text("你不能在这里使用调试棒！").color(NamedTextColor.RED));
                return;
            }

            if (!player.hasPermission("minecraft.debugstick.always")) {
                player.sendMessage("抱歉，只有建筑师可以使用调试棒。");
                event.setUseItemInHand(Event.Result.DENY);
                return;
            }

            Block clickedblock = event.getClickedBlock();
            if (clickedblock != null && action == Action.RIGHT_CLICK_BLOCK) {
                if (excluded_blocks.contains(clickedblock.getType().toString())) {
                    event.setUseItemInHand(Event.Result.DENY);
                    player.sendMessage("你不能对这种类型的方块使用调试棒！");
                }
            }
        }

    }
}

