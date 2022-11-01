package me.approximations.parkourPlugin.util;

import fr.mrmicky.fastboard.FastBoard;
import me.approximations.parkourPlugin.Main;
import me.approximations.parkourPlugin.dao.UserDao;
import me.approximations.parkourPlugin.dao.repository.UserRepository;
import me.approximations.parkourPlugin.manager.TopManager;
import me.approximations.parkourPlugin.model.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//Parkour
//
//Best Attempt: ${Player's best attempt}
//
//Leaderboard:
//  #1 - $NAME - $TIME
//  #2 - $NAME - $TIME
//  #3 - $NAME - $TIME
//  #4 - $NAME - $TIME
//  #5 - $NAME - $TIME

public class ScoreboardUtils {
    private static final UserRepository userRepository = Main.getUserRepository();
    private static final UserDao userDao = Main.getUserDao();
    private static final TopManager topManager = new TopManager();
    public static void show(Player player) {
        User user = userDao.getUsers().get(player.getUniqueId());
        TimeUtils time = new TimeUtils(user.getBestTime());

        topManager.updateTop();

        FastBoard score = new FastBoard(player);
        score.updateTitle("Parkour");
        score.updateLines(
                "",
                "Best Attempt: "+time.getTimeFormatted(),
                "",
                "Leaderboard:",
                "  #1 - "+(topManager.getTop(1) == null ? "None" : Bukkit.getOfflinePlayer(topManager.getTop(1).getUuid()).getName())+" - "+(topManager.getTop(1) == null ? "None" : TimeUtils.formatTime(topManager.getTop(1).getBestTime())),
                "  #2 - "+(topManager.getTop(2) == null ? "None" : Bukkit.getOfflinePlayer(topManager.getTop(2).getUuid()).getName())+" - "+(topManager.getTop(2) == null ? "None" : TimeUtils.formatTime(topManager.getTop(2).getBestTime())),
                "  #3 - "+(topManager.getTop(3) == null ? "None" : Bukkit.getOfflinePlayer(topManager.getTop(3).getUuid()).getName())+" - "+(topManager.getTop(3) == null ? "None" : TimeUtils.formatTime(topManager.getTop(3).getBestTime())),
                "  #4 - "+(topManager.getTop(4) == null ? "None" : Bukkit.getOfflinePlayer(topManager.getTop(4).getUuid()).getName())+" - "+(topManager.getTop(4) == null ? "None" : TimeUtils.formatTime(topManager.getTop(4).getBestTime())),
                "  #5 - "+(topManager.getTop(5) == null ? "None" : Bukkit.getOfflinePlayer(topManager.getTop(5).getUuid()).getName())+" - "+(topManager.getTop(5) == null ? "None" : TimeUtils.formatTime(topManager.getTop(5).getBestTime()))
                );
    }
}
