package com.github.sparkedstudios.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import com.github.sparkedstudios.config.internal.SparkConfigLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SparkYML {

    private final Plugin plugin;
    private final File file;
    private final String name;
    private FileConfiguration config;

    public SparkYML(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.name = fileName.endsWith(".yml") ? fileName : fileName + ".yml";
        this.file = new File(plugin.getDataFolder(), this.name);

        saveDefault();
        reload();
    }

    public void reload() {
        if (!file.exists()) {
            saveDefault();
        }

        this.config = YamlConfiguration.loadConfiguration(file);

        InputStream stream = plugin.getResource(name);
        if (stream != null) {
            try (Reader defReader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defReader);
                this.config.setDefaults(defConfig);
            } catch (IOException e) {
                SparkConfigLogger.error(plugin, name, "Error loading defaults: " + e.getMessage());
            }
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            SparkConfigLogger.error(plugin, name, "Error saving file: " + e.getMessage());
        }
    }

    public void saveDefault() {
        if (!file.exists()) {
            try {
                plugin.saveResource(name, false);
            } catch (IllegalArgumentException ignored) {
                SparkConfigLogger.warn(plugin, name, "Default resource not found in jar: " + name);
            }
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public Object get(String path) {
        return config.get(path);
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    public boolean exists(String path) {
        if (!contains(path)) {
            SparkConfigLogger.warn(plugin, name, "Path does not exist: \"" + path + "\"");
            return false;
        }
        return true;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public static SparkYML of(Plugin plugin, String fileName) {
        return new SparkYML(plugin, fileName);
    }

    @Override
    public String toString() {
        return name;
    }
}