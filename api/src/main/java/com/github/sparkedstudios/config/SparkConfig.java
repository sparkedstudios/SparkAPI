package com.github.sparkedstudios.config;

import org.bukkit.plugin.Plugin;

public class SparkConfig extends SparkYML {

    public SparkConfig(Plugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public String getVersion() {
        return getConfig().getString("version", "undefined");
    }
}