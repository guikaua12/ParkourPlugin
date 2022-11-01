package me.approximations.parkourPlugin.util;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@AllArgsConstructor
public class LocationUtils {
    private String worldName;
    private double x;
    private double y;
    private double z;

    public Location toLocation() {
        World world = Bukkit.getWorld(worldName);
        return new Location(world, x, y, z);
    }
}
