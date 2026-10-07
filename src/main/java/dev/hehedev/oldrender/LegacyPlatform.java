package dev.hehedev.oldrender;

import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.platform.UserConnectionViaVersionPlatform;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;

final class LegacyPlatform extends UserConnectionViaVersionPlatform {
    private static final ScheduledExecutorService EXECUTOR =
        Executors.newScheduledThreadPool(2, runnable -> {
            Thread thread = new Thread(runnable, "OldRender-ViaVersion");
            thread.setDaemon(true);
            return thread;
        });

    LegacyPlatform() {
        super(FabricLoader.getInstance().getConfigDir().resolve("old-render").toFile());
    }

    @Override
    public Logger createLogger(String name) {
        return Logger.getLogger(name);
    }

    @Override
    public String getPlatformName() {
        return "Old Render";
    }

    @Override
    public String getPlatformVersion() {
        return OldRenderMod.modVersion();
    }

    @Override
    public PlatformTask<?> runAsync(Runnable runnable) {
        return task(EXECUTOR.schedule(runnable, 0, TimeUnit.MILLISECONDS));
    }

    @Override
    public PlatformTask<?> runRepeatingAsync(Runnable runnable, long ticks) {
        return task(EXECUTOR.scheduleAtFixedRate(runnable, 0, ticks * 50, TimeUnit.MILLISECONDS));
    }

    @Override
    public PlatformTask<?> runSync(Runnable runnable) {
        return runAsync(runnable);
    }

    @Override
    public PlatformTask<?> runSync(Runnable runnable, long ticks) {
        return task(EXECUTOR.schedule(runnable, ticks * 50, TimeUnit.MILLISECONDS));
    }

    @Override
    public PlatformTask<?> runRepeatingSync(Runnable runnable, long ticks) {
        return runRepeatingAsync(runnable, ticks);
    }

    @Override
    public boolean hasPlugin(String name) {
        return FabricLoader.getInstance().isModLoaded(name);
    }

    private static PlatformTask<?> task(java.util.concurrent.Future<?> future) {
        return () -> future.cancel(false);
    }
}
