package me.approximations.parkourPlugin.util;

import lombok.Getter;
import me.approximations.parkourPlugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsonUtils {
    private static final Main plugin = Main.getInstance();
    private static final File checkpointsFile = new File(plugin.getDataFolder(), "checkpoints.json");



    public static void saveCheckpointsFile() {
        if(!checkpointsFile.exists()) {
            plugin.saveResource(checkpointsFile.getName(), false);
        }
    }

    public static List<Location> loadCheckpointsFile() {
        List<Location> locations = new ArrayList<>();
        JSONArray checkpointsData = getJsonObject(checkpointsFile).getJSONArray("checkpointsData");
        for (Object o : checkpointsData) {
            JSONObject line = (JSONObject) o;

            LocationUtils lu = new LocationUtils(line.getString("worldName"), line.getDouble("x"), line.getDouble("y"), line.getDouble("z"));
            locations.add(lu.toLocation());
        }
        return locations;
    }

    private static JSONObject getJsonObject(File file) {
        try {
            FileInputStream fis;
            byte[] data;
            fis = new FileInputStream(file);
            data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String str = new String(data, StandardCharsets.UTF_8);
            return new JSONObject(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
