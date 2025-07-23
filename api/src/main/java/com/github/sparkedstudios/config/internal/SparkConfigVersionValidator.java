package com.github.sparkedstudios.config.internal;

import org.bukkit.plugin.Plugin;
import com.github.sparkedstudios.config.SparkConfig;

public class SparkConfigVersionValidator {

    public static boolean validate(Plugin plugin, SparkConfig config, String expectedVersion) {
        String current = config.getVersion();

        if (current == null || current.equalsIgnoreCase("undefined")) {
            SparkConfigLogger.warn(plugin, config.toString(), "Missing 'version' field");
            return false;
        }

        if (!current.equals(expectedVersion)) {
            SparkConfigLogger.warn(plugin, config.toString(), "Version mismatch → Expected: " + expectedVersion + " | Found: " + current);
            return false;
        }

        return true;
    }
}