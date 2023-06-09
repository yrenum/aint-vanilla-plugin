package io.github.samusoff.trees.destroyer;

import io.github.samusoff.AintVanilla;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {

    private boolean trigger = true;

    private static final Material[] allLogTypes = {Material.ACACIA_LOG, Material.OAK_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.DARK_OAK_LOG, Material.MANGROVE_LOG, Material.SPRUCE_LOG};
    private List<Block> logs;

    public Tree(Block block) {
        logs = new ArrayList<>();

        fromBlock(block);
    }




    private void fromBlock(Block anchor) {
        int LOGS_LIMIT = 150;
        if(logs.size() > LOGS_LIMIT)
            return;

        Block nextAnchor;

        for(BlockFace blockFace: BlockFace.values()) {
            nextAnchor = anchor.getRelative(blockFace);

            if(isALog(nextAnchor) && !nextAnchor.hasMetadata("placed-by-player")) {
                logs.add(nextAnchor);
                fromBlock(nextAnchor);
            }
        }
    }

    public void destroyIt(Player player) {
        // to avoid recursive calling
        if(trigger) {
            trigger = false;
            for (Block log : logs) {
                BlockBreakEvent event = new BlockBreakEvent(log, player);
                Bukkit.getPluginManager().callEvent(event);

                log.breakNaturally();
            }
            trigger = true;
        }
    }

    public static boolean isALog(Block block) {
        return Arrays.asList(allLogTypes).contains(block.getType());
    }

}

