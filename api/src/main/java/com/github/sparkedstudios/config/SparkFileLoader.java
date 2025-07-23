package com.github.sparkedstudios.config;

import org.bukkit.plugin.Plugin;
import com.github.sparkedstudios.config.annotations.FileType;
import com.github.sparkedstudios.config.annotations.SparkFile;
import com.github.sparkedstudios.config.internal.SparkConfigLogger;
import com.github.sparkedstudios.config.internal.SparkConfigVersionValidator;

import java.lang.reflect.Field;

public class SparkFileLoader {

    public static void load(Plugin plugin, Class<?> clazz, String expectedConfigVersion, String expectedMessagesVersion) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(SparkFile.class)) continue;

            SparkFile annotation = field.getAnnotation(SparkFile.class);
            String fileName = annotation.name();
            FileType type = annotation.type();

            try {
                field.setAccessible(true);

                switch (type) {
                    case CONFIG: {
                        SparkConfig config = new SparkConfig(plugin, fileName);
                        SparkConfigVersionValidator.validate(plugin, config, expectedConfigVersion);
                        field.set(null, config);
                        break;
                    }
                    case MESSAGES: {
                        SparkConfig messages = new SparkConfig(plugin, fileName);
                        SparkConfigVersionValidator.validate(plugin, messages, expectedMessagesVersion);
                        field.set(null, messages);
                        break;
                    }
                    case GENERIC: {
                        SparkYML yml = new SparkYML(plugin, fileName);
                        field.set(null, yml);
                        break;
                    }
                }
            } catch (Exception e) {
                SparkConfigLogger.error(plugin, fileName, "Error loading: " + e.getMessage());
            }
        }
    }
}