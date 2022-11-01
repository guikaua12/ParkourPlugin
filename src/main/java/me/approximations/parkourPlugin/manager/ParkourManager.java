package me.approximations.parkourPlugin.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.approximations.parkourPlugin.util.JsonUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
public class ParkourManager {
    private final HashMap<UUID, Player> players = new HashMap<>();
    private final HashMap<UUID, Long> playersTime = new HashMap<>();
    private final HashMap<UUID, Location> lastPlayerCheckpoint = new HashMap<>();
    private List<Location> checkpoints;

    public void init() {
        checkpoints = JsonUtils.loadCheckpointsFile();
    }

    public Location getStartCheckpoint() {
        return checkpoints.get(0);
    }

    public Location getEndCheckpoint() {
        return checkpoints.get(checkpoints.size()-1);
    }

    public void addPlayer(Player player) {
        this.players.put(player.getUniqueId(), player);
        this.playersTime.put(player.getUniqueId(), System.currentTimeMillis());
        saveLastCheckpoint(player, getStartCheckpoint());
    }

    public void removePlayer(Player player) {
        this.players.remove(player.getUniqueId());
        this.playersTime.remove(player.getUniqueId());
        this.lastPlayerCheckpoint.remove(player.getUniqueId());
    }

    public void saveLastCheckpoint(Player player, Location checkpoint) {
        this.lastPlayerCheckpoint.put(player.getUniqueId(), checkpoint);
    }

    public Location getLastCheckpoint(Player player) {
        return this.lastPlayerCheckpoint.get(player.getUniqueId());
    }

    public boolean isCheckpoint(Location location) {
        return this.checkpoints.contains(location);
    }

}
