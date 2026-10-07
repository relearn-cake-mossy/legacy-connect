package dev.hehedev.oldrender;

import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.platform.NoopInjector;
import dev.hehedev.oldrender.protocol.OldRenderProtocol;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class OldRenderMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Old Render");
    private static final TargetVersion TARGET_VERSION = new TargetVersion();

    @Override
    public void onInitialize() {
        TARGET_VERSION.load();

        LegacyPlatform platform = new LegacyPlatform();
        ViaManagerImpl manager = ViaManagerImpl.builder()
            .platform(platform)
            .injector(new NoopInjector())
            .loader(ViaPlatformLoader.NOOP)
            .commandHandler(new ViaCommandHandler())
            .build();

        Via.init(manager);
        manager.init();
        OldRenderProtocol.INSTANCE.initialize();
        manager.onServerLoaded();

        LOGGER.info("Protocol translation initialized for Minecraft 1.16.5 -> {}", TARGET_VERSION.get().getName());
    }

    public static TargetVersion targetVersion() {
        return TARGET_VERSION;
    }

    public static String modVersion() {
        return FabricLoader.getInstance().getModContainer("oldrender")
            .map(container -> container.getMetadata().getVersion().getFriendlyString())
            .orElse("unknown");
    }
}
