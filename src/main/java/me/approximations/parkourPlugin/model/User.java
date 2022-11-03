package me.approximations.parkourPlugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private UUID uuid;
    private long bestTime;

    public String getName() {
        return Bukkit.getOfflinePlayer(uuid).getName();
    }
}
