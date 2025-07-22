package sparkapi.config;

import sparkapi.config.annotations.SparkFile;
import sparkapi.config.annotations.FileType;
import sparkapi.config.internal.SparkConfigVersionValidator;

import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class SparkFileLoader {

    public static void load(Plugin plugin, Class<?> clazz, String expectedConfigVersion, String expectedMessagesVersion) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(SparkFile.class)) continue;

            SparkFile annotation = field.getAnnotation(SparkFile.class);
            FileType type = annotation.type();
            String fileName = annotation.name();

            try {
                field.setAccessible(true);

                switch (type) {
                    case CONFIG -> {
                        SparkConfig config = new SparkConfig(plugin, fileName);
                        SparkConfigVersionValidator.validate(plugin, config, expectedConfigVersion);
                        field.set(null, config);
                    }
                    case MESSAGES -> {
                        SparkConfig messages = new SparkConfig(plugin, fileName);
                        SparkConfigVersionValidator.validate(plugin, messages, expectedMessagesVersion);
                        field.set(null, messages);
                    }
                    case GENERIC -> {
                        SparkYML yml = new SparkYML(plugin, fileName);
                        field.set(null, yml);
                    }
                }

            } catch (Exception e) {
                plugin.getLogger().severe("[SparkAPI] Error uploading file: " + fileName + " → " + e.getMessage());
            }
        }
    }
}