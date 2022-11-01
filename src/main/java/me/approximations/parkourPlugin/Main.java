package me.approximations.parkourPlugin;

import com.jaoow.sql.executor.SQLExecutor;
import lombok.Getter;
import me.approximations.parkourPlugin.dao.SQLProvider;
import me.approximations.parkourPlugin.dao.UserDao;
import me.approximations.parkourPlugin.dao.adapter.UserAdapter;
import me.approximations.parkourPlugin.dao.repository.UserRepository;
import me.approximations.parkourPlugin.dao.scheduler.AutoSave;
import me.approximations.parkourPlugin.listener.PlayerListener;
import me.approximations.parkourPlugin.manager.ParkourManager;
import me.approximations.parkourPlugin.manager.TopManager;
import me.approximations.parkourPlugin.model.User;
import me.approximations.parkourPlugin.util.JsonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class Main extends JavaPlugin {
    @Getter
    private static Main instance;
    @Getter
    private static ParkourManager parkourManager;
    private static SQLExecutor sqlExecutor;
    @Getter
    private static UserRepository userRepository;
    @Getter
    private static UserDao userDao;

    @Getter
    private static TopManager topManager;


    @Override
    public void onEnable() {
        instance = this;
        setupDatabase();
        JsonUtils.saveCheckpointsFile();
        parkourManager = new ParkourManager();
        parkourManager.init();
        topManager = new TopManager();
        topManager.init(this);
        setupListener();
    }

    private void setupListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(parkourManager), this);
    }

    private void setupDatabase() {
        SQLProvider sqlProvider = new SQLProvider(this);
        sqlExecutor = sqlProvider.setupDatabase();
        UserAdapter userAdapter = new UserAdapter();
        sqlExecutor.registerAdapter(User.class, userAdapter);
        userRepository = new UserRepository(this, sqlExecutor);
        userRepository.createTable();

        userDao = new UserDao(userRepository, this);

        sqlProvider.registerEvents();

        AutoSave autoSave = new AutoSave(this);
    }
}