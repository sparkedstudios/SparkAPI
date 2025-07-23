package com.github.sparkedstudios.config.internal;

import org.bukkit.plugin.Plugin;

public class SparkConfigLogger {

    public static void warn(Plugin plugin, String fileName, String message) {
        plugin.getLogger().warning("[SparkAPI] " + plugin.getName() + ": " + message + " (" + fileName + ")");
    }

    public static void error(Plugin plugin, String fileName, String message) {
        plugin.getLogger().severe("[SparkAPI] " + plugin.getName() + ": Error in " + fileName + " → " + message);
    }
}