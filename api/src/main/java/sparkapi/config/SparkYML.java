package sparkapi.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import sparkapi.config.internal.SparkConfigLogger;

import java.io.*;

public class SparkYML {

    protected final Plugin plugin;
    protected final File file;
    protected FileConfiguration config;
    protected final String name;

    public SparkYML(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.name = fileName.endsWith(".yml") ? fileName : fileName + ".yml";
        this.file = new File(plugin.getDataFolder(), this.name);

        saveDefault();
        reload();
    }

    public void reload() {
        try {
            config = YamlConfiguration.loadConfiguration(file);

            InputStream is = plugin.getResource(name);
            if (is != null) {
                try (Reader reader = new InputStreamReader(is, "UTF-8")) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(reader);
                    config.setDefaults(defConfig);
                }
            }

        } catch (IOException e) {
            SparkConfigLogger.error(plugin, name, "Error reloading: " + e.getMessage());
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            SparkConfigLogger.error(plugin, name, "Error saving: " + e.getMessage());
        }
    }

    public void saveDefault() {
        if (!file.exists()) {
            plugin.saveResource(name, false);
        }
    }

    public FileConfiguration get() {
        return config;
    }

    public boolean exists(String path) {
        if (!config.contains(path)) {
            SparkConfigLogger.warn(plugin, name, "Non-existent Path: \"" + path + "\"");
            return false;
        }
        return true;
    }

    public static SparkYML of(Plugin plugin, String name) {
        return new SparkYML(plugin, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
