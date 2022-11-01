package me.approximations.parkourPlugin.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lombok.RequiredArgsConstructor;
import me.approximations.parkourPlugin.Main;
import me.approximations.parkourPlugin.dao.UserDao;
import me.approximations.parkourPlugin.manager.ParkourManager;
import me.approximations.parkourPlugin.model.User;
import me.approximations.parkourPlugin.util.ScoreboardUtils;
import me.approximations.parkourPlugin.util.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener {
    private final ParkourManager parkourManager;
    private final UserDao userDao = Main.getUserDao();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(e.getFrom().distance(e.getTo()) == 0D) return;
        Player p = e.getPlayer();

        Location loc = BukkitAdapter.adapt(p.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);
        for (ProtectedRegion region : set.getRegions()) {
            if(region.getId().equalsIgnoreCase("parkour_region")) {
                if(ScoreboardUtils.getScores().containsKey(p.getUniqueId())) return;
                ScoreboardUtils.show(p);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block b = e.getClickedBlock();
        if(!(b.getState() instanceof Sign)) return;
        Player p = e.getPlayer();
        if(b.getLocation().equals(parkourManager.getStartCheckpoint())) {
            if(parkourManager.getPlayers().containsKey(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED+"You are already in the parkour.");
                return;
            }
            parkourManager.addPlayer(p);
            p.sendMessage(ChatColor.GREEN+"You've entered the parkour.");
        }else if(b.getLocation().equals(parkourManager.getEndCheckpoint())) {
            if(!parkourManager.getPlayers().containsKey(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED+"You are not in the parkour.");
                return;
            }
            long timestamp = (System.currentTimeMillis() - parkourManager.getPlayersTime().get(p.getUniqueId()));
            TimeUtils time = new TimeUtils(timestamp);
            p.sendMessage(ChatColor.GREEN+"You've ended the parkour in "+time.getTimeFormatted());
            parkourManager.removePlayer(p);

            User user = userDao.getUsers().get(p.getUniqueId());
            long bestTime = user.getBestTime();
            if(bestTime < 0) {
                user.setBestTime(timestamp);
            }else {
                if(bestTime > timestamp) {
                    user.setBestTime(timestamp);
                }
            }
            userDao.insertOrUpdate(user);
        }else if(parkourManager.isCheckpoint(b.getLocation())){
            if(!parkourManager.getPlayers().containsKey(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED+"You are not in the parkour.");
                return;
            }
            if(b.getLocation().equals(parkourManager.getLastCheckpoint(p))) {
                p.sendMessage(ChatColor.RED+"This is already your last checkpoint.");
                return;
            }
            parkourManager.saveLastCheckpoint(p, b.getLocation());
            p.sendMessage(ChatColor.GREEN+"You saved your checkpoint.");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(ScoreboardUtils.getScores().containsKey(p.getUniqueId())) {
            ScoreboardUtils.getScores().remove(p.getUniqueId());
        }
    }
}
