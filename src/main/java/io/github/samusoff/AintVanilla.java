package io.github.samusoff;

import io.github.samusoff.trees.destroyer.Tree;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;


public class AintVanilla extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockDestroyed(BlockBreakEvent event) {
        Block block = event.getBlock();

        if(Tree.isALog(block)) {
            Tree tree = new Tree(block);
            tree.destroyIt(event.getPlayer());
        }

    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        if(Tree.isALog(event.getBlock())) {
            event.getBlock().setMetadata("placed-by-player",new FixedMetadataValue(this, true));
        }
    }
}
