package sparkapi.config;

import org.bukkit.plugin.Plugin;

public class SparkConfig extends SparkYML {

    public SparkConfig(Plugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public String getVersion() {
        return get().getString("version", "undefined");
    }
}