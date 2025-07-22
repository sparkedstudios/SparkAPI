package sparkapi.config.internal;

import sparkapi.config.SparkConfig;
import org.bukkit.plugin.Plugin;

public class SparkConfigVersionValidator {

    public static boolean validate(Plugin plugin, SparkConfig config, String expectedVersion) {
        String current = config.getVersion();

        if (current == null || current.equalsIgnoreCase("undefined")) {
            SparkConfigLogger.warn(plugin, config.toString(), "The 'version' field is missing");
            return false;
        }

        if (!current.equals(expectedVersion)) {
            SparkConfigLogger.warn(plugin, config.toString(), "The current version ("+ current +") does not match the expected one (" + expectedVersion + ")");
            return false;
        }

        return true;
    }
}
