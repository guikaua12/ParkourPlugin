package me.approximations.parkourPlugin.manager;

import me.approximations.parkourPlugin.Main;
import me.approximations.parkourPlugin.dao.repository.UserRepository;
import me.approximations.parkourPlugin.model.User;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class TopManager {
    private Main plugin;
    private final UserRepository userRepository = plugin.getUserRepository();
    private final List<User> top5 = new ArrayList<>();

    public void init(Main plugin) {
        this.plugin = plugin;
        //5 minutes update timer
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            updateTop();
        }, 20L, 300*20);
    }

    public void updateTop() {
        long before = System.currentTimeMillis();
        top5.clear();
        top5.addAll(userRepository.getTop5());
        long now = System.currentTimeMillis();
        long total = now - before;
        plugin.getLogger().info("Top 5 updated in "+total+" ms.");

    }

    public User getTop(int i) {
        if(top5.size() >= i) {
            return top5.get(i-1);
        }else {
            return null;
        }
    }
}
