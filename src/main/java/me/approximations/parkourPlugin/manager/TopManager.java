package me.approximations.parkourPlugin.manager;

import me.approximations.parkourPlugin.Main;
import me.approximations.parkourPlugin.dao.repository.UserRepository;
import me.approximations.parkourPlugin.model.User;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class TopManager {
    private final UserRepository userRepository = Main.getUserRepository();
    private List<User> top5 = new ArrayList<>();

    public void updateTop() {
        top5 = new ArrayList<>(userRepository.getTop5());
        Bukkit.broadcastMessage(""+top5.size());
    }

    public User getTop(int i) {
        if(top5.size() >= i) {
            return top5.get(i-1);
        }else {
            return null;
        }
    }
}
