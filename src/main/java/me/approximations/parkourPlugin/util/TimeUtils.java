package me.approximations.parkourPlugin.util;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public class TimeUtils {
    private final long seconds;
    private final long minutes;
    private final long hours;
    private final long day;

    public TimeUtils(long ms) {
        day = TimeUnit.MILLISECONDS.toDays(ms);
        hours = TimeUnit.MILLISECONDS.toHours(ms) - (day * 24);
        minutes = TimeUnit.MILLISECONDS.toMinutes(ms) - (TimeUnit.MILLISECONDS.toHours(ms) * 60);
        seconds = TimeUnit.MILLISECONDS.toSeconds(ms) - (TimeUnit.MILLISECONDS.toMinutes(ms) * 60);
    }

    public static String formatTime(long time) {
        long day = TimeUnit.MILLISECONDS.toDays(time);
        long hours = TimeUnit.MILLISECONDS.toHours(time) - (day * 24);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - (TimeUnit.MILLISECONDS.toHours(time) * 60);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - (TimeUnit.MILLISECONDS.toMinutes(time) * 60);
        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day).append("d").append(" ");
        }
        if (hours > 0) {
            sb.append(hours).append("h").append(" ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m").append(" ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("").append("s").append(" ");
        }
        String diff = sb.toString();
        return diff.isEmpty() ? "N/A" : diff;
    }

    public String getTimeFormatted() {
        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day).append("d").append(" ");
        }
        if (hours > 0) {
            sb.append(hours).append("h").append(" ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m").append(" ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("").append("s").append(" ");
        }
        String diff = sb.toString();
        return diff.isEmpty() ? "N/A" : diff;
    }
}
