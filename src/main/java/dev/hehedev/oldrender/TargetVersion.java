package dev.hehedev.oldrender;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class TargetVersion {
    private static final String DEFAULT_VERSION = "1.21.11";
    private static final String VERSION_PROPERTY = "target-version";

    private ProtocolVersion target;

    public void load() {
        Path config = FabricLoader.getInstance().getConfigDir().resolve("old-render.properties");
        Properties properties = new Properties();

        try {
            Files.createDirectories(config.getParent());
            if (Files.notExists(config)) {
                properties.setProperty(VERSION_PROPERTY, DEFAULT_VERSION);
                try (Writer writer = Files.newBufferedWriter(config)) {
                    properties.store(writer, "Old Render client protocol settings");
                }
            }
            try (Reader reader = Files.newBufferedReader(config)) {
                properties.load(reader);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read Old Render config at " + config, exception);
        }

        String configured = properties.getProperty(VERSION_PROPERTY, DEFAULT_VERSION).trim();
        target = ProtocolVersion.getProtocols().stream()
            .filter(version -> version.getName().equalsIgnoreCase(configured))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Unsupported target-version '" + configured + "' in " + config
                    + "; enter an exact ViaVersion protocol name such as 1.21.11"));
    }

    public ProtocolVersion get() {
        if (target == null) {
            throw new IllegalStateException("Old Render target version has not been loaded");
        }
        return target;
    }
}
