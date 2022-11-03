package me.approximations.parkourPlugin.util;

import fr.mrmicky.fastboard.FastBoard;
import lombok.Getter;
import me.approximations.parkourPlugin.Main;
import me.approximations.parkourPlugin.dao.UserDao;
import me.approximations.parkourPlugin.dao.repository.UserRepository;
import me.approximations.parkourPlugin.manager.TopManager;
import me.approximations.parkourPlugin.model.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

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
    private static final TopManager topManager = Main.getTopManager();

    @Getter
    private static final Map<UUID, FastBoard> scores = new HashMap<>();
    public static void show(Player player) {
        User user = userDao.getUsers().get(player.getUniqueId());
        TimeUtils time = new TimeUtils(user.getBestTime());

        topManager.updateTop();

        FastBoard score = new FastBoard(player);
        score.updateTitle("Parkour");
        //messy code but understandable
        score.updateLines(
                "",
                "Best Attempt: "+time.getTimeFormatted(),
                "",
                "Leaderboard:",
                "  #1 - "+(topManager.getTop(1) == null ? "N/A" : topManager.getTop(5).getName())+" - "+(topManager.getTop(1) == null ? "N/A" : TimeUtils.formatTime(topManager.getTop(1).getBestTime())),
                "  #2 - "+(topManager.getTop(2) == null ? "N/A" : topManager.getTop(5).getName())+" - "+(topManager.getTop(2) == null ? "N/A" : TimeUtils.formatTime(topManager.getTop(2).getBestTime())),
                "  #3 - "+(topManager.getTop(3) == null ? "N/A" : topManager.getTop(5).getName())+" - "+(topManager.getTop(3) == null ? "N/A" : TimeUtils.formatTime(topManager.getTop(3).getBestTime())),
                "  #4 - "+(topManager.getTop(4) == null ? "N/A" : topManager.getTop(5).getName())+" - "+(topManager.getTop(4) == null ? "N/A" : TimeUtils.formatTime(topManager.getTop(4).getBestTime())),
                "  #5 - "+(topManager.getTop(5) == null ? "N/A" : topManager.getTop(5).getName())+" - "+(topManager.getTop(5) == null ? "N/A" : TimeUtils.formatTime(topManager.getTop(5).getBestTime()))
                );
        scores.put(player.getUniqueId(), score);
    }
}
