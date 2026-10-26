
package com.example.setupzombies;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class SetupZombiesPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SetupZombies enabled!");
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (!command.getName().equalsIgnoreCase("setup")) {
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Эту команду может использовать только игрок.");
            return true;
        }

        if (!player.hasPermission("setupzombies.use")) {
            player.sendMessage("§cУ тебя нет прав на эту команду!");
            return true;
        }

        World world = player.getWorld();
        Location center = player.getLocation();
        int spawned = 0;

        for (int i = 0; i < 100; i++) {
            int xOffset = (i % 10) - 5;
            int zOffset = (i / 10) - 5;

            Location loc = center.clone().add(xOffset, 0, zOffset);
            loc.setY(world.getHighestBlockYAt(loc) + 1);

            if (!world.getBlockAt(loc).isPassable()
                    || !world.getBlockAt(
                    loc.clone().add(0, 1, 0)
            ).isPassable()) {
                continue;
            }

            world.spawnEntity(loc, EntityType.ZOMBIE);
            spawned++;
        }

        player.sendMessage(
                "§aПоявилось зомби: §e" + spawned + "§a из 100."
        );

        return true;
    }
}
